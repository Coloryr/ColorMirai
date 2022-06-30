package coloryr.colormirai.demo.sdk.pipe;

import coloryr.colormirai.demo.sdk.*;
import coloryr.colormirai.demo.sdk.api.IColorMiraiPipe;
import coloryr.colormirai.demo.sdk.enums.LogType;
import coloryr.colormirai.demo.sdk.enums.StateType;
import coloryr.colormirai.demo.sdk.pack.PackBase;
import coloryr.colormirai.demo.sdk.pack.from.*;
import com.alibaba.fastjson.JSON;

import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ColorMiraiSocket implements IColorMiraiPipe {

    private Queue<byte[]> queue;
    private Socket socket;
    private RobotBase robot;
    private Thread thread;

    public ColorMiraiSocket(RobotBase robot) {
        this.robot = robot;
        queue = new ConcurrentLinkedDeque<>();
        thread = new Thread(this::read);
    }

    private RobotTask readPack() {
        try {
            PushbackInputStream input = new PushbackInputStream(socket.getInputStream(), 1);
            while (true) {
                int bytesToRead = input.available();
                if (bytesToRead > 0) {
                    byte[] data = new byte[bytesToRead];
                    input.read(data);
                    byte index = data[bytesToRead - 1];
                    String line = new String(data, 0, bytesToRead - 1);
                    return new RobotTask(index, line);
                } else {
                    int b = input.read(); //此操作会阻塞，直到有数据被读到
                    if (b < 0) {
                        return null;
                    }
                    input.unread(b);
                }
            }
        } catch (Exception e) {
            if (socket.isClosed()) {
                return new RobotTask((byte) -1, "");
            }
            robot.robotLogEvent.LogAction(LogType.ERROR, "通信出现问题");
            e.printStackTrace();
            robot.stop();
        }
        return null;
    }

    private void send(byte[] data) {
        try {
            if (!socket.isConnected() || socket.isOutputShutdown())
                return;
            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();
        } catch (Exception e) {
            robot.robotLogEvent.LogAction(LogType.LOG, "通信出现问题");
            e.printStackTrace();
        }
    }

    private void read() {
        byte[] data;
        int time = 0;
        while (robot.isRun) {
            try {
                if (!robot.isConnect) {
                    reConnect();
                    robot.isFirst = false;
                    robot.times = 0;
                    robot.robotStateEvent.StateAction(StateType.CONNECT);
                } else if (socket.getInputStream().available() > 0) {
                    RobotTask pack = readPack();
                    if (pack == null)
                        continue;
                    if (RobotPack.packType.containsKey(pack.index)) {
                        Class<? extends PackBase> class1 = RobotPack.packType.get(pack.index);
                        robot.addRead(JSON.parseObject((String) pack.data, class1), pack.index);
                    }
                } else if (robot.config.check && time >= 20) {
                    time = 0;
                    addSend(null, (byte) 60);
                } else if ((data = queue.poll()) != null) {
                    send(data);
                }
                time++;
                Thread.sleep(50);
            } catch (Exception e) {
                robot.isConnect = false;
                robot.robotStateEvent.StateAction(StateType.DISCONNECT);
                if (robot.isFirst) {
                    robot.isRun = false;
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人连接失败");
                } else {
                    robot.times++;
                    if (robot.times == 10) {
                        robot.isRun = false;
                        robot.robotLogEvent.LogAction(LogType.ERROR, "重连失败次数过多");
                    }
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人连接失败");
                    e.printStackTrace();
                    robot.isConnect = false;
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人" + robot.config.time + "毫秒后重连");
                    try {
                        Thread.sleep(robot.config.time);
                    } catch (Exception e1) {

                    }
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人重连中");
                }
            }
        }
    }

    @Override
    public void addSend(PackBase pack, byte index) {
        byte[] temp;
        if (index == 60) {
            temp = BuildPack.build(new Object(), 60);
        } else if (index == 61) {
            SendGroupImagePack pack1 = (SendGroupImagePack) pack;
            temp = BuildPack.buildImage(pack1.qq, pack1.id, 0, RobotUtils.base64E(pack1.data), 61, pack1.ids);
        } else if (index == 62) {
            SendGroupPrivateImagePack pack1 = (SendGroupPrivateImagePack) pack;
            temp = BuildPack.buildImage(pack1.qq, pack1.id, pack1.fid, RobotUtils.base64E(pack1.data), 62, null);
        } else if (index == 63) {
            SendFriendImagePack pack1 = (SendFriendImagePack) pack;
            temp = BuildPack.buildImage(pack1.qq, pack1.id, 0, RobotUtils.base64E(pack1.data), 63, pack1.ids);
        } else if (index == 74) {
            SendFriendImagePack pack1 = (SendFriendImagePack) pack;
            temp = BuildPack.buildSound(pack1.qq, pack1.id, RobotUtils.base64E(pack1.data), (byte) 74, pack1.ids);
        } else if (index == 95) {
            MessageBuffPack pack1 = (MessageBuffPack) pack;
            pack1.imgData = null;
            temp = BuildPack.build(pack1, 95);
        } else if (index == 126) {
            SendFriendSoundPack pack1 = (SendFriendSoundPack) pack;
            temp = BuildPack.buildSound(pack1.qq, pack1.id, RobotUtils.base64E(pack1.data), (byte) 126, pack1.ids);
        } else {
            temp = BuildPack.build(pack, index);
        }

        queue.add(temp);
    }

    @Override
    public void reConnect() throws Exception {
        if (socket != null)
            socket.close();

        robot.robotStateEvent.StateAction(StateType.CONNECTING);

        socket = new Socket();
        socket.connect(new InetSocketAddress(robot.config.ip, robot.config.port));

        byte[] data = (JSON.toJSON(robot.packStart) + " ").getBytes(StandardCharsets.UTF_8);
        data[data.length - 1] = 0;

        send(data);

        RobotTask task;
        while ((task = readPack()) == null) {
            Thread.sleep(10);
        }

        robot.qqs = JSON.parseArray((String) task.data, Long.class);

        queue.clear();
        robot.robotLogEvent.LogAction(LogType.LOG, "机器人已连接");
        robot.isConnect = true;
    }

    @Override
    public void sendStop() {
        if (!robot.isConnect)
            return;
        byte[] data = BuildPack.build(new Object(), 127);
        send(data);
    }

    @Override
    public void stop() {
        try {
            sendStop();
            if (socket != null)
                socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startRead() {
        thread.start();
    }
}
