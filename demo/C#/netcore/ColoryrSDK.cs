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
    /// 58 [插件]获取群设置
    /// </summary>
    public record GroupSettingPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 设定
        /// </summary>
        public GroupSettings setting { get; set; }
    }
    /// <summary>
    /// 91 [插件]获取群成员信息
    /// </summary>
    public record MemberInfoPack : PackBase
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
    /// 92 [插件]获取朋友信息
    /// </summary>
    public record FriendInfoPack : PackBase
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
    /// <summary>
    /// 101 [插件]获取群文件
    /// </summary>
    public record GroupFilesPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 文件列表
        /// </summary>
        public List<string> files { get; set; }
    }
    /// <summary>
    /// 性别
    /// </summary>
    public enum Sex
    {
        MALE,
        FEMALE,
        UNKNOWN
    }
    /// <summary>
    /// 成员权限
    /// </summary>
    public enum MemberPermission
    {
        MEMBER,
        ADMINISTRATOR,
        OWNER
    }
    /// <summary>
    /// 用户信息
    /// </summary>
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
    /// 群信息
    /// </summary>
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
    /// 群设定
    /// </summary>
    public record GroupSettings
    {
        /// <summary>
        /// 入群公告, 没有时为空字符串.
        /// </summary>
        public string entranceAnnouncement { get; set; }
        /// <summary>
        /// 全体禁言状态
        /// </summary>
        public bool isMuteAll { get; set; }
        /// <summary>
        /// 允许群员邀请好友入群的状态
        /// </summary>
        public bool isAllowMemberInvite { get; set; }
        /// <summary>
        /// 自动加群审批
        /// </summary>
        public bool isAutoApproveEnabled { get; set; }
        /// <summary>
        /// 匿名聊天
        /// </summary>
        public bool isAnonymousChatEnabled { get; set; }
    }
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
    /// <summary>
    /// 1 [机器人]图片上传前. 可以阻止上传（事件）
    /// </summary>
    public record BeforeImageUploadPack : PackBase
    {
        /// <summary>
        /// 发送给的号码
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 图片ID
        /// </summary>
        public string name { get; set; }
    }
    /// <summary>
    /// 2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）
    /// </summary>
    public record BotAvatarChangedPack : PackBase
    {

    }
    /// <summary>
    /// 3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
    /// </summary>
    public record BotGroupPermissionChangePack : PackBase
    {
        /// <summary>
        /// 当前权限
        /// </summary>
        public MemberPermission now { get; set; }
        /// <summary>
        /// 旧的权限
        /// </summary>
        public MemberPermission old { get; set; }
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 4 [机器人]被邀请加入一个群（事件）
    /// </summary>
    public record BotInvitedJoinGroupRequestEventPack : PackBase
    {
        /// <summary>
        /// 事件ID
        /// </summary>
        public long eventid { get; set; }
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 邀请人QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
    /// </summary>
    public record BotJoinGroupEventAPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
    /// </summary>
    public record BotJoinGroupEventBPack : BotJoinGroupEventAPack
    {
        /// <summary>
        /// 邀请人QQ
        /// </summary>
        public long fid;
    }
    /// <summary>
    /// 7 [机器人]主动退出一个群（事件）
    /// </summary>
    public record BotLeaveEventAPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 8 [机器人]被管理员或群主踢出群（事件）
    /// </summary>
    public record BotLeaveEventBPack : BotLeaveEventAPack
    {
        /// <summary>
        /// 执行人QQ
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 9 [机器人]被禁言（事件）
    /// </summary>
    public record BotMuteEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 禁言时间
        /// </summary>
        public int time { get; set; }
    }
    /// <summary>
    /// 10 [机器人]主动离线（事件）
    /// 12 [机器人]被服务器断开（事件）
    /// 13 [机器人]因网络问题而掉线（事件）
    /// </summary>
    public record BotOfflineEventAPack : PackBase
    {
        /// <summary>
        /// 离线原因
        /// </summary>
        public string message { get; set; }
    }
    /// <summary>
    /// 11 [机器人]被挤下线（事件）
    /// </summary>
    public record BotOfflineEventBPack : BotOfflineEventAPack
    {
        /// <summary>
        /// 标题
        /// </summary>
        public string title { get; set; }
    }
    /// <summary>
    /// 14 [机器人]服务器主动要求更换另一个服务器（事件）
    /// </summary>
    public record BotOfflineEventCPack : PackBase
    {

    }
    /// <summary>
    /// 15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）
    /// </summary>
    public record BotOnlineEventPack : PackBase
    {

    }
    /// <summary>
    /// 16 [机器人]主动或被动重新登录（事件）
    /// </summary>
    public record BotReloginEventPack : PackBase
    {
        /// <summary>
        /// 原因消息
        /// </summary>
        public string message { get; set; }
    }
    /// <summary>
    /// 17 [机器人]被取消禁言（事件）
    /// </summary>
    public record BotUnmuteEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 18 [机器人]成功添加了一个新好友（事件）
    /// </summary>
    public record FriendAddEventPack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 19 [机器人]好友头像修改（事件）
    /// </summary>
    public record FriendAvatarChangedEventPack : PackBase
    {
        /// <summary>
        /// 图片url
        /// </summary>
        public string url { get; set; }
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 20 [机器人]好友已被删除（事件）
    /// </summary>
    public record FriendDeleteEventPack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 21 [机器人]在好友消息发送后广播（事件）
    /// </summary>
    public record FriendMessagePostSendEventPack : PackBase
    {
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 是否成功发送
        /// </summary>
        public bool res { get; set; }
        /// <summary>
        /// 错误消息
        /// </summary>
        public string error { get; set; }
    }
    /// <summary>
    /// 22 [机器人]在发送好友消息前广播（事件）
    /// </summary>
    public record FriendMessagePreSendEventPack : PackBase
    {
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 23 [机器人]好友昵称改变（事件）
    /// </summary>
    public record FriendRemarkChangeEventPack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 旧的昵称
        /// </summary>
        public string old { get; set; }
        /// <summary>
        /// 新的昵称
        /// </summary>
        public string now { get; set; }
    }
    /// <summary>
    /// 24 [机器人]群 "匿名聊天" 功能状态改变（事件）
    /// </summary>
    public record GroupAllowAnonymousChatEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 旧的状态
        /// </summary>
        public bool old { get; set; }
        /// <summary>
        /// 新的状态
        /// </summary>
        public bool now { get; set; }
    }
    /// <summary>
    /// 25 [机器人]群 "坦白说" 功能状态改变（事件）
    /// </summary>
    public record GroupAllowConfessTalkEventPack : GroupAllowAnonymousChatEventPack
    {

    }
    /// <summary>
    /// 26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）
    /// </summary>
    public record GroupAllowMemberInviteEventPack : GroupAllowAnonymousChatEventPack
    {

    }
    /// <summary>
    /// 27 [机器人]入群公告改变（事件）
    /// </summary>
    public record GroupEntranceAnnouncementChangeEventPack : GroupAllowAnonymousChatEventPack
    {

    }
    /// <summary>
    /// 28 [机器人]在群消息发送后广播（事件）
    /// </summary>
    public record GroupMessagePostSendEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 是否发送成功
        /// </summary>
        public bool res { get; set; }
        /// <summary>
        /// 发送的消息
        /// </summary>
        public List<string> message { get; set; }
        /// <summary>
        /// 错误消息
        /// </summary>
        public string error { get; set; }
    }
    /// <summary>
    /// 29 [机器人]在发送群消息前广播（事件）
    /// </summary>
    public record GroupMessagePreSendEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 30 [机器人]群 "全员禁言" 功能状态改变（事件）
    /// </summary>
    public record GroupMuteAllEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 旧的状态
        /// </summary>
        public bool old { get; set; }
        /// <summary>
        /// 新的状态
        /// </summary>
        public bool now { get; set; }
    }
    /// <summary>
    /// 31 [机器人]群名改变（事件）
    /// </summary>
    public record GroupNameChangeEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 旧的名字
        /// </summary>
        public string old { get; set; }
        /// <summary>
        /// 新的名字
        /// </summary>
        public string now { get; set; }
    }
    /// <summary>
    /// 32 [机器人]图片上传成功（事件）
    /// </summary>
    public record ImageUploadEventAPack : PackBase
    {
        /// <summary>
        /// 目标ID
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 图片ID
        /// </summary>
        public string uuid { get; set; }
    }
    /// <summary>
    /// 33 [机器人]图片上传失败（事件）
    /// </summary>
    public record ImageUploadEventBPack : ImageUploadEventAPack
    {
        /// <summary>
        /// 错误消息
        /// </summary>
        public string error { get; set; }
        /// <summary>
        /// 错误码
        /// </summary>
        public int index { get; set; }
    }
    /// <summary>
    /// 34 [机器人]成员群名片改动（事件）
    /// </summary>
    public record MemberCardChangeEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 旧的状态
        /// </summary>
        public string old { get; set; }
        /// <summary>
        /// 新的状态
        /// </summary>
        public string now { get; set; }
    }
    /// <summary>
    /// 35 [机器人]成成员被邀请加入群（事件）
    /// 36 [机器人] 成员主动加入群（事件）
    /// </summary>
    public record MemberJoinEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 进群人QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
    /// </summary>
    public record MemberJoinRequestEventPack : PackBase
    {
        /// <summary>
        /// 事件ID
        /// </summary>
        public long eventid { get; set; }
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 进群人
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 邀请人ID
        /// </summary>
        public long qif { get; set; }
        /// <summary>
        /// 入群消息
        /// </summary>
        public string message { get; set; }
    }
    /// <summary>
    /// 38 [机器人]成员被踢出群（事件）
    /// </summary>
    public record MemberLeaveEventAPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 被踢出人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long eid { get; set; }
    }
    /// <summary>
    /// 39 [机器人]成员主动离开（事件）
    /// </summary>
    public record MemberLeaveEventBPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 离开人QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 40 [机器人]群成员被禁言（事件）
    /// </summary>
    public record MemberMuteEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 被禁言的QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 执行禁言的QQ号
        /// </summary>
        public long eid { get; set; }
        /// <summary>
        /// 时间
        /// </summary>
        public int time { get; set; }
    }
    /// <summary>
    /// 41 [机器人]成员权限改变（事件）
    /// </summary>
    public record MemberPermissionChangeEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 旧的状态
        /// </summary>
        public MemberPermission old { get; set; }
        /// <summary>
        /// 新的状态
        /// </summary>
        public MemberPermission now { get; set; }
    }
    /// <summary>
    /// 42 [机器人]成员群头衔改动（事件）
    /// </summary>
    public record MemberSpecialTitleChangeEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 旧的状态
        /// </summary>
        public string old { get; set; }
        /// <summary>
        /// 新的状态
        /// </summary>
        public string now { get; set; }
    }
    /// <summary>
    /// 43 [机器人]群成员被取消禁言（事件）
    /// </summary>
    public record MemberUnmuteEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 被执行人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 执行人QQ号
        /// </summary>
        public long eid { get; set; }
    }
    /// <summary>
    /// 44 [机器人]好友消息撤回（事件）
    /// </summary>
    public record MessageRecallEventAPack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 消息ID
        /// </summary>
        public int[] mid { get; set; }
        /// <summary>
        /// 时间
        /// </summary>
        public int time { get; set; }
    }
    /// <summary>
    /// 45 [机器人]群消息撤回事件（事件）
    /// </summary>
    public record MessageRecallEventBPack : PackBase
    {
        /// <summary>
        /// 群员QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 撤回者
        /// </summary>
        public long oid { get; set; }
    }
    /// <summary>
    /// 46 [机器人]一个账号请求添加机器人为好友（事件）
    /// </summary>
    public record NewFriendRequestEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 请求人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 请求消息
        /// </summary>
        public string message;
        /// <summary>
        /// 事件ID
        /// </summary>
        public long eventid;
    }
    /// <summary>
    /// 47 [机器人]在群临时会话消息发送后广播（事件）
    /// </summary>
    public record TempMessagePostSendEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 发送到的QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 是否成功发送
        /// </summary>
        public bool res { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
        /// <summary>
        /// 错误信息
        /// </summary>
        public string error { get; set; }
    }
    /// <summary>
    /// 48 [机器人]在发送群临时会话消息前广播（事件）
    /// </summary>
    public record TempMessagePreSendEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 发送人的QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 49 [机器人]收到群消息（事件）
    /// </summary>
    public record GroupMessageEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 发送人QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 发送人权限
        /// </summary>
        public MemberPermission permission { get; set; }
        /// <summary>
        /// 发送的消息
        /// </summary>
        public List<string> message { get; set; }
        /// <summary>
        /// 群名片
        /// </summary>
        public string name { get; set; }
    }
    /// <summary>
    /// 50 [机器人]收到群临时会话消息（事件）
    /// </summary>
    public record TempMessageEventPack : GroupMessageEventPack
    {
        /// <summary>
        /// 时间
        /// </summary>
        public int time { get; set; }
    }
    /// <summary>
    /// 51 [机器人]收到朋友消息（事件）
    /// </summary>
    public record FriendMessageEventPack : PackBase
    {
        /// <summary>
        /// 朋友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
        /// <summary>
        /// 时间
        /// </summary>
        public int time { get; set; }
        /// <summary>
        /// 昵称
        /// </summary>
        public string name { get; set; }
    }
    /// <summary>
    /// 52 [插件]发送群消息
    /// </summary>
    public record SendGroupMessagePack : PackBase
    {
        /// <summary>
        /// 群号
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
    /// 55 [插件]获取群列表
    /// 56 [插件] 获取好友列表
    /// </summary>
    public record GetPack : PackBase
    {

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
    /// 71 [插件]撤回消息
    /// </summary>
    public record ReCallMessagePack : PackBase
    {
        /// <summary>
        /// 消息ID
        /// </summary>
        public int id { get; set; }
    }
    /// <summary>
    /// 72 [机器人]友输入状态改变（事件）
    /// </summary>
    public record FriendInputStatusChangedEventPack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 输入状态
        /// </summary>
        public bool input { get; set; }
    }
    /// <summary>
    /// 73 [机器人]好友昵称改变（事件）
    /// </summary>
    public record FriendNickChangedEventPack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 旧的昵称
        /// </summary>
        public string old { get; set; }
        /// <summary>
        /// 新的昵称
        /// </summary>
        public string now { get; set; }
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
    /// 79 [机器人]成员群恢复（事件）
    /// </summary>
    public record MemberJoinRetrieveEventPack : PackBase
    {
        /// <summary>
        /// QQ群
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 成员QQ号
        /// </summary>
        public long fid { get; set; }
    }
    /// <summary>
    /// 80 [机器人]机器人群恢复（事件）
    /// </summary>
    public record BotJoinGroupEventRetrieveEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
    }
    /// <summary>
    /// 81 [机器人]群成员戳一戳（事件）
    /// 82 [机器人]机器人被戳一戳（事件）
    /// </summary>
    public record NudgedEventPack : PackBase
    {
        /// <summary>
        /// QQ群
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 发起戳一戳QQ号
        /// </summary>
        public long fid { get; set; }
        /// <summary>
        /// 被戳成员QQ号
        /// </summary>
        public long aid { get; set; }
        /// <summary>
        /// 戳一戳的动作名称
        /// </summary>
        public string action { get; set; }
        /// <summary>
        /// 戳一戳中设置的自定义后缀
        /// </summary>
        public string suffix { get; set; }
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
    /// 86 [机器人]其他客户端上线（事件）
    /// </summary>
    public record OtherClientOnlineEventPack : OtherClientOfflineEventPack
    {
        /// <summary>
        /// 设备类型
        /// </summary>
        public string kind { get; set; }
    }
    /// <summary>
    /// 87 [机器人]其他客户端离线（事件）
    /// </summary>
    public record OtherClientOfflineEventPack : PackBase
    {
        /// <summary>
        /// 设备ID
        /// </summary>
        public int appId { get; set; }
        /// <summary>
        /// 设备类型
        /// </summary>
        public string platform { get; set; }
        /// <summary>
        /// 设备名字
        /// </summary>
        public string deviceName { get; set; }
        /// <summary>
        /// 设备类型
        /// </summary>
        public string deviceKind { get; set; }
    }
    /// <summary>
    /// 88 [机器人]其他客户端发送消息给 Bot（事件）
    /// </summary>
    public record OtherClientMessageEventPack : OtherClientOfflineEventPack
    {
        /// <summary>
        /// 信息
        /// </summary>
        public List<string> message { get; set; }
    }
    /// <summary>
    /// 89 [机器人]其他客户端发送群消息（事件）
    /// </summary>
    public record GroupMessageSyncEventPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 时间
        /// </summary>
        public int time { get; set; }
        /// <summary>
        /// 消息
        /// </summary>
        public List<string> message { get; set; }
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
    /// 85 [机器人]龙王改变时（事件）
    /// </summary>
    public record GroupTalkativeChangePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 当前龙王
        /// </summary>
        public long now { get; set; }
        /// <summary>
        /// 先前龙王
        /// </summary>
        public long old { get; set; }
    }
    /// <summary>
    /// 96 [插件]发送朋友骰子
    /// </summary>
    public record SendFriendDicePack : PackBase
    {
        /// <summary>
        /// 好友QQ号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 点数
        /// </summary>
        public int dice { get; set; }
    }
    /// <summary>
    /// 97 [插件]发送群骰子
    /// </summary>
    public record SendGroupDicePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 点数
        /// </summary>
        public int dice { get; set; }
    }
    /// <summary>
    /// 98 [插件]发送群私聊骰子
    /// </summary>
    public record SendGroupPrivateDicePack : PackBase
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
        /// 点数
        /// </summary>
        public int dice { get; set; }
    }
    /// <summary>
    /// 99 [插件]上传群文件
    /// </summary>
    public record AddGroupFilePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 文件路径
        /// </summary>
        public string file { get; set; }
        /// <summary>
        /// 群文件名称
        /// </summary>
        public string name { get; set; }
    }
    /// <summary>
    /// 100 [插件]删除群文件
    /// </summary>
    public record DeleteGroupFilePack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 群文件名称
        /// </summary>
        public string name { get; set; }
    }
    /// <summary>
    /// 101 [插件]获取群文件
    /// </summary>
    public record GetGroupFilesPack : PackBase
    {
        /// <summary>
        /// 群号
        /// </summary>
        public long id { get; set; }
        /// <summary>
        /// 文件夹
        /// </summary>
        public string name { get; set; }
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
                            var temp = BuildPack.Build(new object(), 60);
                            AddTask(temp);
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
                        IsConnect = false;
                        RobotStateEvent.Invoke(StateType.Disconnect);
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
            if (!IsConnect)
                return;
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
