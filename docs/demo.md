# ColorMirai

[返回](../README.md)

## C#

1. 准备工作  
   下载[Visual Studio](https://visualstudio.microsoft.com/zh-hans/vs/)
   下载[net5](https://dotnet.microsoft.com/download/dotnet/5.0) 安装SDK  
   打开[demo代码](../demo/C%23)

2. 开始编写代码  
   
需要先创建一个机器人配置

定义一个全局变量

```C#
static Robot Robot = new Robot();
```

设置机器人回调，回调函数需要有byte, string两个参数  
一个示例回调方法

```C#
void Message(byte type, string data)
{
    switch (type)
    {
        case 49:
            var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(data);
            var temp = BuildPack.Build(new SendGroupMessagePack
            {
                qq = robot.QQs[0],
                id = pack.id,
                message = new()
                {
                    $"{pack.fid} 你发送了消息 {pack.message[^1]}"
                }
            }, 52);
            robot.AddTask(temp);
            break;
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
using System;
using ColoryrSDK;
using Newtonsoft.Json;

Robot robot = new();

void Message(byte type, string data)
{
    switch (type)
    {
        case 49:
            var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(data);
            var temp = BuildPack.Build(new SendGroupMessagePack
            {
                qq = robot.QQs[0],
                id = pack.id,
                message = new()
                {
                    $"{pack.fid} 你发送了消息 {pack.message[^1]}"
                }
            }, 52);
            robot.AddTask(temp);
            break;
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
    Name = "Demo",
    Pack = new() { 49, 50, 51 },
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
}
```

## JAVA

1. 准备工作  
   首先打开[demo代码](../demo/JAVA/)使用IDEA打开文件夹

2. 开始编写

创建机器人全局变量
```JAVA
Robot Robot = new Robot();
```

实例化`RobotConfig`类

```JAVA
RobotConfig Config = new RobotConfig() {{
     Name = "Demo";
     IP = "127.0.0.1";
     Port = 23333;
     Pack = new ArrayList<Byte>() {{
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
```

根据需求填好参数后，实例化一个`Robot`类，并给机器人设置配置

```JAVA
Robot.Set(Config);
```

启动机器人

```JAVA
Robot.Start();
```

完整启动代码：

```JAVA
import Robot.Pack.FromPlugin.SendGroupMessagePack;
import Robot.Pack.ToPlugin.GroupMessageEventPack;
import Robot.*;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Scanner;

public class ColoryrSDK {
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
```

## Python  
   Python可以直接调用C#的DLL反射执行  
   [Ironpython](https://ironpython.net/)  
   [pythonnet](https://pypi.org/project/pythonnet/)

Dll自行构建

## 其他语言  
   待补充