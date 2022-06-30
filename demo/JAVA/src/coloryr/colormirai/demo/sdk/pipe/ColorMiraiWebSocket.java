package coloryr.colormirai.demo.sdk.pipe;

import coloryr.colormirai.demo.sdk.RobotBase;
import coloryr.colormirai.demo.sdk.RobotPack;
import coloryr.colormirai.demo.sdk.RobotTask;
import coloryr.colormirai.demo.sdk.api.IColorMiraiPipe;
import coloryr.colormirai.demo.sdk.enums.LogType;
import coloryr.colormirai.demo.sdk.enums.StateType;
import coloryr.colormirai.demo.sdk.pack.PackBase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ColorMiraiWebSocket implements IColorMiraiPipe {
    private static class PackTask {
        public PackBase pack;
        public byte index;
    }
    private final Queue<RobotTask> queue1;
    private final Queue<String> queue2;
    private final RobotBase robot;
    private final Thread thread;
    private WebSocketClient client;

    public ColorMiraiWebSocket(RobotBase robot) {
        this.robot = robot;
        queue1 = new ConcurrentLinkedDeque<>();
        queue2 = new ConcurrentLinkedDeque<>();
        thread = new Thread(this::read);
    }

    private void read() {
        int time = 0;
        String item;
        RobotTask item1;
        while (robot.isRun) {
            try {
                Thread.sleep(50);
                if (!robot.isConnect) {
                    reConnect();
                    robot.isFirst = false;
                    robot.times = 0;
                    robot.robotStateEvent.StateAction(StateType.CONNECT);
                } else if ((item = queue2.poll()) != null) {
                    JSONObject obj = JSON.parseObject(item);
                    byte index = (byte) obj.getByteValue("index");
                    if (index == 60)
                        continue;
                    JSONObject obj1 = obj.getJSONObject("pack");
                    if (RobotPack.packType.containsKey(index)) {
                        Class<? extends PackBase> class1 = RobotPack.packType.get(index);
                        robot.addRead(obj1.toJavaObject(class1), index);
                    }
                } else if (robot.config.check && time >= 20) {
                    time = 0;
                    addSend(null, (byte) 60);
                } else if ((item1 = queue1.poll()) != null) {
                    client.send(JSON.toJSONString(item1));
                }
                time++;
            } catch (Exception e) {
                robot.isConnect = false;
                robot.robotStateEvent.StateAction(StateType.DISCONNECT);
                e.printStackTrace();
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
                    } catch (Exception exception) {
                    }
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人重连中");
                }
            }
        }
    }

    @Override
    public void addSend(PackBase pack, byte index) {
        queue1.add(new RobotTask(index, pack));
    }

    @Override
    public void reConnect() throws Exception {
        if (client != null)
            client.close();

        queue2.clear();

        robot.robotStateEvent.StateAction(StateType.CONNECTING);

        String temp = JSON.toJSONString(new PackTask(){{
            index = 0;
            pack = robot.packStart;
        }});

        client = new WebSocketClient(new URI(robot.config.ip)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {

            }

            @Override
            public void onMessage(String s) {
                queue2.add(s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                robot.isConnect = false;
                robot.robotStateEvent.StateAction(StateType.DISCONNECT);
            }

            @Override
            public void onError(Exception e) {

            }
        };
        client.connectBlocking();
        client.send(temp);

        while ((temp = queue2.poll()) == null) {
            Thread.sleep(10);
        }

        robot.qqs = JSON.parseArray(temp, Long.class);

        queue1.clear();
        robot.robotLogEvent.LogAction(LogType.LOG, "机器人已连接");
        robot.isConnect = true;
    }

    @Override
    public void sendStop() {
        client.send(JSON.toJSONString(new RobotTask((byte) 127, null)));
    }

    @Override
    public void stop() {
        client.close();
    }

    @Override
    public void startRead() {
        thread.start();
    }
}
