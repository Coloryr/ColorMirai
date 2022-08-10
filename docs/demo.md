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

设置通信管道，下列中的任意一个
```C#
robot.SetPipe(new ColorMiraiWebSocket(robot));
robot.SetPipe(new ColorMiraiNetty(robot));
```
注意：如果使用WebSocket需要将IP设置为`ws://xxxxx`

如果使用`ColorMiraiWebSocket`需要安装`nuget`包`websocketsharp.core`  
如果使用`ColorMiraiNetty`需要安装`nuget`包
```
DotNetty.Buffers
DotNetty.Codecs
DotNetty.Common
DotNetty.Transport
```

启动机器人

```C#
Robot.Start();
```

完整启动代码：

```C#
using ColoryrSDK;
using System;

RobotSDK robot = new();

void Message(byte type, object data)
{
    switch (type)
    {
        case 46:
            {
                var pack = data as NewFriendRequestEventPack;
                robot.NewFriendRequestCall(pack.qq, pack.eventid, RobotSDK.FriendCallType.accept);
                break;
            }
        case 49:
            {
                var pack = data as GroupMessageEventPack;
                if (pack.id != 571239090)
                    break;
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
        case 116:

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
    Port = 23335,
    Name = "test",
    Pack = new() { 46, 49, 50, 51, 116 },
    RunQQ = 0,
    Time = 10000,
    CallAction = Message,
    LogAction = Log,
    StateAction = State
};

robot.Set(config);
robot.SetPipe(new ColorMiraiNetty(robot));
robot.Start();

while (!robot.IsConnect) ;

while (true)
{
    string temp = Console.ReadLine();
    string[] arg = temp.Split(' ');
    switch (arg[0])
    {
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
```

根据需求填好参数后，实例化一个`Robot`类，并给机器人设置配置和链接方式

```JAVA
robot.setPipe(new ColorMiraiNetty(robot));
robot.set(config);
```

启动机器人

```JAVA
robot.start();
```

完整启动代码：

```JAVA
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
```

## Python  
   Python可以直接调用C#的DLL反射执行  
   [Ironpython](https://ironpython.net/)  
   [pythonnet](https://pypi.org/project/pythonnet/)

Dll自行构建

## 其他语言  
   待补充