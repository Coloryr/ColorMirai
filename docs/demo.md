# ColorMirai

[返回](../README.md)

## C#

1. 准备工作  
   下载[Visual Studio](https://visualstudio.microsoft.com/zh-hans/vs/)  
   下载[net6](https://dotnet.microsoft.com/download/dotnet/6.0) 安装SDK  
   打开[demo代码](../demo/C%23)

2. 开始编写代码  
   
需要先创建一个机器人配置

定义一个全局变量

```C#
static Robot Robot = new Robot();
```

设置机器人回调，回调函数需要有byte, object两个参数  
一个示例回调方法

```C#
void Message(byte type, object data)
{
    switch (type)
    {
        case 46:
            {
                var pack = data as NewFriendRequestEventPack;
                robot.NewFriendRequestCall(pack.qq, pack.eventid, Robot.FriendCallType.accept);
                break;
            }
        case 49:
            {
                var pack = data as GroupMessageEventPack;
                Console.WriteLine($"id = {pack.id}");
                Console.WriteLine($"fid = {pack.fid}");
                Console.WriteLine($"message = ");
                foreach (var item in pack.message)
                {
                    Console.WriteLine(item);
                }
                Console.WriteLine();
                robot.SendGroupMessage(pack.qq, pack.id, new() { $"{pack.fid} 你发送了消息 {pack.message[^1]}" });
                break;
            }
        case 50:

            break;
        case 51:

            break;
    }
}

void Log(LogType type, string data)
{

    Console.WriteLine($"日志:{type} {data}");
}

void State(StateType type)
{
    Console.WriteLine($"日志:{type}");
}
```

实例化`RobotConfig`

```C#
var Config = new RobotConfig
{
    IP = "127.0.0.1",
    Port = 23333,
    Name = "Demo",
    Pack = new() { 49, 50, 51 },
    Time = 10000,
    CallAction = Message,
    LogAction = Log,
    StateAction = State
};
```

根据需求填好参数后，实例化一个`Robot`类，并给机器人设置配置

```C#
Robot.Set(Config);
```

启动机器人

```C#
Robot.Start();
```

完整启动代码：

```C#
using ColoryrSDK;
using System;

Robot robot = new();

void Message(byte type, object data)
{
    switch (type)
    {
        case 46:
            {
                var pack = data as NewFriendRequestEventPack;
                robot.NewFriendRequestCall(pack.qq, pack.eventid, Robot.FriendCallType.accept);
                break;
            }
        case 49:
            {
                var pack = data as GroupMessageEventPack;
                Console.WriteLine($"id = {pack.id}");
                Console.WriteLine($"fid = {pack.fid}");
                Console.WriteLine($"message = ");
                foreach (var item in pack.message)
                {
                    Console.WriteLine(item);
                }
                Console.WriteLine();
                robot.SendGroupMessage(pack.qq, pack.id, new() { $"{pack.fid} 你发送了消息 {pack.message[^1]}" });
                break;
            }
        case 50:

            break;
        case 51:

            break;
    }
}

void Log(LogType type, string data)
{
    Console.WriteLine($"日志:{type} {data}");
}

void State(StateType type)
{
    Console.WriteLine($"日志:{type}");
}

RobotConfig config = new()
{
    IP = "127.0.0.1",
    Port = 23333,
    Name = "test",
    Pack = new() { 46, 49, 50, 51 },
    RunQQ = 0,
    Time = 10000,
    CallAction = Message,
    LogAction = Log,
    StateAction = State
};

robot.Set(config);
robot.Start();

while (!robot.IsConnect) ;

while (true)
{
    string temp = Console.ReadLine();
    string[] arg = temp.Split(' ');
    switch (arg[0])
    {
        case "friends":
            if (arg.Length != 2)
            {
                Console.WriteLine("错误的参数");
                break;
            }
            if (!long.TryParse(arg[1], out long qq))
            {
                Console.WriteLine("错误的参数");
                break;
            }
            robot.GetFriends(qq, (res) =>
            {
                Console.WriteLine($"{res.qq}的好友：");
                foreach (var item in res.friends)
                {
                    Console.WriteLine($"{item.id} {item.remark}");
                }
            });
            break;
        case "groups":
            break;
        case "members":
            break;
        case "stop":
            robot.Stop();
            return;
    }
}
```

## JAVA

1. 准备工作  
   首先打开[demo代码](../demo/JAVA/)使用IDEA打开文件夹

2. 开始编写

创建机器人全局变量
```JAVA
TopRobot robot = new TopRobot();
```

实例化`RobotConfig`类

```JAVA
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
```

根据需求填好参数后，实例化一个`Robot`类，并给机器人设置配置

```JAVA
robot.Set(Config);
```

启动机器人

```JAVA
robot.Start();
```

完整启动代码：

```JAVA
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
```

## Python  
   Python可以直接调用C#的DLL反射执行  
   [Ironpython](https://ironpython.net/)  
   [pythonnet](https://pypi.org/project/pythonnet/)

Dll自行构建

## 其他语言  
   待补充