package coloryr.colormirai.demo;

import coloryr.colormirai.demo.sdk.RobotConfig;
import coloryr.colormirai.demo.sdk.RobotTop;
import coloryr.colormirai.demo.sdk.enums.FriendCallType;
import coloryr.colormirai.demo.sdk.enums.LogType;
import coloryr.colormirai.demo.sdk.enums.StateType;
import coloryr.colormirai.demo.sdk.pack.PackBase;
import coloryr.colormirai.demo.sdk.pack.re.ReFriendInfoPack;
import coloryr.colormirai.demo.sdk.pack.to.GroupMessageEventPack;
import coloryr.colormirai.demo.sdk.pack.to.NewFriendRequestEventPack;
import coloryr.colormirai.demo.sdk.pipe.ColorMiraiNetty;
import coloryr.colormirai.demo.sdk.pipe.ColorMiraiSocket;
import coloryr.colormirai.demo.sdk.pipe.ColorMiraiWebSocket;

import java.util.ArrayList;
import java.util.Scanner;

public class ColoryrTest {
    private static RobotTop robot;

    private static void messgae(byte type, PackBase data) {
        switch (type) {
            case 46: {
                NewFriendRequestEventPack pack = (NewFriendRequestEventPack) data;
                robot.newFriendRequestCall(pack.qq, pack.eventid, FriendCallType.ACCEPT);
                break;
            }
            case 49:
                GroupMessageEventPack pack = (GroupMessageEventPack) data;
                System.out.println("id = " + pack.id);
                System.out.println("fid = " + pack.fid);
                System.out.println("message = ");
                for (String item : pack.message) {
                    System.out.println(item);
                }
                System.out.println();
//                robot.sendGroupMessage(pack.qq, pack.id, new ArrayList<String>() {{
//                    this.add(pack.fid + " 你发送了消息 " + pack.message.get(pack.message.size() - 1));
//                }});
                break;
        }
    }

    private static void log(LogType type, String data) {
        System.out.println("机器人日志:" + type.toString() + ":" + data);
    }

    private static void state(StateType type) {
        System.out.println("机器人状态:" + type.toString());
    }

    public static void main(String[] arg) {
        robot = new RobotTop();
        RobotConfig config = new RobotConfig() {{
            name = "Demo";
            ip = "127.0.0.1";
            port = 23333;
            pack = new ArrayList<Integer>() {{
                this.add(46);
                this.add(49);
                this.add(50);
                this.add(51);
            }};
            groups = null;
            qqs = null;
            runQQ = 0;
            time = 10000;
            check = true;
            callAction = ColoryrTest::messgae;
            logAction = ColoryrTest::log;
            stateAction = ColoryrTest::state;
        }};

        //WebSocket
        //config.ip = "ws://127.0.0.1:23334";
        //robot.setPipe(new ColorMiraiWebSocket(robot));

        //Netty
        config.port = 23335;
        robot.setPipe(new ColorMiraiNetty(robot));

        robot.set(config);
        robot.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String data = scanner.nextLine();
            String[] args = data.split(" ");
            if (args[0].equals("stop")) {
                robot.stop();
                return;
            } else if (args[0].equals("friends")) {
                if (arg.length != 2) {
                    System.out.println("错误的参数");
                    continue;
                }
                try {
                    long qq = Long.parseLong(args[1]);
                    robot.getFriends(qq, (res) -> {
                        System.out.println(res.qq + "的好友：");
                        for (ReFriendInfoPack item : res.friends) {
                            System.out.println(item.id + " " + item.remark);
                        }
                    });
                } catch (NumberFormatException e) {
                    System.out.println("错误的参数");
                }
            }
        }
    }
}