import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

class StartPack {
    private String Name;
    private List<Integer> Reg;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Integer> getReg() {
        return Reg;
    }

    public void setReg(List<Integer> reg) {
        Reg = reg;
    }
}

class SendFriendImagePack {
    private long id;
    private String img;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

class SendFriendMessagePack {
    private long id;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class SendGroupMessagePack {
    private long id;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class SendGroupPrivateMessagePack {
    private long id;
    private long fid;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}

class SendGroupImagePack {
    private long id;
    private String img;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

class SendGroupPrivateImagePack {
    private long id;
    private long fid;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}

class GroupMessageEventPack {
    private long id;
    private long fid;
    private String name;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class TempMessageEventPack extends GroupMessageEventPack {
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}

class FriendMessageEventPack {
    private long id;
    private String name;
    private String message;
    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class BuildPack {
    public static byte[] Build(Object data, int index) {
        String str = JSON.toJSONString(data) + " ";
        byte[] temp = str.getBytes(StandardCharsets.UTF_8);
        temp[temp.length - 1] = (byte) index;
        return temp;
    }
}

class RobotTask {
    private byte index;
    private String data;

    public RobotTask(byte index, String data)
    {
        this.index = index;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public byte getIndex() {
        return index;
    }

    public void setIndex(byte index) {
        this.index = index;
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
        setName("ColoryrSDK");
        setReg(new ArrayList<>() {{
            add(49);
            add(50);
            add(51);
        }});
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
                        switch (task.getIndex()) {
                            case 49:
                                var pack = JSON.parseObject(task.getData(), GroupMessageEventPack.class);
                                System.out.println("id = " + pack.getId());
                                System.out.println("fid = " + pack.getFid());
                                System.out.println("name = " + pack.getName());
                                System.out.println("message = " + pack.getMessage());
                                System.out.println();
                                break;
                            case 50:
                                var pack1 = JSON.parseObject(task.getData(), TempMessageEventPack.class);
                                System.out.println("id = " + pack1.getId());
                                System.out.println("fid = " + pack1.getFid());
                                System.out.println("name = " + pack1.getName());
                                System.out.println("message = " + pack1.getMessage());
                                System.out.println();
                                break;
                            case 51:
                                var pack2 = JSON.parseObject(task.getData(), FriendMessageEventPack.class);
                                System.out.println("id = " + pack2.getId());
                                System.out.println("time = " + pack2.getTime());
                                System.out.println("name = " + pack2.getName());
                                System.out.println("message = " + pack2.getMessage());
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
            SendGroupMessage(571239090, scanner.nextLine());
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

    public static void SendGroupMessage(long id, String message) {
        var data = BuildPack.Build(new SendGroupMessagePack() {{
            setId(id);
            setMessage(message);
        }}, 52);
        QueueSend.add(data);
    }

    public static void SendGroupPrivateMessage(long id, long fid, String message) {
        var data = BuildPack.Build(new SendGroupPrivateMessagePack() {{
            setId(id);
            setFid(fid);
            setMessage(message);
        }}, 53);
        QueueSend.add(data);
    }

    public static void SendFriendMessage(long id, String message) {
        var data = BuildPack.Build(new SendFriendMessagePack() {{
            setId(id);
            setMessage(message);
        }}, 54);
        QueueSend.add(data);
    }

    public static void SendGroupImage(long id, String img) {
        var data = BuildPack.Build(new SendGroupImagePack() {{
        }}, 61);
        QueueSend.add(data);
    }

    public static void SendGroupPrivateImage(long id, long fid, String img) {
        var data = BuildPack.Build(new SendGroupPrivateImagePack() {{
            setFid(fid);
            setId(id);
            setImg(img);
        }}, 62);
        QueueSend.add(data);
    }

    public static void SendFriendImage(long id, String img) {
        var data = BuildPack.Build(new SendFriendImagePack() {{
            setId(id);
            setImg(img);
        }}, 63);
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
