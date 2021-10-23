package coloryr.colormirai.demo.RobotSDK;

import coloryr.colormirai.demo.RobotSDK.api.ICall;
import coloryr.colormirai.demo.RobotSDK.api.ILog;
import coloryr.colormirai.demo.RobotSDK.api.IState;
import coloryr.colormirai.demo.RobotSDK.enums.LogType;
import coloryr.colormirai.demo.RobotSDK.enums.StateType;
import coloryr.colormirai.demo.RobotSDK.pack.PackBase;
import coloryr.colormirai.demo.RobotSDK.pack.from.StartPack;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

//需要使用JAVA8环境
//需要安装fastjson库

class RobotTask {
    public byte index;
    public String data;

    public RobotTask(byte type, String s) {
        index = type;
        data = s;
    }
}

public class BaseRobot {

    /**
     * 机器人登录的QQ号列表
     */
    public List<Long> QQs;
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
    public void Set(RobotConfig config) {
        this.config = config;

        robotCallEvent = config.CallAction;
        robotLogEvent = config.LogAction;
        robotStateEvent = config.StateAction;

        packStart = new StartPack() {{
            Name = config.Name;
            Reg = config.Pack;
            Groups = config.Groups;
            QQs = config.QQs;
            RunQQ = config.RunQQ;
        }};
    }

    /**
     * 启动机器人
     */
    public void Start() {
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
                        if (RobotSDK.PackType.containsKey(task.index)) {
                            Class<? extends PackBase> class1 = RobotSDK.PackType.get(task.index);
                            robotCallEvent.CallAction(task.index, JSON.parseObject(task.data, class1));
                        } else {
                            LogError("不认识的数据包" + task.index);
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    LogError(e);
                }
            }
        });

        readThread = new Thread(() -> {
            try {
                while (!isRun) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                LogError(e);
            }
            doThread.start();
            int time = 0;
            byte[] data;
            while (isRun) {
                try {
                    if (!isConnect) {
                        ReConnect();
                        isFirst = false;
                        times = 0;
                        robotStateEvent.StateAction(StateType.Connect);
                    } else if (socket.getInputStream().available() > 0) {
                        data = new byte[socket.getInputStream().available()];
                        socket.getInputStream().read(data);
                        byte type = data[data.length - 1];
                        data[data.length - 1] = 0;
                        queueRead.add(new RobotTask(type, new String(data, StandardCharsets.UTF_8)));
                    } else if (config.Check && time >= 20) {
                        time = 0;
                        byte[] temp = BuildPack.Build(new Object(), 60);
                        addTask(temp);
                    } else if ((data = queueSend.poll()) != null) {
                        socket.getOutputStream().write(data);
                        socket.getOutputStream().flush();
                    }
                    time++;
                    Thread.sleep(50);
                } catch (Exception e) {
                    isConnect = false;
                    robotStateEvent.StateAction(StateType.Disconnect);
                    if (isFirst) {
                        isRun = false;
                        LogError("机器人连接失败");
                        LogError(e);
                    } else {
                        times++;
                        if (times == 10) {
                            isRun = false;
                            LogError("重连失败次数过多");
                        }
                        LogError("机器人连接失败");
                        LogError(e);
                        isConnect = false;
                        LogError("机器人" + config.Time + "毫秒后重连");
                        try {
                            Thread.sleep(config.Time);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        LogError("机器人重连中");
                    }
                }
            }
        });
        readThread.start();
        isRun = true;
    }

    private void ReConnect() {
        try {
            if (socket != null)
                socket.close();

            robotStateEvent.StateAction(StateType.Connecting);

            socket = new Socket(config.IP, config.Port);

            byte[] data = (JSON.toJSON(packStart) + " ").getBytes(StandardCharsets.UTF_8);
            data[data.length - 1] = 0;

            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();

            while (socket.getInputStream().available() == 0) {
                Thread.sleep(10);
            }

            data = new byte[socket.getInputStream().available()];
            socket.getInputStream().read(data);
            QQs = JSON.parseArray(new String(data, StandardCharsets.UTF_8), Long.class);

            queueRead.clear();
            queueSend.clear();
            LogOut("机器人已连接");
            isConnect = true;
        } catch (Exception e) {
            LogError("机器人连接失败");
            LogError(e);
        }
    }

    private void SendStop() {
        try {
            byte[] data = BuildPack.Build(new Object(), 127);
            socket.getOutputStream().write(data);
        } catch (Exception e) {
            LogOut("机器人断开错误");
            e.printStackTrace();
        }
    }

    /**
     * 停止机器人
     */
    public void Stop() {
        LogOut("机器人正在断开");
        if (isConnect)
            SendStop();
        isRun = false;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LogOut("机器人已断开");
    }

    /**
     * 添加数据包
     *
     * @param data 数据包
     */
    public void addTask(byte[] data) {
        queueSend.add(data);
    }

    private void LogError(Exception e) {
        robotLogEvent.LogAction(LogType.Error, "机器人错误\n" + e.toString());
    }

    private void LogError(String a) {
        robotLogEvent.LogAction(LogType.Error, "机器人错误:" + a);
    }

    private void LogOut(String a) {
        robotLogEvent.LogAction(LogType.Log, a);
    }
}
