import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//需要使用JAVA8环境
//需要安装fastjson库

enum LogType {
    Log, Error
}

enum StateType {
    Disconnect, Connecting, Connect
}

interface ICall {
    void CallAction(byte type, String data);
}

interface ILog {
    void LogAction(LogType type, String data);
}

interface IState {
    void StateAction(StateType type);
}

class StartPack {
    public String Name;
    public List<Byte> Reg;
    public List<Long> Groups;
    public List<Long> QQs;
    public long RunQQ;
}

abstract class PackBase {
    public long qq;
}

class FriendMessageEventPack extends PackBase {
    public long id;
    public String name;
    public List<String> message;
    public int time;
}

class GroupMessageEventPack extends PackBase {
    public long id;
    public long fid;
    public String name;
    public List<String> message;
}

class TempMessageEventPack extends GroupMessageEventPack {
    public int time;
}

class EventCallPack extends PackBase {
    public long eventid;
    public int dofun;
    public List<Object> arg;
}

class SendFriendMessagePack extends PackBase {
    public long id;
    public List<String> message;
}

class SendGroupMessagePack extends PackBase {
    public long id;
    public List<String> message;
}

class SendGroupPrivateMessagePack extends PackBase {
    public long id;
    public long fid;
    public List<String> message;
}

class SendFriendImagePack extends PackBase {
    public long id;
    public String img;
}

class GroupMuteAll extends PackBase {
    public long id;
}

class GroupUnmuteAll extends PackBase
{
    public long id;
}

class SetGroupMemberCard extends PackBase {
    public long id;
    public long fid;
    public String card;
}

class SetGroupName extends PackBase {
    public long id;
    public String name;
}

class NewFriendRequestEventPack extends PackBase {
    public long id;
    public long fid;
    public String name;
    public String message;
    public long eventid;
}

class ReCallMessage extends PackBase {
    public long id;
}

class LoadFileSendToGroupImagePack extends PackBase {
    public long id;
    public String file;
}

class GroupMessagePostSendEventPack extends PackBase {
    public long id;
    public boolean res;
    public List<String> message;
    public String error;
}

class MemberNudgePack extends PackBase {
    public long id;
    public long fid;
}

class FriendNudgePack extends PackBase {
    public long id;
}

class GetImageUrlPack extends PackBase {
    public String uuid;
}

class ReImagePack extends PackBase {
    public String uuid;
    public String url;
}

class MessageBuffPack extends PackBase {
    public boolean send;
    public List<String> text;
    public String img;
    public String imgurl;
    public int type;
    public long id;
    public long fid;
}

class BuildPack {
    public static byte[] Build(Object data, int index) {
        String str = JSON.toJSONString(data) + " ";
        byte[] temp = str.getBytes(StandardCharsets.UTF_8);
        temp[temp.length - 1] = (byte) index;
        return temp;
    }

    public static byte[] BuildImage(long qq, long id, long fid, String img, int index) {
        String temp = "";
        if (id != 0) {
            temp += "id=" + id + "&";
        }
        if (fid != 0) {
            temp += "fid=" + fid + "&";
        }
        temp += "qq=" + qq + "&" + "img=" + img + " ";
        byte[] temp1 = temp.getBytes(StandardCharsets.UTF_8);
        temp1[temp1.length - 1] = (byte) index;
        return temp1;
    }

    public static byte[] BuildSound(long qq, long id, String sound, byte index) {
        String temp = "id=" + id + "&qq=" + qq + "&sound=" + sound + " ";
        byte[] data = temp.getBytes(StandardCharsets.UTF_8);
        data[data.length - 1] = index;
        return data;
    }

    public static byte[] BuildBuffImage(long qq, long id, long fid, int type, String img, boolean send) {
        String temp = "";
        if (id != 0) {
            temp += "id=" + id + "&";
        }
        if (fid != 0) {
            temp += "fid=" + fid + "&";
        }
        temp += "qq=" + qq + "&img=" + img + "&type=" + type + "&send=" + send + " ";
        byte[] data = temp.getBytes(StandardCharsets.UTF_8);
        data[data.length - 1] = 97;
        return data;
    }
}

