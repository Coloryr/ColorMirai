package coloryr.colormirai.demo.sdk;

import coloryr.colormirai.demo.sdk.api.ICall;
import coloryr.colormirai.demo.sdk.api.ILog;
import coloryr.colormirai.demo.sdk.api.IState;
import coloryr.colormirai.demo.sdk.enums.LogType;
import coloryr.colormirai.demo.sdk.enums.StateType;
import coloryr.colormirai.demo.sdk.pack.PackBase;
import coloryr.colormirai.demo.sdk.pack.from.StartPack;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

//需要使用JAVA8环境
//需要安装fastjson库

public class BaseRobot {

    /**
     * 机器人登录的QQ号列表
     */
    public List<Long> qqs;
    /**
     * 是否正在运行
     */
    public boolean isRun;
    /**
     * 是否连接
     */
    public boolean isConnect;
    /**
     * 第一次连接检查
     */
    public boolean isFirst = true;

    private ICall robotCallEvent;
    private ILog robotLogEvent;
    private IState robotStateEvent;
    private Socket socket;
    private Thread readThread;
    private Thread doThread;
    private Queue<RobotTask> queueRead;
    private Queue<byte[]> queueSend;
    private StartPack packStart;
    private RobotConfig config;
    private int times = 0;

    protected boolean callTop(byte index, String data) {
        return false;
    }

    /**
     * 设置配置
     *
     * @param config 机器人配置
     */
    public void set(RobotConfig config) {
        this.config = config;

        robotCallEvent = config.callAction;
        robotLogEvent = config.logAction;
        robotStateEvent = config.stateAction;

        packStart = new StartPack() {{
            Name = config.name;
            Reg = config.pack;
            Groups = config.groups;
            QQs = config.qqs;
            RunQQ = config.runQQ;
        }};
    }

    /**
     * 启动机器人
     */
    public void start() {
        if (readThread != null && readThread.isAlive())
            return;
        queueRead = new ConcurrentLinkedDeque<>();
        queueSend = new ConcurrentLinkedDeque<>();

        doThread = new Thread(() -> {
            RobotTask task;
            while (isRun) {
                try {
                    task = queueRead.poll();
                    if (task != null) {
                        if (task.index == 60)
                            continue;
                        if (callTop(task.index, task.data))
                            continue;
                        if (RobotSDK.packType.containsKey(task.index)) {
                            Class<? extends PackBase> class1 = RobotSDK.packType.get(task.index);
                            robotCallEvent.CallAction(task.index, JSON.parseObject(task.data, class1));
                        } else {
                            logError("不认识的数据包" + task.index);
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    logError(e);
                }
            }
        });

        readThread = new Thread(() -> {
            try {
                while (!isRun) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                logError(e);
            }
            doThread.start();
            int time = 0;
            byte[] data;
            while (isRun) {
                try {
                    if (!isConnect) {
                        reConnect();
                        isFirst = false;
                        times = 0;
                        robotStateEvent.StateAction(StateType.CONNECT);
                    } else if (socket.getInputStream().available() > 0) {
                        data = new byte[socket.getInputStream().available()];
                        socket.getInputStream().read(data);
                        byte type = data[data.length - 1];
                        data[data.length - 1] = 0;
                        queueRead.add(new RobotTask(type, new String(data, StandardCharsets.UTF_8)));
                    } else if (config.check && time >= 20) {
                        time = 0;
                        byte[] temp = BuildPack.build(new Object(), 60);
                        addTask(temp);
                    } else if ((data = queueSend.poll()) != null) {
                        socket.getOutputStream().write(data);
                        socket.getOutputStream().flush();
                    }
                    time++;
                    Thread.sleep(50);
                } catch (Exception e) {
                    isConnect = false;
                    robotStateEvent.StateAction(StateType.DISCONNECT);
                    if (isFirst) {
                        isRun = false;
                        logError("机器人连接失败");
                        logError(e);
                    } else {
                        times++;
                        if (times == 10) {
                            isRun = false;
                            logError("重连失败次数过多");
                        }
                        logError("机器人连接失败");
                        logError(e);
                        isConnect = false;
                        logError("机器人" + config.time + "毫秒后重连");
                        try {
                            Thread.sleep(config.time);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        logError("机器人重连中");
                    }
                }
            }
        });
        readThread.start();
        isRun = true;
    }

    private void reConnect() {
        try {
            if (socket != null)
                socket.close();

            robotStateEvent.StateAction(StateType.CONNECTING);

            socket = new Socket(config.ip, config.port);

            byte[] data = (JSON.toJSON(packStart) + " ").getBytes(StandardCharsets.UTF_8);
            data[data.length - 1] = 0;

            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();

            while (socket.getInputStream().available() == 0) {
                Thread.sleep(10);
            }

            data = new byte[socket.getInputStream().available()];
            socket.getInputStream().read(data);
            qqs = JSON.parseArray(new String(data, StandardCharsets.UTF_8), Long.class);

            queueRead.clear();
            queueSend.clear();
            logOut("机器人已连接");
            isConnect = true;
        } catch (Exception e) {
            logError("机器人连接失败");
            logError(e);
        }
    }

    private void sendStop() {
        try {
            byte[] data = BuildPack.build(new Object(), 127);
            socket.getOutputStream().write(data);
        } catch (Exception e) {
            logOut("机器人断开错误");
            e.printStackTrace();
        }
    }

    /**
     * 停止机器人
     */
    public void stop() {
        logOut("机器人正在断开");
        if (isConnect)
            sendStop();
        isRun = false;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logOut("机器人已断开");
    }

    /**
     * 添加数据包
     *
     * @param data 数据包
     */
    public void addTask(byte[] data) {
        queueSend.add(data);
    }

    private void logError(Exception e) {
        robotLogEvent.LogAction(LogType.ERROR, "机器人错误\n" + e.toString());
    }

    private void logError(String a) {
        robotLogEvent.LogAction(LogType.ERROR, "机器人错误:" + a);
    }

    private void logOut(String a) {
        robotLogEvent.LogAction(LogType.LOG, a);
    }
}
