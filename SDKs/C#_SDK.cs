using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Text;
using System.Threading;
//请用net5运行
//并安装Newtonsoft.Json
namespace ColoryrSDK
{
    /// <summary>
    /// 基础包
    /// </summary>
    abstract record PackBase
    {
        public long qq { get; set; }
    }
    /// <summary>
    /// 开始包
    /// </summary>
    record PackStart
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
    record GroupMessageEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 发送群消息包
    /// </summary>
    record SendGroupMessagePack : PackBase
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 发送朋友消息包
    /// </summary>
    record SendFriendMessagePack : PackBase
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 发送群私聊消息包
    /// </summary>
    record SendGroupPrivateMessagePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 临时消息事件包
    /// </summary>
    record TempMessageEventPack : PackBase
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
    record FriendMessageEventPack : PackBase
    {
        public long id { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
        public int time { get; set; }
    }
    /// <summary>
    /// 发送群图片包
    /// </summary>
    record SendGroupImagePack : PackBase
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    /// <summary>
    /// 发送群私聊图片包
    /// </summary>
    record SendGroupPrivateImagePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string img { get; set; }
    }
    /// <summary>
    /// 发送朋友图片包
    /// </summary>
    record SendFriendImagePack : PackBase
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    /// <summary>
    /// 群全部禁言包
    /// </summary>
    record GroupMuteAll : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 群全部解禁包
    /// </summary>
    record GroupUnmuteAll : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 设置群员名字包
    /// </summary>
    record SetGroupMemberCard : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string card { get; set; }
    }
    /// <summary>
    /// 设置群名包
    /// </summary>
    record SetGroupName : PackBase
    {
        public long id { get; set; }
        public string name { get; set; }
    }
    /// <summary>
    /// 加好友请求事件包
    /// </summary>
    record NewFriendRequestEventPack : PackBase
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
    record EventCallPack : PackBase
    {
        public long eventid { get; set; }
        public int dofun { get; set; }
        public List<object> arg { get; set; }
    }
    /// <summary>
    /// 撤回消息包
    /// </summary>
    record ReCallMessage : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 加载本地图片发送到群包
    /// </summary>
    record LoadFileSendToGroupImagePack : PackBase
    {
        public long id { get; set; }
        public string file { get; set; }
    }
    /// <summary>
    /// 发送群消息之后的事件包
    /// </summary>
    record GroupMessagePostSendEventPack : PackBase
    {
        public long id { get; set; }
        public bool res { get; set; }
        public List<string> message { get; set; }
        public string error { get; set; }
    }
    /// <summary>
    /// 发送群员戳一戳
    /// </summary>
    record MemberNudgePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
    }
    /// <summary>
    /// 发送朋友戳一戳
    /// </summary>
    record FriendNudgePack : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 获取图片Url
    /// </summary>
    record GetImageUrlPack : PackBase
    {
        public string uuid { get; set; }
    }
    /// <summary>
    /// 返回Url
    /// </summary>
    record ReImagePack : PackBase
    {
        public string uuid { get; set; }
        public string url { get; set; }
    }
    /// <summary>
    /// 消息队列
    /// </summary>
    record MessageBuffPack : PackBase
    {
        public bool send { get; set; }
        public List<string> text { get; set; }
        public string img { get; set; }
        public string imgurl { get; set; }
        public int type;
        public long id { get; set; }
        public long fid { get; set; }
    }

    //更多pack请看文档说明
    class BuildPack
    {
        /// <summary>
        /// 构建一个包
        /// </summary>
        /// <param name="obj">对象</param>
        /// <param name="index">包ID</param>
        /// <returns>构建好的包</returns>
        public static byte[] Build(object obj, byte index)
        {
            byte[] data = Encoding.UTF8.GetBytes(JsonConvert.SerializeObject(obj) + " ");
            data[^1] = index;
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
        /// <returns>构建好的包</returns>
        public static byte[] BuildImage(long qq, long id, long fid, string img, byte index)
        {
            string temp = "";
            if (id != 0)
            {
                temp += $"id={id}&";
            }
            if (fid != 0)
            {
                temp += $"fid={fid}&";
            }
            temp += $"qq={qq}&img={img}";
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[^1] = index;
            return data;
        }
        /// <summary>
        /// 构建一个发送语音的包
        /// </summary>
        /// <param name="qq">运行的qq号</param>
        /// <param name="id">发送给的id</param>
        /// <param name="sound">音频BASE64</param>
        /// <param name="index">包ID</param>
        /// <returns>构建好的包</returns>
        public static byte[] BuildSound(long qq, long id, string sound, byte index)
        {
            string temp = $"id={id}&qq={qq}&sound={sound}";
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[^1] = index;
            return data;
        }
        /// <summary>
        /// 构建消息队列物图片包
        /// </summary>
        /// <param name="qq">运行的qq号</param>
        /// <param name="id">发送给的id</param>
        /// <param name="fid">发送给的fid</param>
        /// <param name="type">消息类型</param>
        /// <param name="img">图片BASE64流</param>
        /// <returns>构建好的包</returns>
        public static byte[] BuildBuffImage(long qq, long id, long fid, int type, string img, bool send)
        {
            string temp = "";
            if (id != 0)
            {
                temp += $"id={id}&";
            }
            if (fid != 0)
            {
                temp += $"fid={fid}&";
            }
            temp += $"qq={qq}&img={img}&type={type}&send={send}";
            byte[] data = Encoding.UTF8.GetBytes(temp + " ");
            data[^1] = 97;
            return data;
        }
    }
    record RobotTask
    {
        public byte index { get; set; }
        public string data { get; set; }
    }
    public record RobotConfig
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
    public enum LogType
    {
        Log, Error
    }
    public enum StateType
    {
        Disconnect, Connecting, Connect
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

        private delegate void RobotCall(byte packid, string data);
        private delegate void RobotLog(LogType type, string data);
        private delegate void RobotState(StateType type);
        private RobotCall RobotCallEvent;
        private RobotLog RobotLogEvent;
        private RobotState RobotStateEvent;

        private Socket Socket;
        private Thread ReadThread;
        private Thread DoThread;
        private ConcurrentBag<RobotTask> QueueRead;
        private ConcurrentBag<byte[]> QueueSend;
        private PackStart PackStart;
        private RobotConfig Config;

        /// <summary>
        /// 第一次连接检查
        /// </summary>
        public bool IsFirst = true;

        private int Times = 0;
        /// <summary>
        /// 设置配置
        /// </summary>
        /// <param name="Config">机器人配置</param>
        public void Set(RobotConfig Config)
        {
            this.Config = Config;

            RobotCallEvent = new(Config.CallAction);
            RobotLogEvent = new(Config.LogAction);
            RobotStateEvent = new(Config.StateAction);

            PackStart = new()
            {
                Name = Config.Name,
                Reg = Config.Pack,
                Groups = Config.Groups,
                QQs = Config.QQs,
                RunQQ = Config.RunQQ
            };
        }
        /// <summary>
        /// 启动机器人
        /// </summary>
        public void Start()
        {
            if (ReadThread?.IsAlive == true)
                return;
            QueueRead = new();
            QueueSend = new();
            DoThread = new(() =>
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
                        LogError(e);
                    }
                }
            });

            ReadThread = new(() =>
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
                            IsFirst = false;
                            Times = 0;
                            RobotStateEvent.Invoke(StateType.Connect);
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
                        else if (Config.Check && time >= 20)
                        {
                            time = 0;
                            if (Socket.Poll(1000, SelectMode.SelectRead))
                            {
                                LogOut("机器人连接中断");
                                IsConnect = false;
                                RobotStateEvent.Invoke(StateType.Disconnect);
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
                        if (IsFirst)
                        {
                            IsRun = false;
                            LogError("机器人连接失败");
                        }
                        else
                        {
                            Times++;
                            if (Times == 10)
                            {
                                IsRun = false;
                                LogError("重连失败次数过多");
                            }
                            LogError("机器人连接失败");
                            LogError(e);
                            IsConnect = false;
                            LogError($"机器人{Config.Time}毫秒后重连");
                            Thread.Sleep(Config.Time);
                            LogError("机器人重连中");
                        }
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

            RobotStateEvent.Invoke(StateType.Connecting);

            Socket = new Socket(SocketType.Stream, ProtocolType.Tcp);
            Socket.Connect(Config.IP, Config.Port);

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
            LogOut("机器人已连接");
            IsConnect = true;
        }
        public void ReCall(long qq, long id)
        {
            var data = BuildPack.Build(new ReCallMessage { qq = qq, id = id }, 71);
            QueueSend.Add(data);
        }
        public void CallEvent(long qq, long eventid, int dofun, List<object> arg)
        {
            var data = BuildPack.Build(new EventCallPack { qq = qq, eventid = eventid, dofun = dofun, arg = arg }, 59);
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
            var data = BuildPack.Build(new LoadFileSendToGroupImagePack { qq = qq, id = id, file = file }, 75);
            QueueSend.Add(data);
        }
        public void SendMemberNudge(long qq, long id, long fid)
        {
            var data = BuildPack.Build(new MemberNudgePack { qq = qq, id = id, fid = fid }, 84);
            QueueSend.Add(data);
        }
        public void SendFriendNudge(long qq, long id)
        {
            var data = BuildPack.Build(new FriendNudgePack { qq = qq, id = id }, 83);
            QueueSend.Add(data);
        }
        public void GetImageUrl(long qq, string uuid)
        {
            var data = BuildPack.Build(new GetImageUrlPack { qq = qq, uuid = uuid }, 90);
            QueueSend.Add(data);
        }
        public void AddMessageBuff(long qq, long id, long fid, List<string> text, string imgurl, int type, bool send)
        {
            var data = BuildPack.Build(new MessageBuffPack { qq = qq, send = send, text = text, imgurl = imgurl, type = type, fid = fid, id = id }, 97);
            QueueSend.Add(data);
        }
        public void AddMessageImageBuff(long qq, long id, long fid, string img, int type, bool send)
        {
            var data = BuildPack.BuildBuffImage(qq, id, fid, type, img, send);
            QueueSend.Add(data);
        }
        private void SendStop()
        {
            var data = BuildPack.Build(new object(), 127);
            Socket.Send(data);
        }
        public void Stop()
        {
            LogOut("机器人正在断开");
            IsRun = false;
            SendStop();
            if (Socket != null)
                Socket.Close();
            LogOut("机器人已断开");
        }
        private void LogError(Exception e)
        {
            RobotLogEvent.Invoke(LogType.Error, "机器人错误\n" + e.ToString());
        }
        private void LogError(string a)
        {
            RobotLogEvent.Invoke(LogType.Error, "机器人错误:" + a);
        }
        private void LogOut(string a)
        {
            RobotLogEvent.Invoke(LogType.Log, a);
        }
    }
}
