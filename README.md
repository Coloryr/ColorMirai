# ColorMirai
一个基于[Mirai](https://github.com/mamoe/mirai) 的机器人框架


**官方QQ群**：[571239090](https://qm.qq.com/cgi-bin/qm/qr?k=85m_MZMJ7BbyZ2vZW4wHVZGGvGnIL2As&jump_from=webapi)

**索引列表**

- [构建说明](#构建说明)
- [**启动**](#启动)
    - [无法登录](#无法登录临时解决)
- [**插件教程**](#插件教程)
    - [使用说明](#使用说明)
    - [C#](#C#)
    - [JAVA](#JAVA)
- [依赖ColorMirai的插件](#依赖ColorMirai的插件)

## 构建说明
![Gradle构建](https://github.com/Coloryr/ColorMirai/workflows/Gradle%E6%9E%84%E5%BB%BA/badge.svg)

*需要openjdk8环境*

*运行环境看你的构建环境*

*构建不了可以去群里找人要*

请根据自己的系统先安装openjdk8并且设置好环境变量  
- 安装git  
- 右键打开git bash  
输入下面的指令（如果慢的话, 可能需要梯子等工具辅助下载）
```bash
git clone https://github.com/Coloryr/ColorMirai.git
cd ColorMirai
./gradlew shadowJar
```
在`BUILD SUCCESSFUL`之后, 你会在以下路径找到一个jar文件

`build/libs/ColorMirai-3.1.1-SNAPSHOT-all.jar`

## 启动
1. 启动ColorMirai，必须使用JAVA8及以上，推荐使用JAVA14启动
```
java -jar ColorMirai-3.X-SNAPSHOT-all.jar
```
首次启动后, 会生成`info.json`和`MainConfig.json`文件  
- `info.json`是设备名称，不需要特别设置  
- `MainConfig.json`是配置文件

2. 默认配置

`MainConfig.json`
```Json
{
    "MaxList": 100000,
    "Pack": true,
    "Port": 23333,
    "QQs": [
        {
            "Password": "请填写你的密码",
            "QQ": 0
        }
    ],
    "ReadEncoding": "UTF-8",
    "SendEncoding": "UTF-8",
    "LoginType": 0,
    "escapeSelf": true,
    "SocketType": 0
}
```
- `MaxList`：最大消息列表
- `Pack`：是否发送心跳包
- `Port`：启动的端口
- `QQs` : QQ账号列表 (可以添加多个)
     - `QQ`：登录的QQ号
     - `Password`：QQ号密码
- `Type`：登录的方式
- 目前支持两种协议: 
     - 安卓 [0]
     - 手表 [1]
     - 平板 [2]
- `escapeSelf`：是否跳过自己机器人的信息
- `ReadEncoding`：读数据包编码
- `SendEncoding`：发送数据包编码
- `SocketType`：插件连接方式
     - 普通的Socket [0]
     - WebSocket [1]

设置完成后再次启动ColorMirai，出现
```
[INFO] Socket已启动: 23333
``` 
说明机器人已成功启动

### 无法登录临时解决
1. 将协议切换成`手表 [1]`
2. 登录成功后, 下次登录可以选择`安卓 [0]`

## 目前的SDK代码
[C#](demo/C%23demo.cs)  
[JAVA](demo/JAVAdemo.java)  
示例代码不包含Main函数，可以作为库用  
使用方法看下面的[插件教程](#插件教程)所示

## 插件教程
ColorMirai使用socket/WebSocket方式让机器人和插件互相链接  
Socket数据包的接受和封装在Demo已经写完了，只需要引用一下就好了  

### 使用说明
插件第一次连接需要发送一个数据包来注册所监听事件的包
```Json
{
  "Name": "test",
  "Reg": [49,50,51],
  "Groups" : [],
  "QQs" : [],
  "RunQQ": 0
}
```
- `Name`：插件名字
- `Reg`：监听的包
- `Groups`：只监听的群号，可以为null
- `QQs`：只监听的QQ号，可以为null
- `RunQQ`：插件运行的机器人QQ号，可以为0

每当机器人事件触发后，会通过Socket/WebSocket将数据发送给所有插件，每个数据包的最后一个byte为数据包ID。  
所有数据包必须带ID，否则无法识别，只有标注`（事件）`的包才会被监听，数据包ID的代表含义请看[数据包](#数据包)

### C#  
1. 准备工作  
首先下载[Demo代码](/demo/C%23demo.cs)  并且导入到你的IDE  
下载[net5](https://dotnet.microsoft.com/download/dotnet/5.0) 安装SDK  
下载[Visual Studio](https://visualstudio.microsoft.com/zh-hans/vs/)  
新建`.net core`项目，并选择`net5`  
安装[nuget](https://www.nuget.org/packages/Newtonsoft.Json/) 包  

2. 开始编写代码  
需要先创建一个机器人配置
```C#
public class RobotConfig
{
    /// <summary>
    /// 机器人IP
    /// </summary>
    public string IP { get; init; }
    /// <summary>
    /// 机器人端口
    /// </summary>
    public int Port { get; init; }
    /// <summary>
    /// 监听的包
    /// </summary>
    public List<byte> Pack { get; init; }
    /// <summary>
    /// 插件名字
    /// </summary>
    public string Name { get; init; }
    /// <summary>
    /// 监听的群，可以为null
    /// </summary>
    public List<long> Groups { get; init; }
    /// <summary>
    /// 监听的qq号，可以为null
    /// </summary>
    public List<long> QQs { get; init; }
    /// <summary>
    /// 运行的qq，可以不设置
    /// </summary>
    public long RunQQ { get; init; }
    /// <summary>
    /// 重连时间
    /// </summary>
    public int Time { get; init; }
    /// <summary>
    /// 检测是否断开
    /// </summary>
    public bool Check { get; init; }
    /// <summary>
    /// 机器人事件回调函数
    /// </summary>
    public Action<byte, string> CallAction { get; init; }
    /// <summary>
    /// 机器人日志回调函数
    /// </summary>
    public Action<LogType, string> LogAction { get; init; }
    /// <summary>
    /// 机器人状态回调函数
    /// </summary>
    public Action<StateType> StateAction { get; init; }
}
```
定义一个全局变量
```C#
static Robot Robot = new Robot();
```
设置机器人回调，回调函数需要有byte, string两个参数  
一个示例回调方法
```C#
private static void Call(byte packid, string data)
{
    Console.WriteLine($"收到消息{data}");
    switch (packid)
    {
        case 49:
            var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(data);
            Robot.SendGroupMessage(Robot.QQs[0], pack.id, new()
            { $"{pack.fid} {pack.name} 你发送了消息 {pack.message[^1]}" });
            string temp = GetString(pack.message[0], "[mirai:source:[", "]");
            long.TryParse(temp, out long test);
            Robot.ReCall(Robot.QQs[0], test);
            break;
        case 50:
            break;
        case 51:
            break;
    }
}
private static void LogCall(LogType type , string data)
{
    Console.WriteLine($"机器人日志:{type} {data}");
}
private static void StateCall(StateType type)
{
    Console.WriteLine($"机器人状态:{type}");
}
```
实例化`RobotConfig`类
```C#
var Config = new RobotConfig
{
    Name = "Demo",
    Groups = null,
    QQs = null,
    RunQQ = 0,
    Pack = new() { 49, 50, 51 },
    IP = "127.0.0.1",
    Port = 23333,
    Time = 10000,
    Check = true,
    CallAction = Call,
    LogAction = LogCall,
    StateAction = StateCall
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
using Newtonsoft.Json;

namespace ColoryrSDK
{
    class Name
    {
        static readonly Robot Robot = new();
        private static void Call(byte packid, string data)
        {
            Console.WriteLine($"收到消息{data}");
            switch (packid)
            {
                case 49:
                    var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(data);
                    Robot.SendGroupMessage(Robot.QQs[0], pack.id, new()
                    { $"{pack.fid} {pack.name} 你发送了消息 {pack.message[^1]}" });
                    string temp = GetString(pack.message[0], "[mirai:source:[", "]");
                    long.TryParse(temp, out long test);
                    Robot.ReCall(Robot.QQs[0], test);
                    break;
                case 50:
                    break;
                case 51:
                    break;
            }
        }
        private static string GetString(string a, string b, string c = null)
        {
            int x = a.IndexOf(b) + b.Length;
            int y;
            if (c != null)
            {
                y = a.IndexOf(c, x);
                if (a[y - 1] == '"')
                {
                    y = a.IndexOf(c, y + 1);
                }
                if (y - x <= 0)
                    return a;
                else
                    return a.Substring(x, y - x);
            }
            else
                return a.Substring(x);
        }
        private static void LogCall(LogType type , string data)
        {
            Console.WriteLine($"机器人日志:{type} {data}");
        }
        private static void StateCall(StateType type)
        {
            Console.WriteLine($"机器人状态:{type}");
        }
        public static void Main()
        {
            var Config = new RobotConfig
            {
                Name = "Demo",
                Groups = null,
                QQs = null,
                RunQQ = 0,
                Pack = new() { 49, 50, 51 },
                IP = "127.0.0.1",
                Port = 23333,
                Time = 10000,
                Check = true,
                CallAction = Call,
                LogAction = LogCall,
                StateAction = StateCall
            };
            Robot.Set(Config);
            Robot.IsFirst = false;
            Robot.Start();
            while (true)
            {
                string data = Console.ReadLine();
                if (data == "stop")
                {
                    Robot.Stop();
                    return;
                }
            }
        }
    }
}
```

### JAVA
1. 准备工作  
首先下载[Demo代码](/demo/JAVAdemo.java)并且导入到你的IDE  
导入[fastjson](https://github.com/alibaba/fastjson/) 库  
   
2. 开始编写代码  
需要先创建一个机器人配置
```JAVA
class RobotConfig {
    //注释看C#的
    public String IP;
    public int Port;
    public List<Byte> Pack;
    public String Name;
    public List<Long> Groups;
    public List<Long> QQs;
    public long RunQQ;
    public int Time;
    public boolean Check;
    public ICall CallAction;
    public ILog LogAction;
    public IState StateAction;
}
```
设置机器人回调，回调函数需要有byte, string两个参数  
一个示例回调方法
```JAVA
CallAction = (type, data) -> {
    switch (type) {
        case 49:
            GroupMessageEventPack pack = JSON.parseObject(data, GroupMessageEventPack.class);
            System.out.println("id = " + pack.id);
            System.out.println("fid = " + pack.fid);
            System.out.println("name = " + pack.name);
            System.out.println("message = ");
            for (String item : pack.message) {
                System.out.println(item);
            }
            System.out.println();

            Robot.SendGroupMessage(Robot.QQs.get(0), pack.id, new ArrayList<String>() {{
                add(pack.fid + " " + pack.name + " 你发送了消息 " + pack.message.get(pack.message.size() - 1));
            }});
            break;
        case 50:
            break;
        case 51:
            break;
    }
};
LogAction = (type, data) -> {
    System.out.println("机器人日志:" + type.toString() + ":" + data);
};
StateAction = type -> {
    System.out.println("机器人状态:" + type.toString());
};
```
实例化`RobotConfig`类
```JAVA
RobotConfig Config = new RobotConfig() {{
    Name = "ColoryrSDK";
    IP = "127.0.0.1";
    Port = 23333;
    Pack = new ArrayList<Byte>() {{
        add((byte) 49);
        add((byte) 50);
        add((byte) 51);
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
                System.out.println("name = " + pack.name);
                System.out.println("message = ");
                for (String item : pack.message) {
                    System.out.println(item);
                }
                System.out.println();

                Robot.SendGroupMessage(Robot.QQs.get(0), pack.id, new ArrayList<String>() {{
                    add(pack.fid + " " + pack.name + " 你发送了消息 " + pack.message.get(pack.message.size() - 1));
                }});
                break;
            case 50:
                break;
            case 51:
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
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Scanner;

public class ColoryrSDK {

  public static void main(String[] arg) {
    Robot Robot = new Robot();
    RobotConfig Config = new RobotConfig() {{
      Name = "ColoryrSDK";
      IP = "127.0.0.1";
      Port = 23333;
      Pack = new ArrayList<Byte>() {{
        add((byte) 49);
        add((byte) 50);
        add((byte) 51);
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
            System.out.println("name = " + pack.name);
            System.out.println("message = ");
            for (String item : pack.message) {
              System.out.println(item);
            }
            System.out.println();

            Robot.SendGroupMessage(Robot.QQs.get(0), pack.id, new ArrayList<String>() {{
              add(pack.fid + " " + pack.name + " 你发送了消息 " + pack.message.get(pack.message.size() - 1));
            }});
            break;
          case 50:
            break;
          case 51:
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

    Robot.Set(Config);
    Robot.IsFirst = false;
    Robot.Start();
    Scanner scanner = new Scanner(System.in);
    for (; ; ) {
      String data = scanner.nextLine();
      if (data.equals("stop")) {
        Robot.Stop();
        return;
      }
    }
  }
}
```

4. Python  
Python可以直接调用C#的DLL反射执行  
[Ironpython](https://ironpython.net/)  
[pythonnet](https://pypi.org/project/pythonnet/)
   
Dll自行构建
5. 其他语言  
待补充

### 数据包
大部分数据包是一串JSON字符串+数据包ID构成  
但是发送图片和发送语音用的是FormData格式

**注**：经测试Server 2008存在数据包断包和丢包的现象，请切换到Server 2019使用

```
以字符串的方式看
{...}[ID]
以byte[]的方式看
123,...,125,[ID]
```
`[ID]`对应数据包的ID  

目前重要的ID：
```
0 插件开始连接
49 [机器人]收到群消息（事件）
50 [机器人]收到群临时会话消息（事件）
51 [机器人]收到朋友消息（事件）

52 [插件]发送群消息
53 [插件]发送私聊消息
54 [插件]发送好友消息

60 心跳包

61 [插件]发送图片到群
62 [插件]发送图片到私聊
63 [插件]发送图片到朋友

127 [插件]断开连接
```
只有正确的发包，才能处理  
更多包请看[PackDo.java](src/main/java/Color_yr/ColorMirai/Pack/PackDo.java)  
每个包需要发送的数据都在`/Pack`路径下的java  
标注为`[机器人]`是框架发给插件的  
标注为`[插件]`是插件发送给框架的

## 依赖ColorMirai的插件
[Minecraft_QQ_Gui/Cmd](https://github.com/HeartAge/Minecraft_QQ-C-Server-)  
[GitHubPush](https://github.com/Coloryr/GitHubPush)

## 使用ColorMirai的项目
[CmdControl](https://github.com/Coloryr/CmdControl)

<!--有人帮你写2333-->
懒得写了（jiushi）
