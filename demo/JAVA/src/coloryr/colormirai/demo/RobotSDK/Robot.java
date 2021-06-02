package coloryr.colormirai.demo.RobotSDK;

import coloryr.colormirai.demo.RobotSDK.pack.from.StartPack;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//需要使用JAVA8环境
//需要安装fastjson库

public class Robot {
    public List<Long> QQs;
    public boolean IsRun;
    public boolean IsConnect;
    public boolean IsFirst = true;
    private ICall RobotCallEvent;
    private ILog RobotLogEvent;
    private IState RobotStateEvent;
    private Socket Socket;
    private Thread ReadThread;
    private Thread DoThread;
    private List<RobotTask> QueueRead;
    private List<byte[]> QueueSend;
    private StartPack StartPack;
    private RobotConfig Config;
    private int Times = 0;

    public void Set(RobotConfig Config) {
        this.Config = Config;

        RobotCallEvent = Config.CallAction;
        RobotLogEvent = Config.LogAction;
        RobotStateEvent = Config.StateAction;

        StartPack = new StartPack() {{
            Name = Config.Name;
            Reg = Config.Pack;
            Groups = Config.Groups;
            QQs = Config.QQs;
            RunQQ = Config.RunQQ;
        }};
    }

    public void Start() {
        if (ReadThread != null && ReadThread.isAlive())
            return;
        QueueRead = new CopyOnWriteArrayList<>();
        QueueSend = new CopyOnWriteArrayList<>();

        DoThread = new Thread(() -> {
            RobotTask task;
            int count = 0;
            while (IsRun) {
                try {
                    if (!QueueRead.isEmpty()) {
                        task = QueueRead.remove(0);
                        RobotCallEvent.CallAction(task.index, task.data);
                    }
                    Thread.sleep(10);
                    if (Config.Check) {
                        count++;
                        if (count >= 30000) {
                            count = 0;
                            QueueSend.add(BuildPack.Build(new Object(), 60));
                        }
                    }
                } catch (Exception e) {
                    LogError(e);
                }
            }
        });
        ReadThread = new Thread(() -> {
            try {
                while (!IsRun) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                LogError(e);
            }
            DoThread.start();
            byte[] data;
            while (IsRun) {
                try {
                    if (!IsConnect) {
                        ReConnect();
                        IsFirst = false;
                        Times = 0;
                        RobotStateEvent.StateAction(StateType.Connect);
                    } else if (Socket.getInputStream().available() > 0) {
                        data = new byte[Socket.getInputStream().available()];
                        Socket.getInputStream().read(data);
                        byte type = data[data.length - 1];
                        data[data.length - 1] = 0;
                        QueueRead.add(new RobotTask(type, new String(data, StandardCharsets.UTF_8)));
                    } else if (Socket.getInputStream().available() < 0) {
                        LogOut("机器人连接中断");
                        RobotStateEvent.StateAction(StateType.Disconnect);
                        IsConnect = false;
                    } else if (!QueueSend.isEmpty()) {
                        data = QueueSend.remove(0);
                        Socket.getOutputStream().write(data);
                        Socket.getOutputStream().flush();
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    if (IsFirst) {
                        IsRun = false;
                        LogError("机器人连接失败");
                        LogError(e);
                    } else {
                        Times++;
                        if (Times == 10) {
                            IsRun = false;
                            LogError("重连失败次数过多");
                        }
                        LogError("机器人连接失败");
                        LogError(e);
                        IsConnect = false;
                        LogError("机器人" + Config.Time + "毫秒后重连");
                        try {
                            Thread.sleep(Config.Time);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        LogError("机器人重连中");
                    }
                }
            }
        });
        ReadThread.start();
        IsRun = true;
    }

    private void ReConnect() {
        try {
            if (Socket != null)
                Socket.close();

            RobotStateEvent.StateAction(StateType.Connecting);

            Socket = new Socket(Config.IP, Config.Port);

            byte[] data = (JSON.toJSON(StartPack) + " ").getBytes(StandardCharsets.UTF_8);
            data[data.length - 1] = 0;

            Socket.getOutputStream().write(data);
            Socket.getOutputStream().flush();

            while (Socket.getInputStream().available() == 0) {
                Thread.sleep(10);
            }

            data = new byte[Socket.getInputStream().available()];
            Socket.getInputStream().read(data);
            QQs = JSON.parseArray(new String(data, StandardCharsets.UTF_8), Long.class);

            QueueRead.clear();
            QueueSend.clear();
            LogOut("机器人已连接");
            IsConnect = true;
        } catch (Exception e) {
            LogError("机器人连接失败");
            LogError(e);
        }
    }

    private void SendStop() {
        try {
            byte[] data = BuildPack.Build(new Object(), 127);
            Socket.getOutputStream().write(data);
        } catch (Exception e) {
            LogOut("机器人断开错误");
            e.printStackTrace();
        }
    }

    public void Stop() {
        LogOut("机器人正在断开");
        if (IsConnect)
            SendStop();
        IsRun = false;
        if (Socket != null) {
            try {
                Socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LogOut("机器人已断开");
    }

    public void addTask(byte[] data) {
        QueueSend.add(data);
    }

    private void LogError(Exception e) {
        RobotLogEvent.LogAction(LogType.Error, "机器人错误\n" + e.toString());
    }

    private void LogError(String a) {
        RobotLogEvent.LogAction(LogType.Error, "机器人错误:" + a);
    }

    private void LogOut(String a) {
        RobotLogEvent.LogAction(LogType.Log, a);
    }
}
