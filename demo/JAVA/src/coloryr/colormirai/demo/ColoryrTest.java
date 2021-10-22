package coloryr.colormirai.demo;

import coloryr.colormirai.demo.RobotSDK.BuildPack;
import coloryr.colormirai.demo.RobotSDK.BaseRobot;
import coloryr.colormirai.demo.RobotSDK.RobotConfig;
import coloryr.colormirai.demo.RobotSDK.TopRobot;
import coloryr.colormirai.demo.RobotSDK.enums.FriendCallType;
import coloryr.colormirai.demo.RobotSDK.enums.LogType;
import coloryr.colormirai.demo.RobotSDK.enums.StateType;
import coloryr.colormirai.demo.RobotSDK.pack.PackBase;
import coloryr.colormirai.demo.RobotSDK.pack.from.SendGroupMessagePack;
import coloryr.colormirai.demo.RobotSDK.pack.re.FriendInfoPack;
import coloryr.colormirai.demo.RobotSDK.pack.to.GroupMessageEventPack;
import coloryr.colormirai.demo.RobotSDK.pack.to.NewFriendRequestEventPack;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Scanner;

public class ColoryrTest {
    private static TopRobot robot;

    private static void messgae(byte type, PackBase data) {
        switch (type) {
            case 46:
            {
                NewFriendRequestEventPack pack  = (NewFriendRequestEventPack) data;
                robot.NewFriendRequestCall(pack.qq, pack.eventid, FriendCallType.accept);
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
                robot.SendGroupMessage(pack.qq, pack.id, new ArrayList<String>(){{
                    this.add(pack.fid + " 你发送了消息 " + pack.message.get(pack.message.size() - 1));
                }});
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
        robot = new TopRobot();
        RobotConfig Config = new RobotConfig() {{
            Name = "Demo";
            IP = "127.0.0.1";
            Port = 23333;
            Pack = new ArrayList<Integer>() {{
                this.add(46);
                this.add(49);
                this.add(50);
                this.add(51);
            }};
            Groups = null;
            QQs = null;
            RunQQ = 0;
            Time = 10000;
            Check = true;
            CallAction = ColoryrTest::messgae;
            LogAction = ColoryrTest::log;
            StateAction = ColoryrTest::state;
        }};

        robot.Set(Config);
        robot.Start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String data = scanner.nextLine();
            String[] args = data.split(" ");
            if (args[0].equals("stop")) {
                robot.Stop();
                return;
            } else if (args[0].equals("friends")) {
                if (arg.length != 2) {
                    System.out.println("错误的参数");
                    continue;
                }
                try {
                    long qq = Long.parseLong(args[1]);
                    robot.GetFriends(qq, (res) -> {
                        System.out.println(res.qq + "的好友：");
                        for (FriendInfoPack item : res.friends) {
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