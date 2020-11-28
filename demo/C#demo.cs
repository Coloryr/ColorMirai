using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;
using Newtonsoft.Json;
using System.Threading;
//请用net5运行
//并安装Newtonsoft.Json
namespace netcore
{
    /// <summary>
    /// 基础包
    /// </summary>
    abstract class PackBase
    {
        public long qq { get; set; }
    }
    /// <summary>
    /// 开始包
    /// </summary>
    class PackStart
    {
        /// <summary>
        /// 插件名字
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 监听的包
        /// </summary>
        public List<byte> Reg { get; set; }
        /// <summary>
        /// 监听的群，可以为null
        /// </summary>
        public List<long> Groups { get; set; }
        /// <summary>
        /// 监听的QQ号，可以为null
        /// </summary>
        public List<long> QQs { get; set; }
        /// <summary>
        /// 运行的QQ，0为不指定
        /// </summary>
        public long RunQQ { get; set; }
    }
    /// <summary>
    /// 群消息事件包
    /// </summary>
    class GroupMessageEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 发送群消息包
    /// </summary>
    class SendGroupMessagePack : PackBase
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 发送朋友消息包
    /// </summary>
    class SendFriendMessagePack : PackBase
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 发送群私聊消息包
    /// </summary>
    class SendGroupPrivateMessagePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 临时消息事件包
    /// </summary>
    class TempMessageEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
        public int time { get; set; }
    }
    /// <summary>
    /// 朋友消息事件包
    /// </summary>
    class FriendMessageEventPack : PackBase
    {
        public long id { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
        public int time { get; set; }
    }
    /// <summary>
    /// 发送群图片包
    /// </summary>
    class SendGroupImagePack : PackBase
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    /// <summary>
    /// 发送群私聊图片包
    /// </summary>
    class SendGroupPrivateImagePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string img { get; set; }
    }
    /// <summary>
    /// 发送朋友图片包
    /// </summary>
    class SendFriendImagePack : PackBase
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    /// <summary>
    /// 群全部禁言包
    /// </summary>
    class GroupMuteAll : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 群全部解禁包
    /// </summary>
    class GroupUnmuteAll : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 设置群员名字包
    /// </summary>
    class SetGroupMemberCard : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string card { get; set; }
    }
    /// <summary>
    /// 设置群名包
    /// </summary>
    class SetGroupName : PackBase
    {
        public long id { get; set; }
        public string name { get; set; }
    }
    /// <summary>
    /// 加好友请求事件包
    /// </summary>
    class NewFriendRequestEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public string message { get; set; }
        public long eventid { get; set; }
    }
    /// <summary>
    /// 事件处理包
    /// </summary>
    class EventCallPack : PackBase
    {
        public long eventid { get; set; }
        public int dofun { get; set; }
        public List<object> arg { get; set; }
    }
    /// <summary>
    /// 撤回消息包
    /// </summary>
    class ReCallMessage : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 加载本地图片发送到群包
    /// </summary>
    class LoadFileSendToGroupImagePack : PackBase
    {
        public long id { get; set; }
        public string file { get; set; }
    }
    /// <summary>
    /// 发送群消息之后的事件包
    /// </summary>
    class GroupMessagePostSendEventPack : PackBase
    {
        public long id { get; set; }
        public bool res { get; set; }
        public List<string> message { get; set; }
        public string error { get; set; }
    }
    class BuildPack
    {
        /// <summary>
        /// 构架一个包
        /// </summary>
        /// <param name="obj">对象</param>
        /// <param name="index">包ID</param>
        /// <returns></returns>
        public static byte[] Build(object obj, byte index)
        {
            byte[] data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(obj) + " ");
            data[data.Length - 1] = index;
            return data;
        }
        /// <summary>
        /// 构建一个图片发送包
        /// </summary>
        /// <param name="qq">运行qq</param>
        /// <param name="id">发给群的号码</param>
        /// <param name="fid">发给朋友的qq号</param>
        /// <param name="img">图片BASE64流</param>
        /// <param name="index">包ID</param>
        /// <returns></returns>
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
        /// <summary>
        /// 构建一个发送语音的包
        /// </summary>
        /// <param name="qq">运行的qq号</param>
        /// <param name="id">发送给的id</param>
        /// <param name="sound">音频BASE64</param>
        /// <param name="index">包ID</param>
        /// <returns></returns>
        public static byte[] BuildSound(long qq, long id, string sound, byte index)
        {
            string temp = "id=" + id + "&qq=" + qq + "&sound=" + sound;
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[data.Length - 1] = index;
            return data;
        }
    }
    class RobotTask
    {
        public byte index { get; set; }
        public string data { get; set; }
    }
    class ServerMain
    {
        public static void LogError(Exception e)
        {
            string a = "[错误]" + e.ToString();
            Console.WriteLine(a);
        }
        public static void LogError(string a)
        {
            a = "[错误]" + a;
            Console.WriteLine(a);
        }
        public static void LogOut(string a)
        {
            a = "[信息]" + a;
            Console.WriteLine(a);
        }
    }
    public class RobotConfig
    {
        /// <summary>
        /// 机器人IP
        /// </summary>
        public string ip { get; init; }
        /// <summary>
        /// 机器人端口
        /// </summary>
        public int port { get; init; }
        /// <summary>
        /// 监听的包
        /// </summary>
        public List<byte> pack { get; init; }
        /// <summary>
        /// 插件名字
        /// </summary>
        public string name { get; init; }
        /// <summary>
        /// 监听的群，可以为null
        /// </summary>
        public List<long> groups { get; init; }
        /// <summary>
        /// 监听的qq号，可以为null
        /// </summary>
        public List<long> qqs { get; init; }
        /// <summary>
        /// 运行的qq，可以不设置
        /// </summary>
        public long runqq { get; init; }
        /// <summary>
        /// 重连时间
        /// </summary>
        public int time { get; init; }
        /// <summary>
        /// 检测是否断开
        /// </summary>
        public bool check { get; init; }
        /// <summary>
        /// 机器人事件回调函数
        /// </summary>
        public Action<byte, string> action { get; init; }
    }
    public class Robot
    {
        /// <summary>
        /// 机器人登录的QQ号列表
        /// </summary>
        public List<long> QQs { get; private set; }
        /// <summary>
        /// 是否正在运行
        /// </summary>
        public bool IsRun { get; private set; }
        /// <summary>
        /// 是否连接
        /// </summary>
        public bool IsConnect { get; private set; }

        private Socket Socket;
        private Thread ReadThread;
        private Thread DoThread;
        private ConcurrentBag<RobotTask> QueueRead;
        private ConcurrentBag<byte[]> QueueSend;
        private PackStart PackStart;
        private delegate void RobotCall(byte packid, string data);
        private RobotCall RobotCallEvent;
        private RobotConfig Config;
        public Robot(RobotConfig config)
        {
            Config = config;
            RobotCallEvent = new RobotCall(config.action);
            PackStart = new PackStart
            {
                Name = config.name,
                Reg = config.pack,
                Groups = config.groups,
                QQs = config.qqs,
                RunQQ = config.runqq
            };
        }
        public void Start()
        {
            QueueRead = new ConcurrentBag<RobotTask>();
            QueueSend = new ConcurrentBag<byte[]>();
            DoThread = new Thread(() =>
            {
                while (IsRun)
                {
                    try
                    {
                        if (QueueRead.TryTake(out RobotTask task))
                        {
                            RobotCallEvent.Invoke(task.index, task.data);
                        }
                        Thread.Sleep(10);
                    }
                    catch (Exception e)
                    {
                        ServerMain.LogError(e);
                    }
                }
            });

            ReadThread = new Thread(() =>
            {
                while (!IsRun)
                {
                    Thread.Sleep(100);
                }
                DoThread.Start();
                int time = 0;
                while (IsRun)
                {
                    try
                    {
                        if (!IsConnect)
                        {
                            ReConnect();
                        }
                        else if (Socket.Available > 0)
                        {
                            var data = new byte[Socket.Available];
                            Socket.Receive(data);
                            var type = data[^1];
                            data[^1] = 0;
                            QueueRead.Add(new RobotTask
                            {
                                index = type,
                                data = Encoding.UTF8.GetString(data)
                            });
                        }
                        else if (Config.check && time >= 20)
                        {
                            time = 0;
                            if (Socket.Poll(1000, SelectMode.SelectRead))
                            {
                                ServerMain.LogOut("机器人连接中断");
                                IsConnect = false;
                            }
                        }
                        else if (QueueSend.TryTake(out byte[] Send))
                        {
                            Socket.Send(Send);
                        }
                        time++;
                        Thread.Sleep(50);
                    }
                    catch (Exception e)
                    {
                        ServerMain.LogError("机器人连接失败");
                        ServerMain.LogError(e);
                        IsConnect = false;
                        ServerMain.LogError($"机器人{Config.time}毫秒后重连");
                        Thread.Sleep(Config.time);
                        ServerMain.LogError("机器人重连中");
                    }
                }
            });
            ReadThread.Start();
            IsRun = true;
        }
        private void ReConnect()
        {
            if (Socket != null)
                Socket.Close();
            try
            {
                Socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
                Socket.Connect(Config.ip, Config.port);

                var data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(PackStart) + " ");
                data[^1] = 0;

                Socket.Send(data);

                while (Socket.Available == 0)
                {
                    Thread.Sleep(10);
                }

                data = new byte[Socket.Available];
                Socket.Receive(data);
                QQs = JsonConvert.DeserializeObject<List<long>>(Encoding.UTF8.GetString(data));

                QueueRead.Clear();
                QueueSend.Clear();
                ServerMain.LogOut("机器人已连接");
                IsConnect = true;
            }
            catch (Exception e)
            {
                ServerMain.LogError("机器人连接失败");
                ServerMain.LogError(e);
            }
        }
        public void CallEvent(long eventid, int dofun, List<object> arg)
        {
            var data = BuildPack.Build(new EventCallPack { eventid = eventid, dofun = dofun, arg = arg }, 59);
            QueueSend.Add(data);
        }
        public void SendGroupMessage(long qq, long id, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupMessagePack { qq = qq, id = id, message = message }, 52);
            QueueSend.Add(data);
        }
        public void SendGroupPrivateMessage(long qq, long id, long fid, List<string> message)
        {
            var data = BuildPack.Build(new SendGroupPrivateMessagePack { qq = qq, id = id, fid = fid, message = message }, 53);
            QueueSend.Add(data);
        }
        public void SendFriendMessage(long qq, long id, List<string> message)
        {
            var data = BuildPack.Build(new SendFriendMessagePack { qq = qq, id = id, message = message }, 54);
            QueueSend.Add(data);
        }
        public void SendGroupImage(long qq, long id, string img)
        {
            var data = BuildPack.BuildImage(qq, id, 0, img, 61);
            QueueSend.Add(data);
        }
        public void SendGroupPrivateImage(long qq, long id, long fid, string img)
        {
            var data = BuildPack.BuildImage(qq, id, fid, img, 62);
            QueueSend.Add(data);
        }
        public void SendFriendImage(long qq, long id, string img)
        {
            var data = BuildPack.BuildImage(qq, id, 0, img, 63);
            QueueSend.Add(data);
        }

        public void SendGroupSound(long qq, long id, string sound)
        {
            var data = BuildPack.BuildSound(qq, id, sound, 74);
            QueueSend.Add(data);
        }
        public void SendGroupImageFile(long qq, long id, string file)
        {
            var data = BuildPack.Build(new LoadFileSendToGroupImagePack { qq = qq, id = id, file = file, }, 75);
            QueueSend.Add(data);
        }
        public void Stop()
        {
            ServerMain.LogOut("机器人正在断开");
            IsRun = false;
            if (Socket != null)
                Socket.Close();
            ServerMain.LogOut("机器人已断开");
        }
    }
    class Runtest
    {
        private static Robot Robot;
        private static void Call(byte packid, string data)
        {
            Console.WriteLine($"收到消息{data}");
            switch (packid)
            {
                case 49:
                    var pack = JsonConvert.DeserializeObject<GroupMessageEventPack>(data);
                    Robot.SendGroupMessage(Robot.QQs[0], pack.id, new()
                    { $"{pack.fid} {pack.name} 你发送了消息：{pack.message[1]}" });
                    break;
                case 50:
                    break;
                case 51:
                    break;
            }
        }
        public static void Main()
        {
            //var config = new RobotConfig
            //{
            //    name = "Demo",
            //    groups = new() { 571239090 },
            //    qqs = new() { 402067010 },
            //    runqq = 0,
            //    pack = new() { 49, 50, 51 },
            //    ip = "127.0.0.1",
            //    port = 23333,
            //    time = 10000,
            //    check = true,
            //    action = Call
            //};
            var config = new RobotConfig
            {
                name = "Demo",
                groups = null,
                qqs = null,
                runqq = 0,
                pack = new() { 49, 50, 51 },
                ip = "127.0.0.1",
                port = 23333,
                time = 10000,
                check = true,
                action = Call
            };
            Robot = new Robot(config);
            Robot.Start();
        }
    }
}
