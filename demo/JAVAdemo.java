import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class PackBase
{
    public long qq;
}

class StartPack {
    public String Name;
    public List<Integer> Reg;
}

class SendFriendImagePack extends PackBase{
    public long id;
    public String img;
}

class SendFriendMessagePack extends PackBase{
    public long id;
    public List<String> message;
}

class SendGroupMessagePack extends PackBase{
    public long id;
    public List<String> message;
}

class SendGroupPrivateMessagePack extends PackBase{
    public long id;
    public long fid;
    public List<String> message;
}

class SendGroupImagePack extends PackBase{
    public long id;
    public String img;
}

class SendGroupPrivateImagePack extends PackBase{
    public long id;
    public long fid;
    public String img;
}

class GroupMessageEventPack extends PackBase{
    public long id;
    public long fid;
    public String name;
    public List<String> message;
}

class TempMessageEventPack extends GroupMessageEventPack {
    public int time;
}

class FriendMessageEventPack extends PackBase{
    public long id;
    public String name;
    public List<String> message;
    public int time;
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
        temp += "qq=" + qq + "&";
        temp += "img=" + img;
        String str = temp + " ";
        byte[] temp1 = str.getBytes(StandardCharsets.UTF_8);
        temp1[temp1.length - 1] = (byte) index;
        return temp1;
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

class ServerMain {
    public static void LogError(Exception e) {
        String a = "[错误]";
        System.out.println(a);
        e.printStackTrace();
    }

    public static void LogError(String a) {
        a = "[错误]" + a;
        System.out.println(a);
    }

    public static void LogOut(String a) {
        a = "[信息]" + a;
        System.out.println(a);
    }
}

public class RobotSocket {
    private static Socket Socket;
    private static Thread ReadThread;
    private static Thread DoThread;
    private static boolean IsRun;
    private static boolean IsConnect;
    private static List<RobotTask> QueueRead;
    private static List<byte[]> QueueSend;
    private static final StartPack PackStart = new StartPack() {{
        Name = "ColoryrSDK";
        Reg = new ArrayList<>() {{
            add(49);
            add(50);
            add(51);
        }};
    }};

    public static void Start() {
        QueueRead = new CopyOnWriteArrayList<>();
        QueueSend = new CopyOnWriteArrayList<>();

        DoThread = new Thread(() -> {
            RobotTask task;
            while (IsRun) {
                try {
                    if (!QueueRead.isEmpty()) {
                        task = QueueRead.remove(0);
                        switch (task.index) {
                            case 49:
                                var pack = JSON.parseObject(task.data, GroupMessageEventPack.class);
                                System.out.println("qq = " + pack.qq);
                                System.out.println("id = " + pack.id);
                                System.out.println("fid = " + pack.fid);
                                System.out.println("name = " + pack.name);
                                System.out.println("message = ");
                                for (var item : pack.message) {
                                    System.out.println(item);
                                }
                                System.out.println();
                                break;
                            case 50:
                                var pack1 = JSON.parseObject(task.data, TempMessageEventPack.class);
                                System.out.println("qq = " + pack1.qq);
                                System.out.println("id = " + pack1.id);
                                System.out.println("fid = " + pack1.fid);
                                System.out.println("name = " + pack1.name);
                                System.out.println("message = ");
                                for (var item : pack1.message) {
                                    System.out.println(item);
                                }
                                System.out.println();
                                break;
                            case 51:
                                var pack2 = JSON.parseObject(task.data, FriendMessageEventPack.class);
                                System.out.println("qq = " + pack2.qq);
                                System.out.println("id = " + pack2.id);
                                System.out.println("time = " + pack2.time);
                                System.out.println("name = " + pack2.name);
                                System.out.println("message = ");
                                for (var item : pack2.message) {
                                    System.out.println(item);
                                }
                                System.out.println();
                                break;
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    ServerMain.LogError(e);
                }
            }
        });
        ReadThread = new Thread(() -> {
            try {
                while (!IsRun) {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                ServerMain.LogError(e);
            }
            DoThread.start();
            byte[] data;
            while (IsRun) {
                try {
                    if (!IsConnect) {
                        ReConnect();
                    } else if (Socket.getInputStream().available() > 0) {
                        data = new byte[Socket.getInputStream().available()];
                        Socket.getInputStream().read(data);
                        var type = data[data.length - 1];
                        data[data.length - 1] = 0;
                        QueueRead.add(new RobotTask(type, new String(data)));
                    } else if (Socket.getInputStream().available() < 0) {
                        ServerMain.LogOut("机器人连接中断");
                        IsConnect = false;
                    } else if (!QueueSend.isEmpty()) {
                        data = QueueSend.remove(0);
                        Socket.getOutputStream().write(data);
                        Socket.getOutputStream().flush();
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    ServerMain.LogError("机器人连接失败");
                    ServerMain.LogError(e);
                    IsConnect = false;
                    ServerMain.LogError("机器人20秒后重连");
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    ServerMain.LogError("机器人重连中");
                }
            }
        });
        ReadThread.start();
        IsRun = true;
        ReadTest();
    }

    private static boolean is() {
        try {
            Socket.sendUrgentData(60);
            return false;
        } catch (Exception ex) {
            return true;
        }
    }

    private static void ReadTest() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            SendGroupMessage(1092415357, 571239090, new ArrayList<>() {{
                add(scanner.nextLine());
            }});
        }
    }

    private static void ReConnect() {
        try {
            if (Socket != null)
                Socket.close();
            Socket = new Socket("127.0.0.1", 23333);

            var data = (JSON.toJSON(PackStart) + " ").getBytes(StandardCharsets.UTF_8);
            data[data.length - 1] = 0;

            Socket.getOutputStream().write(data);
            Socket.getOutputStream().flush();

            QueueRead.clear();
            QueueSend.clear();
            ServerMain.LogOut("机器人已连接");
            IsConnect = true;
        } catch (Exception e) {
            ServerMain.LogError("机器人连接失败");
            ServerMain.LogError(e);
        }
    }

    public static void SendGroupMessage(long qq_, long id_, List<String> message_) {
        var data = BuildPack.Build(new SendGroupMessagePack() {{
            qq = qq_;
            id = id_;
            message = message_;
        }}, 52);
        QueueSend.add(data);
    }

    public static void SendGroupPrivateMessage(long qq_, long id_, long fid_, List<String> message_) {
        var data = BuildPack.Build(new SendGroupPrivateMessagePack() {{
            qq = qq_;
            id = id_;
            fid = fid_;
            message = message_;
        }}, 53);
        QueueSend.add(data);
    }

    public static void SendFriendMessage(long qq_, long id_, List<String> message_) {
        var data = BuildPack.Build(new SendFriendMessagePack() {{
            qq = qq_;
            id = id_;
            message = message_;
        }}, 54);
        QueueSend.add(data);
    }

    public static void SendGroupImage(long qq, long id, String img) {
        var data = BuildPack.BuildImage(qq, id, 0, img, 61);
        QueueSend.add(data);
    }

    public static void SendGroupPrivateImage(long qq, long id, long fid, String img) {
        var data = BuildPack.BuildImage(qq, id, fid, img, 62);
        QueueSend.add(data);
    }

    public static void SendFriendImage(long qq, long id, String img) {
        var data = BuildPack.BuildImage(qq, id, 0, img, 63);
        QueueSend.add(data);
    }

    public static void Stop() {
        ServerMain.LogOut("机器人正在断开");
        IsRun = false;
        if (Socket != null) {
            try {
                Socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ServerMain.LogOut("机器人已断开");
    }
}
