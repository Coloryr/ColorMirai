import Robot.Pack.FromPlugin.SendGroupMessagePack;
import Robot.Pack.ToPlugin.GroupMessageEventPack;
import Robot.*;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Scanner;

public class ColoryrTest {
    public static void main(String[] arg) {
        Robot robot = new Robot();
        RobotConfig Config = new RobotConfig() {{
            Name = "Demo";
            IP = "127.0.0.1";
            Port = 23333;
            Pack = new ArrayList<Integer>() {{
                add(49);
                add(50);
                add(51);
            }};
            Groups = null;
            QQs = null;
            RunQQ = 0;
            Time = 10000;
            Check = true;
            CallAction = (type, data) -> {
                switch (type) {
                    case 49:
                        GroupMessageEventPack pack = JSON.parseObject(data, GroupMessageEventPack.class);
                        System.out.println("id = " + pack.id);
                        System.out.println("fid = " + pack.fid);
                        System.out.println("message = ");
                        for (String item : pack.message) {
                            System.out.println(item);
                        }
                        System.out.println();
                        SendGroupMessagePack pack1 = new SendGroupMessagePack();
                        pack1.id = pack.id;
                        pack1.qq = robot.QQs.get(0);
                        pack1.message = new ArrayList<String>() {{
                            add(pack.fid + " 你发送了消息 " + pack.message.get(pack.message.size() - 1));
                        }};
                        robot.addTask(BuildPack.Build(pack1, 52));
                        break;
                }
            };
            LogAction = (type, data) -> {
                System.out.println("机器人日志:" + type.toString() + ":" + data);
            };
            StateAction = type -> {
                System.out.println("机器人状态:" + type.toString());
            };
        }};

        robot.Set(Config);
        robot.IsFirst = false;
        robot.Start();
        Scanner scanner = new Scanner(System.in);
        for (; ; ) {
            String data = scanner.nextLine();
            if (data.equals("stop")) {
                robot.Stop();
                return;
            }
        }
    }
}