class RobotTask {
    public byte index;
    public String data;

    public RobotTask(byte type, String s) {
        index = type;
        data = s;
    }
}

class RobotConfig {
    public String IP;
    public int Port;
    public List<Byte> Pack;
    public String Name;
    public List<Long> Groups;
    public List<Long> QQs;
    public long RunQQ;
    public int Time;
    public boolean Check;
    public ICall CallAction;
    public ILog LogAction;
    public IState StateAction;
}

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
    private StartPack PackStart;
    private RobotConfig Config;
    private int Times = 0;

    public void Set(RobotConfig Config) {
        this.Config = Config;

        RobotCallEvent = Config.CallAction;
        RobotLogEvent = Config.LogAction;
        RobotStateEvent = Config.StateAction;

        PackStart = new StartPack() {{
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
            while (IsRun) {
                try {
                    if (!QueueRead.isEmpty()) {
                        task = QueueRead.remove(0);
                        RobotCallEvent.CallAction(task.index, task.data);
                    }
                    Thread.sleep(10);
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

            byte[] data = (JSON.toJSON(PackStart) + " ").getBytes(StandardCharsets.UTF_8);
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

    public void SendGroupMessage(long qq_, long id_, List<String> message_) {
        byte[] data = BuildPack.Build(new SendGroupMessagePack() {{
            qq = qq_;
            id = id_;
            message = message_;
        }}, 52);
        QueueSend.add(data);
    }

    public void SendGroupPrivateMessage(long qq_, long id_, long fid_, List<String> message_) {
        byte[] data = BuildPack.Build(new SendGroupPrivateMessagePack() {{
            qq = qq_;
            id = id_;
            fid = fid_;
            message = message_;
        }}, 53);
        QueueSend.add(data);
    }

    public void SendFriendMessage(long qq_, long id_, List<String> message_) {
        byte[] data = BuildPack.Build(new SendFriendMessagePack() {{
            qq = qq_;
            id = id_;
            message = message_;
        }}, 54);
        QueueSend.add(data);
    }

    public void SendGroupImage(long qq, long id, String img) {
        byte[] data = BuildPack.BuildImage(qq, id, 0, img, 61);
        QueueSend.add(data);
    }

    public void SendGroupPrivateImage(long qq, long id, long fid, String img) {
        byte[] data = BuildPack.BuildImage(qq, id, fid, img, 62);
        QueueSend.add(data);
    }

    public void SendFriendImage(long qq, long id, String img) {
        byte[] data = BuildPack.BuildImage(qq, id, 0, img, 63);
        QueueSend.add(data);
    }

    public void SendMemberNudge(long qq_, long id_, long fid_) {
        byte[] data = BuildPack.Build(new MemberNudgePack() {{
            qq = qq_;
            id = id_;
            fid = fid_;
        }}, 84);
        QueueSend.add(data);
    }

    public void SendFriendNudge(long qq_, long id_) {
        byte[] data = BuildPack.Build(new FriendNudgePack() {{
            qq = qq_;
            id = id_;
        }}, 83);
        QueueSend.add(data);
    }

    public void GetImageUrl(long qq_, String uuid_) {
        byte[] data = BuildPack.Build(new GetImageUrlPack() {{
            qq = qq_;
            uuid = uuid_;
        }}, 90);
        QueueSend.add(data);
    }

    public void AddMessageBuff(long qq_, long id_, long fid_, List<String> text_, String imgurl_, int type_, boolean send_) {
        byte[] data = BuildPack.Build(new MessageBuffPack() {{
            qq = qq_;
            send = send_;
            text = text_;
            imgurl = imgurl_;
            type = type_;
            fid = fid_;
            id = id_;
        }}, 97);
        QueueSend.add(data);
    }

    public void AddMessageImageBuff(long qq, long id, long fid, String img, int type, boolean send) {
        byte[] data = BuildPack.BuildBuffImage(qq, id, fid, type, img, send);
        QueueSend.add(data);
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
