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
    public abstract record PackBase
    {
        /// <summary>
        /// QQ号
        /// </summary>
        public long qq { get; set; }
    }
    /// <summary>
    /// 0 [插件]插件开始连接
    /// </summary>
    public record StartPack
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
    public enum Sex
    {
        MALE,
        FEMALE,
        UNKNOWN
    }
    public enum MemberPermission
    {
        MEMBER,
        ADMINISTRATOR,
        OWNER
    }
    public record UserProfile
    {
        /// <summary>
        /// 昵称
        /// </summary>
        public string nickname { get; set; }
        /// <summary>
        /// 邮箱
        /// </summary>
        public string email { get; set; }
        /// <summary>
        /// 年龄
        /// </summary>
        public int age { get; set; }
        /// <summary>
        /// QQ等级
        /// </summary>
        public int qLevel { get; set; }
        /// <summary>
        /// 性别
        /// </summary>
        public Sex sex { get; set; }
    }
    /// <summary>
    /// 92 [插件]获取朋友信息
    /// </summary>
    public record FriendInfoPack
    {
        /// <summary>
        /// QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 头像图片
        /// </summary>
        public string img { get; set; }
        /// <summary>
        /// 好友备注
        /// </summary>
        public string remark { get; set; }
        /// <summary>
        /// 用户详细资料
        /// </summary>
        public UserProfile userProfile { get; set; }
    }
    public record GroupInfo
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id;
        /// <summary>
        /// 群名
        /// </summary>
        public string name;
        /// <summary>
        /// 群头像
        /// </summary>
        public string img;
        /// <summary>
        /// 所有者QQ号
        /// </summary>
        public long oid;
        /// <summary>
        /// 机器人所拥有的权限
        /// </summary>
        public MemberPermission per;
    }
    /// <summary>
    /// 91 [插件]获取群成员信息
    /// </summary>
    public record MemberInfoPack
    {
        /// <summary>
        /// QQ号码
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 昵称
        /// </summary>
        public string nick { get; set; }
        /// <summary>
        /// 头像图片
        /// </summary>
        public string img { get; set; }
        /// <summary>
        /// 群权限
        /// </summary>
        public MemberPermission per { get; set; }
        /// <summary>
        /// 群名片
        /// </summary>
        public string nameCard { get; set; }
        /// <summary>
        /// 群头衔
        /// </summary>
        public string specialTitle { get; set; }
        /// <summary>
        /// 头像下载链接
        /// </summary>
        public string avatarUrl { get; set; }
        /// <summary>
        /// 被禁言剩余时长
        /// </summary>
        public int muteTimeRemaining { get; set; }
        /// <summary>
        /// 入群时间
        /// </summary>
        public int joinTimestamp { get; set; }
        /// <summary>
        /// 最后发言时间
        /// </summary>
        public int lastSpeakTimestamp { get; set; }
    }
    /// <summary>
    /// 56 [插件]获取好友列表
    /// </summary>
    public record ListFriendPack : PackBase
    {
        /// <summary>
        /// 朋友列表
        /// </summary>
        public List<FriendInfoPack> friends { get; set; }
    }
    /// <summary>
    /// 55 [插件]获取群列表
    /// </summary>
    public record ListGroupPack : PackBase
    {
        /// <summary>
        /// 群列表
        /// </summary>
        public List<GroupInfo> groups { get; set; }
    }
    /// <summary>
    /// 66 [插件]解除禁言
    /// </summary>
    public record UnmuteGroupMemberPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 成员QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 57 [插件]获取群成员
    /// </summary>
    public record ListMemberPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 成员列表
        /// </summary>
        public List<MemberInfoPack> members { get; set; }
    }
    /// <summary>
    /// 64 [插件]删除群员
    /// </summary>
    public record DeleteGroupMemberPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 群员QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 96 [插件]设置群精华消息
    /// </summary>
    public record EssenceMessagePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 消息ID
        /// </summary>
        public long mid { get; set; }
    }
    /// <summary>
    /// 群消息事件包
    /// </summary>
    public record GroupMessageEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
        public string name { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 发送群消息包
    /// </summary>
    public record SendGroupMessagePack : PackBase
    {
        public long id { get; set; }
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 54 [插件]发送好友消息
    /// </summary>
    public record SendFriendMessagePack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 53 [插件]发送私聊消息
    /// </summary>
    public record SendGroupPrivateMessagePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 成员QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 临时消息事件包
    /// </summary>
    public record TempMessageEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
    }
    /// <summary>
    /// 朋友消息事件包
    /// </summary>
    public record FriendMessageEventPack : PackBase
    {
        public long id { get; set; }
        public string name { get; set; }
    }
    /// <summary>
    /// 发送群图片包
    /// </summary>
    public record SendGroupImagePack : PackBase
    {
        public long id { get; set; }
        public string img { get; set; }
    }
    /// <summary>
    /// 发送群私聊图片包
    /// </summary>
    public record SendGroupPrivateImagePack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
    }
    /// <summary>
    /// 发送朋友图片包
    /// </summary>
    public record SendFriendImagePack : PackBase
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
    /// 69 [插件]设置群名片
    /// </summary>
    public record SetGroupMemberCard : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 成员QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 新的群名片
        /// </summary>
        public string card { get; set; }
    }
    /// <summary>
    /// 70 [插件]设置群名
    /// </summary>
    public record SetGroupNamePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 新的群名
        /// </summary>
        public string name { get; set; }
    }
    /// <summary>
    /// 加好友请求事件包
    /// </summary>
    public record NewFriendRequestEventPack : PackBase
    {
        public long id { get; set; }
        public long fid { get; set; }
    }
    /// <summary>
    /// 59 [插件]回应事件
    /// </summary>
    public record EventCallPack : PackBase
    {
        /// <summary>
        /// 事件ID
        /// </summary>
        public long eventid { get; set; }
        /// <summary>
        /// 方法
        /// </summary>
        public int dofun { get; set; }
        /// <summary>
        /// 附带参数
        /// </summary>
        public List<object> arg { get; set; }
    }
    /// <summary>
    /// 撤回消息包
    /// </summary>
    public record ReCallMessage : PackBase
    {
        public long id { get; set; }
    }
    /// <summary>
    /// 75 [插件]从本地文件加载图片发送到群
    /// </summary>
    public record LoadFileSendToGroupImagePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        public string file { get; set; }
    }
    /// <summary>
    /// 76 [插件]从本地文件加载图片发送到群私聊
    /// </summary>
    public record LoadFileSendToGroupPrivateImagePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 群员QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        public string file { get; set; }
    }
    /// <summary>
    /// 78 [插件]从本地文件加载语音发送到群s
    /// </summary>
    public record LoadFileSendToGroupSoundPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        public string file { get; set; }
    }
    /// <summary>
    /// 发送群消息之后的事件包
    /// </summary>
    public record GroupMessagePostSendEventPack : PackBase
    {
        public long id { get; set; }
        public bool res { get; set; }
    }
    /// <summary>
    /// 84 [插件]发送群成员戳一戳
    /// </summary>
    public record MemberNudgePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 成员QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 83 [插件]发送朋友戳一戳
    /// </summary>
    public record FriendNudgePack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 57 [插件]获取群成员
    /// </summary>
    public record GetGroupMemberInfoPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 58 [插件]获取群设置
    /// </summary>
    public record GetGroupSettingPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 获取图片Url
    /// </summary>
    public record GetImageUrlPack : PackBase
    {
        /// <summary>
        /// 图片UUID
        /// </summary>
        public string uuid { get; set; }
    }
    /// <summary>
    /// 92 [插件]获取朋友信息
    /// </summary>
    public record GetFriendInfoPack : PackBase
    {
        /// <summary>
        /// 朋友QQ号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 91 [插件]获取群成员信息
    /// </summary>
    public record GetMemberInfo : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 群员QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 55 [插件]获取群列表
    /// 56 [插件] 获取好友列表
    /// </summary>
    public record GetPack : PackBase
    {

    }
    /// <summary>
    /// 67 [插件]开启全员禁言
    /// </summary>
    public record GroupMuteAllPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 68 [插件]关闭全员禁言
    /// </summary>
    public record GroupUnmuteAllPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 77 [插件]从本地文件加载图片发送到朋友
    /// </summary>
    public record LoadFileSendToFriendImagePack : PackBase
    {
        /// <summary>
        /// 朋友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        public string file { get; set; }
    }
    /// <summary>
    /// 90 [插件]获取图片Url
    /// </summary>
    public record ReImagePack : PackBase
    {
        /// <summary>
        /// 图片UUID
        /// </summary>
        public string uuid { get; set; }
        /// <summary>
        /// 图片地址
        /// </summary>
        public string url { get; set; }
    }
    /// <summary>
    /// 97 [插件]消息队列
    /// </summary>
    public record MessageBuffPack : PackBase
    {
        /// <summary>
        /// 是否发送
        /// </summary>
        public bool send { get; set; }
        /// <summary>
        /// 消息内容
        /// </summary>
        public List<string> text { get; set; }
        /// <summary>
        /// 图片内容
        /// </summary>
        public string img { get; set; }
        /// <summary>
        /// 图片路径
        /// </summary>
        public string imgurl { get; set; }
        /// <summary>
        /// 发送对象类型
        /// </summary>
        public int type;
        /// <summary>
        /// 发送目标
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 发送目标
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 93 [插件]发送音乐分享
    /// </summary>
    public record MusicSharePack : PackBase
    {
        /// <summary>
        /// 发送目标
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 发送目标
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 音乐类型
        /// </summary>
        public int type { get; set; }
        /// <summary>
        /// 目标类型
        /// </summary>
        public int type1 { get; set; }
        /// <summary>
        /// 标题
        /// </summary>
        public string title { get; set; }
        /// <summary>
        /// 概要
        /// </summary>
        public string summary { get; set; }
        /// <summary>
        /// 跳转Url
        /// </summary>
        public string jumpUrl { get; set; }
        /// <summary>
        /// 图片Url
        /// </summary>
        public string pictureUrl { get; set; }
        /// <summary>
        /// 音乐Url
        /// </summary>
        public string musicUrl { get; set; }
    }
    /// <summary>
    /// 65 [插件]禁言群员
    /// </summary>
    public record MuteGroupMemberPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 成员QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 时间
        /// </summary>
        public int time { get; set; }
    }
    /// <summary>
    /// 71 [插件]撤回消息
    /// </summary>
    public record ReCallMessagePack : PackBase
    {
        /// <summary>
        /// 消息ID
        /// </summary>
        public int id { get; set; }
    }
    public class BuildPack
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
        private StartPack PackStart;
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
        public void AddTask(byte[] data)
        {
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
