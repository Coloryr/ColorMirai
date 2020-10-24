# ColorMirai
一个基于[Mirai](https://github.com/mamoe/mirai) 的机器人框架


**官方QQ群**：[571239090](https://qm.qq.com/cgi-bin/qm/qr?k=85m_MZMJ7BbyZ2vZW4wHVZGGvGnIL2As&jump_from=webapi)

**索引列表**


- [构建说明](#构建说明)
- [**启动**](#启动)
    - [无法登录](#无法登录临时解决)
- [**插件教程**](#插件教程)
    - [链接机器人](#链接机器人)
    - [Socket数据包](#数据包)
- [示例](#目前的示例代码)
- [使用ColorMirai的插件](#使用ColorMirai的插件)


## 构建说明
![Gradle构建](https://github.com/Coloryr/ColorMirai/workflows/Gradle%E6%9E%84%E5%BB%BA/badge.svg)

*需要JAVA11环境*

*运行环境看你的构建环境*

*构建不了可以去群里找人要*

请根据自己的系统先安装JDK11并且设置好环境变量  
- 安装git  
- 右键打开git bash  
输入下面的指令（如果慢的话, 可能需要梯子等工具辅助下载）
```bash
git clone https://github.com/Coloryr/ColorMirai.git
cd ColorMirai
./gradlew shadowJar
```
在`BUILD SUCCESSFUL`之后, 你会在以下路径找到一个jar文件

`build/libs/ColorMirai-2.0-SNAPSHOT-all.jar`

## 启动
> 1. 启动ColorMirai，必须使用JAVA11及以上
> ```
> java -jar ColorMirai-2.X-SNAPSHOT-all.jar
> ```
> 首次启动后, 会生成`info.json`和`MainConfig.json`文件  
> `info.json`是设备名称，不需要特别设置  
> `MainConfig.json`是配置文件

> 2. 默认配置

> `MainConfig.json`
> ```Json
> {
>     "MaxList": 100000,
>     "Pack": true,
>     "Port": 23333,
>     "QQs": [
>         {
>             "QQ": 1234567890,
>             "Password": "qwe123456789"
>         }
>     ],
>     "Type": 0,
>     "escapeSelf": true
> }
> ```
> - `MaxList`：最大消息列表
> - `Pack`：是否发送心跳包
> - `Port`：启动的端口
> - `QQs` : QQ账号列表 (可以添加多个)
>     - `QQ`：登录的QQ号
>     - `Password`：QQ号密码
> - `Type`：登录的方式
> - 目前支持两种协议: 
>     - 安卓 [0]
>     - 手表 [1]
> - `escapeSelf`：是否跳过自己机器人的信息

设置完成后再次启动ColorMirai，出现`[INFO] Socket已启动: 23333` 说明机器人已成功启动

### 无法登录临时解决
1. 将协议切换成`手表 [1]`
2. 登录成功后, 下次登录可以选择`安卓 [0]`


## 插件教程

### 链接机器人
ColorMirai使用socket方式让机器人和插件互相链接  
Socket数据包的接受和封装在Demo已经写完了，只需要引用一下就好了  
下面的教程使用C#作为示例

首先下载Demo的代码并且导入到你的IDE  
[C#](/demo/C%23demo.cs) [JAVA](/demo/JAVAdemo.java)

然后加入Main函数
```C#
namespace netcore
{
    class HelloWorld
    {
        static void Main(string[] args)
        {
            RobotSocket.Start();
        }
    }
}
```
即可开始链接机器人

Demo说明
1. 插件第一次连接需要发送一个数据包来注册所监听事件的包
```Json
{
  "Name": "test",
  "Reg": [49,50,51]
}
```
- `Name`：插件名字
- `Reg`：监听的包

注意：所有包必须带ID，否则无法识别，只有标注`（事件）`的包才会被监听，数据包ID的代表含义请看[数据包](#数据包)

2. 监听消息
首先查看数据包49
```
49 [机器人]收到群消息（事件）
```
它监听的是群消息的数据

当机器人收到群消息时候会通过Socket发送数据包给插件  
插件的处理逻辑位于`DoThread`
```C#
DoThread = new Thread(() =>
{
    RobotTask task;
    while (IsRun)
    {
        try
        {
            if (QueueRead.TryTake(out task))
            {
                switch (task.index)
                {
                    case 28:
                        var pack5 = JsonConvert.DeserializeObject<GroupMessagePostSendEventPack>(task.data);
                        if (pack5.res && pack5.message[pack5.message.Count - 1] == "3秒后撤回")
                        {
                            Task.Run(() =>
                            {
                                Thread.Sleep(2900);
                                string id = Utils.GetString(pack5.message[0], "source:", ",");
                                var data = BuildPack.Build(new ReCallMessage { id = long.Parse(id) }, 71);
                                QueueSend.Add(data);
                            });
                        }
                        break;
                    case 46:
                        var pack3 = JsonConvert.DeserializeObject<NewFriendRequestEventPack>(task.data);
                        Console.WriteLine("qq = " + pack3.qq);
                        Console.WriteLine("id = " + pack3.id);
                        Console.WriteLine("fid = " + pack3.fid);
                        Console.WriteLine("name = " + pack3.name);
                        Console.WriteLine("message = " + pack3.message);
                        Console.WriteLine("eventid = " + pack3.eventid);
                        Console.WriteLine();
                        CallEvent(pack3.eventid, 0, null);
                        break;
                    case 49:
                        var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(task.data);
                        Console.WriteLine("qq = " + pack.qq);
                        Console.WriteLine("id = " + pack.id);
                        Console.WriteLine("fid = " + pack.fid);
                        Console.WriteLine("name = " + pack.name);
                        Console.WriteLine("message = ");
                        foreach (var item in pack.message)
                        {
                            Console.WriteLine(item);
                        }
                        Console.WriteLine();
                        if (pack.message[pack.message.Count - 1] == "撤回")
                        {
                            string id = Utils.GetString(pack.message[0], "source:", ",");
                            var data = BuildPack.Build(new ReCallMessage { qq = pack.qq, id = long.Parse(id) }, 71);
                            QueueSend.Add(data);
                        }
                        else if (pack.message[pack.message.Count - 1] == "回复")
                        {
                            string id = Utils.GetString(pack.message[0], "source:", ",");
                            var list2 = new List<string>() { "quote:" + id };
                            list2.Add("回复消息");
                            SendGroupMessage(pack.qq, pack.id, list2);
                        }
                        else if (pack.message[pack.message.Count - 1] == "撤回自己")
                        {
                            var list2 = new List<string>() { "3秒后撤回" };
                            SendGroupMessage(pack.qq, pack.id, list2);
                        }
                        break;
                    case 50:
                        var pack1 = JsonConvert.DeserializeObject<TempMessageEventPack>(task.data);
                        Console.WriteLine("qq = " + pack1.qq);
                        Console.WriteLine("id = " + pack1.id);
                        Console.WriteLine("fid = " + pack1.fid);
                        Console.WriteLine("name = " + pack1.name);
                        Console.WriteLine("message = ");
                        foreach (var item in pack1.message)
                        {
                            Console.WriteLine(item);
                        }
                        Console.WriteLine();
                        var list = new List<string>() { pack1.name };
                        list.AddRange(pack1.message);
                        SendGroupPrivateMessage(pack1.qq, pack1.id, pack1.fid, list);
                        break;
                    case 51:
                        var pack2 = JsonConvert.DeserializeObject<FriendMessageEventPack>(task.data);
                        Console.WriteLine("id = " + pack2.id);
                        Console.WriteLine("time = " + pack2.time);
                        Console.WriteLine("name = " + pack2.name);
                        Console.WriteLine("message = ");
                        foreach (var item in pack2.message)
                        {
                            Console.WriteLine(item);
                        }
                        Console.WriteLine();
                        var list1 = new List<string>() { pack2.name };
                        list1.AddRange(pack2.message);
                        SendFriendMessage(pack2.qq, pack2.id, list1);
                        break;
                }
            }
            Thread.Sleep(10);
        }
        catch (Exception e)
        {
            ServerMain.LogError(e);
        }
    }
});
```
`case`后面的为数据包的ID，只有正确的数据包ID才会被处理  
紧接着是一个JSON反序列化，得到数据对象`GroupMessageEventPack`  
在这个对象里面有
```C#
abstract class PackBase
{
    public long qq { get; set; }
}
class GroupMessageEventPack : PackBase
{
    public long id { get; set; }
    public long fid { get; set; }
    public string name { get; set; }
    public List<string> message { get; set; }
}
```
`qq`为QQ人号码  
`id`为群号  
`fid`为群成员QQ号  
`name`为群成员群名片  
`message`为消息内容  

在`message`中有消息ID和符号，最后一个为处理后的消息内容

3. 发送群消息  
发送群消息同样也是Socket发送给机器人，数据包内容提供了一个构建方法
```C#
class BuildPack
{
    public static byte[] Build(object obj, byte index)
    {
        byte[] data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(obj) + " ");
        data[data.Length - 1] = index;
        return data;
    }

    public static byte[] BuildImage(long qq, long id, long fid, string img, byte index)
    {
        string temp = "";
        if (id != 0)
        {
            temp += "id=" + id + "&";
        }
        if (fid != 0)
        {
            temp += "fid=" + fid + "&";
        }
        temp += "qq=" + qq + "&";
        temp += "img=" + img;
        byte[] data = Encoding.UTF8.GetBytes(temp + " ");
        data[data.Length - 1] = index;
        return data;
    }

    public static byte[] BuildSound(long qq, long id, string sound, byte index)
    {
        string temp = "id=" + id + "&qq=" + qq + "&sound=" + sound;
        byte[] data = Encoding.UTF8.GetBytes(temp + " ");
        data[data.Length - 1] = index;
        return data;
    }
}
```
然后对BuildPack进行简单的封装后，可以使用这个来实现
```C#
public static void CallEvent(long eventid, int dofun, List<object> arg)
{
    var data = BuildPack.Build(new EventCallPack { eventid = eventid, dofun = dofun, arg = arg }, 59);
    QueueSend.Add(data);
}
public static void SendGroupMessage(long qq, long id, List<string> message)
{
    var data = BuildPack.Build(new SendGroupMessagePack {qq=qq, id = id, message = message }, 52);
    QueueSend.Add(data);
}
public static void SendGroupPrivateMessage(long qq, long id, long fid, List<string> message)
{
    var data = BuildPack.Build(new SendGroupPrivateMessagePack { qq=qq,id = id, fid = fid, message = message }, 53);
    QueueSend.Add(data);
}
public static void SendFriendMessage(long qq, long id, List<string> message)
{
    var data = BuildPack.Build(new SendFriendMessagePack { qq=qq,id = id, message = message }, 54);
    QueueSend.Add(data);
}
public static void SendGroupImage(long qq, long id, string img)
{
    var data = BuildPack.BuildImage(qq, id, 0, img, 61);
    QueueSend.Add(data);
}
public static void SendGroupPrivateImage(long qq, long id, long fid, string img)
{
    var data = BuildPack.BuildImage(qq, id, fid, img, 62);
    QueueSend.Add(data);
}
public static void SendFriendImage(long qq, long id, string img)
{
    var data = BuildPack.BuildImage(qq, id, 0, img, 63);
    QueueSend.Add(data);
}

public static void SendGroupSound(long qq, long id, string sound)
{
    var data = BuildPack.BuildSound(qq, id, sound, 74);
    QueueSend.Add(data);
}
public static void SendGroupImageFile(long qq, long id, string file)
{
    var data = BuildPack.Build(new LoadFileSendToGroupImagePack { qq = qq, id = id, file = file, }, 75);
    QueueSend.Add(data);
}
```
这个是封装后的方法，直接调用即可
```C#
SendGroupMessage(1092415357, 571239090, list);
```

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
```
只有正确的发包，才能处理  
更多包请看[PackDo.java](src/main/java/Color_yr/ColorMirai/Pack/PackDo.java)  
每个包需要发送的数据都在`/Pack`路径下的java  
标注为`[机器人]`是框架发给插件的  
标注为`[插件]`是插件发送给框架的

## 目前的示例代码
[C#](demo/C%23demo.cs)  
[JAVA](demo/JAVAdemo.java)  
示例代码不包含Main函数，可以作为库用

## 使用ColorMirai的插件
[Minecraft_QQ_Gui/Cmd](https://github.com/HeartAge/Minecraft_QQ-C-Server-)  
[GitHubPush](https://github.com/Coloryr/GitHubPush)

<!--有人帮你写2333-->
懒得写了（jiushi）
