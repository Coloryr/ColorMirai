using Newtonsoft.Json;
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Text;
using System.Threading;
//请用net6运行
//并安装Newtonsoft.Json
namespace ColoryrSDK;

//////////////////////////////////////////////////////////
//机器人返回数据包
//////////////////////////////////////////////////////////
/// <summary>
/// 0 [插件]插件消息回调
/// </summary>
public record ReMessagePack : PackBase
{
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
    /// <summary>
    /// 消息内容
    /// </summary>
    public string msg { get; set; }
}
/// <summary>
/// 55 [插件]获取群列表
/// </summary>
public record ReListGroupPack : PackBase
{
    /// <summary>
    /// 群列表
    /// </summary>
    public List<GroupInfo> groups { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 56 [插件]获取好友列表
/// </summary>
public record ReListFriendPack : PackBase
{
    /// <summary>
    /// 朋友列表
    /// </summary>
    public List<ReFriendInfoPack> friends { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 57 [插件]获取群成员
/// </summary>
public record ReListMemberPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 成员列表
    /// </summary>
    public List<ReMemberInfoPack> members { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 58 [插件]获取群设置
/// </summary>
public record ReGroupSettingPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 设定
    /// </summary>
    public GroupSettings setting { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 90 [插件]获取图片Url
/// </summary>
public record ReGetImageUrlPack : PackBase
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
public record ReMemberInfoPack : PackBase
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
    /// <summary>
    /// 群等级头衔
    /// </summary>
    public string rankTitle { get; set; }
    /// <summary>
    /// 群活跃度相关属性
    /// </summary>
    public MemberActiveInfo active { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 92 [插件]获取朋友信息
/// </summary>
public record ReFriendInfoPack : PackBase
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
    /// <summary>
    /// 用户分组ID
    /// </summary>
    public int groupId { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 101 [插件]获取群文件
/// </summary>
public record ReGroupFilesPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 文件列表
    /// </summary>
    public List<GroupFileInfo> files { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 109 [插件]获取群公告
/// </summary>
public record ReGroupAnnouncementsPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 群公告
    /// </summary>
    public List<GroupAnnouncement> list { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 128 [插件]获取好友分组信息
/// </summary>
public record ReFriendGroupPack : PackBase
{
    /// <summary>
    /// 好友分组数据
    /// </summary>
    public FriendGroupInfo info { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 129 [插件]获取所有好友分组信息
/// </summary>
public record ReListFriendGroupPack : PackBase
{
    /// <summary>
    /// 好友分组列表
    /// </summary>
    public List<FriendGroupInfo> infos { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
//////////////////////////////////////////////////////////
//机器人枚举
//////////////////////////////////////////////////////////
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
    MEMBER = 0,
    ADMINISTRATOR,
    OWNER
}
/// <summary>
/// 消息来源类型
/// </summary>
public enum MessageSourceKind
{
    /// <summary>
    /// 群消息
    /// </summary>
    GROUP = 0,
    /// <summary>
    /// 好友消息
    /// </summary>
    FRIEND,
    /// <summary>
    /// 来自群成员的临时会话消息
    /// </summary>
    TEMP,
    /// <summary>
    /// 来自陌生人的消息
    /// </summary>
    STRANGER
}
/// <summary>
/// 群成员头衔详情Detail
/// </summary>
public enum MemberMedalType
{
    /// <summary>
    /// 群主独有的头衔
    /// </summary>
    OWNER = 300,
    /// <summary>
    /// 管理员独有的头衔
    /// </summary>
    ADMIN = 301,
    /// <summary>
    /// 群主授予的头衔
    /// </summary>
    SPECIAL = 302,
    /// <summary>
    /// 群主设定的头衔, 保持活跃即可获得
    /// </summary>
    ACTIVE = 315
}
/// <summary>
/// 群荣誉信息
/// </summary>
public enum GroupHonorType
{
    /// <summary>
    /// 龙王
    /// </summary>
    TALKATIVE = 1,
    /// <summary>
    /// 群聊之火
    /// </summary>
    PERFORMER = 2,
    /// <summary>
    /// 群聊炽焰
    /// </summary>
    LEGEND = 3,
    /// <summary>
    /// 冒尖小春笋
    /// </summary>
    STRONG_NEWBIE = 4,
    /// <summary>
    /// 快乐源泉
    /// </summary>
    EMOTION = 5,
    /// <summary>
    /// 学术新星
    /// </summary>
    BRONZE = 6,
    /// <summary>
    /// 顶尖学霸
    /// </summary>
    SILVER = 7,
    /// <summary>
    /// 至尊学神
    /// </summary>
    GOLDEN = 8,
    /// <summary>
    /// 一笔当先
    /// </summary>
    WHIRLWIND = 9,
    /// <summary>
    /// 壕礼皇冠
    /// </summary>
    RICHER = 10,
    /// <summary>
    /// 善财福禄寿
    /// </summary>
    RED_PACKET = 11
}
//////////////////////////////////////////////////////////
//机器人数据类型
//////////////////////////////////////////////////////////
/// <summary>
/// 群公告
/// </summary>
public record GroupAnnouncement
{
    /// <summary>
    /// 发送者ID
    /// </summary>
    public long senderId;
    /// <summary>
    /// 公告ID
    /// </summary>
    public string fid;
    /// <summary>
    /// 有人都已确认
    /// </summary>
    public bool allConfirmed;
    /// <summary>
    /// 为已经确认的成员数量
    /// </summary>
    public int confirmedMembersCount;
    /// <summary>
    /// 公告发出的时间
    /// </summary>
    public long publicationTime;
    /// <summary>
    /// 内容
    /// </summary>
    public string content;
    /// <summary>
    /// 图片
    /// </summary>
    public string image;
    /// <summary>
    /// 是否发送给新成员
    /// </summary>
    public bool sendToNewMember;
    /// <summary>
    /// 置顶
    /// </summary>
    public bool isPinned;
    /// <summary>
    /// 修改昵称
    /// </summary>
    public bool showEditCard;
    /// <summary>
    /// 使用弹窗
    /// </summary>
    public bool showPopup;
    /// <summary>
    /// 需要群成员确认
    /// </summary>
    public bool requireConfirmation;
}
/// <summary>
/// 群文件信息
/// </summary>
public record GroupFileInfo
{
    /// <summary>
    /// 文件名
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 文件ID
    /// </summary>
    public string id { get; set; }
    /// <summary>
    /// 文件路径
    /// </summary>
    public string absolutePath { get; set; }
    /// <summary>
    /// 是否是文件
    /// </summary>
    public bool isFile { get; set; }
    /// <summary>
    /// 是否是文件夹
    /// </summary>
    public bool isFolder { get; set; }
    /// <summary>
    /// 文件大小
    /// </summary>
    public long size { get; set; }
    /// <summary>
    /// 上传者QQ号
    /// </summary>
    public long uploaderId { get; set; }
    /// <summary>
    /// 上传时间
    /// </summary>
    public long uploadTime { get; set; }
    /// <summary>
    /// 上次修改时间
    /// </summary>
    public long lastModifyTime { get; set; }
    /// <summary>
    /// 文件到期时间
    /// </summary>
    public long expiryTime { get; set; }
    /// <summary>
    /// SHA1
    /// </summary>
    public string sha1 { get; set; }
    /// <summary>
    /// MD5
    /// </summary>
    public string md5 { get; set; }
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
    /// <summary>
    /// 个性签名
    /// </summary>
    public string sign { get; set; }
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
/// 好友分组数据
/// </summary>
public record FriendGroupInfo
{
    /// <summary>
    /// 好友分组 ID
    /// </summary>
    public int id { get; set; }
    /// <summary>
    /// 好友分组名
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 好友分组内好友数量
    /// </summary>
    public int count { get; set; }
    /// <summary>
    /// 属于本分组的好友集合
    /// </summary>
    public List<long> friends { get; set; }
}
/// <summary>
/// 群活跃度相关属性
/// </summary>
public record MemberActiveInfo
{
    /// <summary>
    /// 群活跃等级. 取值为 1~6 (包含)
    /// </summary>
    public int rank { get; set; }
    /// <summary>
    /// 群活跃积分
    /// </summary>
    public int point { get; set; }
    /// <summary>
    /// 群荣誉标识
    /// </summary>
    public List<GroupHonorType> honors { get; set; }
    /// <summary>
    /// 群荣誉等级. 取值为 1~100 (包含)
    /// </summary>
    public int temperature { get; set; }
    /// <summary>
    /// 当前佩戴的头衔
    /// </summary>
    public string title { get; set; }
    /// <summary>
    /// 当前佩戴的头衔的颜色
    /// </summary>
    public string color { get; set; }
    /// <summary>
    /// 当前佩戴的头衔类型
    /// </summary>
    public MemberMedalType wearing { get; set; }
    /// <summary>
    /// 拥有的所有头衔
    /// </summary>
    public List<MemberMedalType> medals { get; set; }
}
//////////////////////////////////////////////////////////
//机器人数据包
//////////////////////////////////////////////////////////
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
public record StartPack : PackBase
{
    /// <summary>
    /// 插件名字
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 监听的包
    /// </summary>
    public List<byte> reg { get; set; }
    /// <summary>
    /// 监听的群，可以为null
    /// </summary>
    public List<long> groups { get; set; }
    /// <summary>
    /// 监听的QQ号，可以为null
    /// </summary>
    public List<long> qqList { get; set; }
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
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 旧的权限
    /// </summary>
    public MemberPermission old { get; set; }
    /// <summary>
    /// 当前权限
    /// </summary>
    public MemberPermission now { get; set; }
}
/// <summary>
/// 4 [机器人]被邀请加入一个群（事件）
/// </summary>
public record BotInvitedJoinGroupRequestEventPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 邀请人QQ号
    /// </summary>
    public long fid { get; set; }
    /// <summary>
    /// 事件ID
    /// </summary>
    public long eventid { get; set; }
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
public record BotJoinGroupEventBPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
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
public record BotLeaveEventBPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
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
public record BotOfflineEventBPack : PackBase
{
    /// <summary>
    /// 离线原因
    /// </summary>
    public string message { get; set; }
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
    /// <summary>
    /// 昵称
    /// </summary>
    public string nick { get; set; }
}
/// <summary>
/// 19 [机器人]好友头像修改（事件）
/// </summary>
public record FriendAvatarChangedEventPack : PackBase
{
    /// <summary>
    /// 好友QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 图片url
    /// </summary>
    public string url { get; set; }
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
    /// 好友QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 是否成功发送
    /// </summary>
    public bool res { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
    /// <summary>
    /// 消息
    /// </summary>
    public List<string> message { get; set; }
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
    /// 好友QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 消息
    /// </summary>
    public List<string> message { get; set; }
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
public record GroupAllowConfessTalkEventPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
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
/// 26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）
/// </summary>
public record GroupAllowMemberInviteEventPack : PackBase
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
/// 27 [机器人]入群公告改变（事件）
/// </summary>
//public record GroupEntranceAnnouncementChangeEventPack : PackBase
//{
//    / <summary>
//    / 群号
//    / </summary>
//    public long id { get; set; }
//    / <summary>
//    / 执行人QQ号
//    / </summary>
//    public long fid { get; set; }
//    / <summary>
//    / 旧的状态
//    / </summary>
//    public bool old { get; set; }
//    / <summary>
//    / 新的状态
//    / </summary>
//    public bool now { get; set; }
//}
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
    /// 消息ID
    /// </summary>
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
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
    /// 目标ID
    /// </summary>
    public long fid { get; set; }
    /// <summary>
    /// 图片ID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 33 [机器人]图片上传失败（事件）
/// </summary>
public record ImageUploadEventBPack : PackBase
{
    /// <summary>
    /// 目标ID
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 错误码
    /// </summary>
    public int index { get; set; }
    /// <summary>
    /// 资源名
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 错误消息
    /// </summary>
    public string error { get; set; }
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
/// </summary>
public record InviteMemberJoinEventPack : PackBase
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
    /// 进群人昵称
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 邀请人QQ号
    /// </summary>
    public long ifid { get; set; }
    /// <summary>
    /// 邀请人昵称
    /// </summary>
    public string iname { get; set; }
}
/// <summary>
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
    /// <summary>
    /// 加入人昵称
    /// </summary>
    public string name { get; set; }
}
/// <summary>
/// 37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
/// </summary>
public record MemberJoinRequestEventPack : PackBase
{
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
    /// <summary>
    /// 事件ID
    /// </summary>
    public long eventid { get; set; }
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
    /// 好友QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 时间
    /// </summary>
    public int time { get; set; }
    /// <summary>
    /// 群员QQ号
    /// </summary>
    public long fid { get; set; }
    /// <summary>
    /// 撤回者
    /// </summary>
    public long oid { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] mid { get; set; }
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
    /// 消息ID
    /// </summary>
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
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
    /// 群名片
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 发送时间
    /// </summary>
    public int time { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
    /// <summary>
    /// 发送人权限
    /// </summary>
    public MemberPermission permission { get; set; }
    /// <summary>
    /// 发送的消息
    /// </summary>
    public List<string> message { get; set; }
}
/// <summary>
/// 50 [机器人]收到群临时会话消息（事件）
/// </summary>
public record TempMessageEventPack : PackBase
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
    /// 群名片
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
    /// <summary>
    /// 发送人权限
    /// </summary>
    public MemberPermission permission { get; set; }
    /// <summary>
    /// 发送的消息
    /// </summary>
    public List<string> message { get; set; }
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
    /// 昵称
    /// </summary>
    public string name { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
    /// <summary>
    /// 消息
    /// </summary>
    public List<string> message { get; set; }
    /// <summary>
    /// 时间
    /// </summary>
    public int time { get; set; }
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
    /// <summary>
    /// 群列表
    /// </summary>
    public HashSet<long> ids { get; set; }
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
    /// <summary>
    /// 成员QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
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
    /// <summary>
    /// QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 55 [插件]获取群列表
/// 56 [插件]获取好友列表
/// 129 [插件]获取所有好友分组信息
/// </summary>
public record GetPack : PackBase
{
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 57 [插件]获取群成员
/// </summary>
public record GroupGetMemberInfoPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 只获取关键数据
    /// </summary>
    public bool fast { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 58 [插件]获取群设置
/// </summary>
public record GroupGetSettingPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
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
    public List<string> arg { get; set; }
}
/// <summary>
/// 61 [插件]发送图片到群
/// </summary>
public record SendGroupImagePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 图片数据
    /// </summary>
    public byte[] data { get; set; }
    /// <summary>
    /// 群号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 62 [插件]发送图片到私聊
/// </summary>
public record SendGroupPrivateImagePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// QQ号
    /// </summary>
    public long fid { get; set; }
    /// <summary>
    /// 图片数据
    /// </summary>
    public byte[] data { get; set; }
    /// <summary>
    /// QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 63 [插件]发送图片到朋友
/// </summary>
public record SendFriendImagePack : PackBase
{
    /// <summary>
    /// QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 图片数据
    /// </summary>
    public byte[] data { get; set; }
    /// <summary>
    /// QQ号组
    /// </summary>
    public List<long> ids { get; set; }
}
/// <summary>
/// 64 [插件]删除群员
/// </summary>
public record GroupKickMemberPack : PackBase
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
    /// 黑名单
    /// </summary>
    public bool black { get; set; }
}
/// <summary>
/// 65 [插件]禁言群员
/// </summary>
public record GroupMuteMemberPack : PackBase
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
public record GroupUnmuteMemberPack : PackBase
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
public record GroupSetMemberCardPack : PackBase
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
public record GroupSetNamePack : PackBase
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
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
    /// <summary>
    /// 消息类型
    /// </summary>
    public MessageSourceKind kind { get; set; }
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
/// 74 [插件]发送语音到群
/// </summary>
public record SendGroupSoundPack : PackBase
{
    /// <summary>
    /// QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 文件内容
    /// </summary>
    public byte[] data { get; set; }
    /// <summary>
    /// QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 75 [插件]从本地文件加载图片发送到群
/// </summary>
public record SendGroupImageFilePack : PackBase
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
    /// 群号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 76 [插件]从本地文件加载图片发送到群私聊
/// </summary>
public record SendGroupPrivateImageFilePack : PackBase
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
    /// <summary>
    /// 群员QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 77 [插件]从本地文件加载图片发送到朋友
/// </summary>
public record SendFriendImageFilePack : PackBase
{
    /// <summary>
    /// QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 文件路径
    /// </summary>
    public string file { get; set; }
    /// <summary>
    /// QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 78 [插件]从本地文件加载语音发送到群
/// </summary>
public record SendGroupSoundFilePack : PackBase
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
    /// 群组
    /// </summary>
    public HashSet<long> ids { get; set; }
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
public record SendFriendNudgePack : PackBase
{
    /// <summary>
    /// 好友QQ号
    /// </summary>
    public long id { get; set; }
}
/// <summary>
/// 84 [插件]发送群成员戳一戳
/// </summary>
public record SendGroupMemberNudgePack : PackBase
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
/// 85 [机器人]龙王改变时（事件）
/// </summary>
public record GroupTalkativeChangePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 先前龙王
    /// </summary>
    public long old { get; set; }
    /// <summary>
    /// 当前龙王
    /// </summary>
    public long now { get; set; }
}
/// <summary>
/// 86 [机器人]其他客户端上线（事件）
/// </summary>
public record OtherClientOnlineEventPack : PackBase
{
    /// <summary>
    /// 设备Id
    /// </summary>
    public int appId { get; set; }
    /// <summary>
    /// 设备类型
    /// </summary>
    public string kind { get; set; }
    /// <summary>
    /// 设备平台
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
/// 87 [机器人]其他客户端离线（事件）
/// </summary>
public record OtherClientOfflineEventPack : PackBase
{
    /// <summary>
    /// 设备ID
    /// </summary>
    public int appId { get; set; }
    /// <summary>
    /// 设备平台
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
public record OtherClientMessageEventPack : PackBase
{
    /// <summary>
    /// 设备ID
    /// </summary>
    public int appId { get; set; }
    /// <summary>
    /// 设备平台
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
public record GetImageUrlPack : PackBase
{
    /// <summary>
    /// 图片UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 91 [插件]获取群成员信息
/// </summary>
public record GetMemberInfoPack : PackBase
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
    /// 只获取关键数据
    /// </summary>
    public bool fast { get; set; }
    /// <summary>
    /// 请求UUID
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
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 93 [插件]发送音乐分享
/// </summary>
public record SendMusicSharePack : PackBase
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
    /// <summary>
    /// 号码组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 94 [插件]设置群精华消息
/// </summary>
public record GroupSetEssenceMessagePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids1 { get; set; }
    /// <summary>
    /// 消息ID
    /// </summary>
    public int[] ids2 { get; set; }
}
/// <summary>
/// 95 [插件]消息队列
/// </summary>
public record MessageBuffPack : PackBase
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
    /// 发送对象类型
    /// </summary>
    public int type;
    /// <summary>
    /// 是否发送
    /// </summary>
    public bool send { get; set; }
    /// <summary>
    /// 消息内容
    /// </summary>
    public List<string> text { get; set; }
    /// <summary>
    /// 图片路径
    /// </summary>
    public string imgurl { get; set; }
    /// <summary>
    /// 图片数据
    /// </summary>
    public byte[] imgData { get; set; }
}

/// <summary>
/// 96 [插件]发送朋友骰子
/// </summary>
public record SendFriendDicePack : PackBase
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
public record GroupAddFilePack : PackBase
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
public record GroupDeleteFilePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 群文件ID
    /// </summary>
    public string fid { get; set; }
}
/// <summary>
/// 101 [插件]获取群文件
/// </summary>
public record GroupGetFilesPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 102 [插件]移动群文件
/// </summary>
public record GroupMoveFilePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 原群文件ID
    /// </summary>
    public string fid { get; set; }
    /// <summary>
    /// 新群文件路径
    /// </summary>
    public string dir { get; set; }
}
/// <summary>
/// 103 [插件]重命名群文件
/// </summary>
public record GroupRenameFilePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 旧群文件名
    /// </summary>
    public string fid { get; set; }
    /// <summary>
    /// 新群文件名
    /// </summary>
    public string now { get; set; }
}
/// <summary>
/// 104 [插件]创新群文件文件夹
/// </summary>
public record GroupAddDirPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 文件夹名字
    /// </summary>
    public string dir { get; set; }
}
/// <summary>
/// 105 [插件]删除群文件文件夹
/// </summary>
public record GroupDeleteDirPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 文件夹名字
    /// </summary>
    public string dir { get; set; }
}
/// <summary>
/// 106 [插件]重命名群文件文件夹
/// </summary>
public record GroupRenameDirPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
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
/// 107 [插件]下载群文件到指定位置
/// </summary>
public record GroupDownloadFilePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 文件ID
    /// </summary>
    public string fid { get; set; }
    /// <summary>
    /// 下载到的路径
    /// </summary>
    public string dir { get; set; }
}
/// <summary>
/// 108 [插件]设置取消管理员
/// </summary>
public record GroupSetAdminPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 群员
    /// </summary>
    public long fid { get; set; }
    /// <summary>
    /// 设置或取消
    /// </summary>
    public bool type { get; set; }
}
/// <summary>
/// 109 [插件]获取群公告
/// </summary>
public record GroupGetAnnouncementsPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 110 [插件]设置群公告
/// </summary>
public record GroupAddAnnouncementPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 图片路径
    /// </summary>
    public string imageFile { get; set; }
    /// <summary>
    /// 发送给新群员
    /// </summary>
    public bool sendToNewMember { get; set; }
    /// <summary>
    /// 顶置
    /// </summary>
    public bool isPinned { get; set; }
    /// <summary>
    /// 显示能够引导群成员修改昵称的窗口
    /// </summary>
    public bool showEditCard { get; set; }
    /// <summary>
    /// 使用弹窗
    /// </summary>
    public bool showPopup { get; set; }
    /// <summary>
    /// 需要群成员确认
    /// </summary>
    public bool requireConfirmation { get; set; }
    /// <summary>
    /// 公告内容
    /// </summary>
    public string text { get; set; }
}
/// <summary>
/// 111 [插件]删除群公告
/// </summary>
public record GroupDeleteAnnouncementPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 唯一识别属性
    /// </summary>
    public string fid { get; set; }
}
/// <summary>
/// 112 [插件]发送好友语言文件
/// </summary>
public record SendFriendSoundFilePack : PackBase
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
    /// QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 113 [机器人]群解散消息（事件）
/// </summary>
public record GroupDisbandPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
}
/// <summary>
/// 114 [插件]设置允许群员邀请好友入群的状态
/// </summary>
public record GroupSetAllowMemberInvitePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 是否允许
    /// </summary>
    public bool enable { get; set; }
}
/// <summary>
/// 115 [插件]设置允许匿名聊天
/// </summary>
public record GroupSetAnonymousChatEnabledPack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 是否允许
    /// </summary>
    public bool enable { get; set; }
}
/// <summary>
/// 116 [机器人]收到陌生人消息（事件）
/// </summary>
public record StrangerMessageEventPack : FriendMessageEventPack
{

}
/// <summary>
/// 117 [插件]发送陌生人消息
/// </summary>
public record SendStrangerMessagePack : SendFriendMessagePack
{

}
/// <summary>
/// 118 [插件]从本地文件加载图片发送到陌生人
/// </summary>
public record SendStrangerImageFilePack : SendFriendImageFilePack
{

}
/// <summary>
/// 119 [插件]发送陌生人骰子
/// </summary>
public record SendStrangerDicePack : SendFriendDicePack
{

}
/// <summary>
/// 120 [插件]发送陌生人戳一戳
/// </summary>
public record SendStrangerNudgePack : SendFriendNudgePack
{

}
/// <summary>
/// 121 [插件]从本地文件加载语音发送到陌生人
/// </summary>
public record SendStrangerSoundFilePack : SendFriendSoundFilePack
{

}
/// <summary>
/// 122 [机器人]在发送陌生人消息前广播（事件）
/// </summary>
public record StrangerMessagePreSendEventPack : FriendMessagePreSendEventPack
{

}
/// <summary>
/// 123 [机器人]在陌生人消息发送后广播（事件）
/// </summary>
public record StrangerMessagePostSendEventPack : FriendMessagePostSendEventPack
{

}
/// <summary>
/// 124 [机器人]陌生人关系改变->删除（事件）
/// 125 [机器人]陌生人关系改变->朋友（事件）
/// </summary>
public record StrangerRelationChangePack : PackBase
{
    /// <summary>
    /// QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 方式
    /// </summary>
    public int type { get; set; }
}
/// <summary>
/// 126 [插件]发送好友语音
/// </summary>
public record SendFriendSoundPack : PackBase
{
    /// <summary>
    /// QQ号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 语音内容
    /// </summary>
    public byte[] data { get; set; }
    /// <summary>
    /// QQ号组
    /// </summary>
    public HashSet<long> ids { get; set; }
}
/// <summary>
/// 128 [插件]获取好友分组信息
/// </summary>
public record GetFriendGroupPack : PackBase
{
    /// <summary>
    /// 分组ID
    /// </summary>
    public int id { get; set; }
    /// <summary>
    /// 请求UUID
    /// </summary>
    public string uuid { get; set; }
}
/// <summary>
/// 130 [插件]创建好友分组
/// </summary>
public record FriendGroupCreatePack : PackBase
{
    /// <summary>
    /// 分组名
    /// </summary>
    public string name { get; set; }
}
/// <summary>
/// 131 [插件]修改好友分组名 
/// </summary>
public record FriendGroupRenamePack : PackBase
{
    /// <summary>
    /// 好友组ID
    /// </summary>
    public int id { get; set; }
    /// <summary>
    /// 新的名字
    /// </summary>
    public string name { get; set; }
}
/// <summary>
/// 132 [插件]移动好友到分组
/// </summary>
public record FriendGroupMovePack : PackBase
{
    /// <summary>
    /// 好友分组ID
    /// </summary>
    public int id { get; set; }
    /// <summary>
    /// 好友QQ号
    /// </summary>
    public long fid { get; set; }
}
/// <summary>
/// 133 [插件]删除好友分组
/// </summary>
public record FriendGroupDeletePack : PackBase
{
    /// <summary>
    /// 好友分组ID
    /// </summary>
    public int id { get; set; }
}
/// <summary>
/// 134 [插件]修改群成员头衔
/// </summary>
public record GroupMemberEditSpecialTitlePack : PackBase
{
    /// <summary>
    /// 群号
    /// </summary>
    public long id { get; set; }
    /// <summary>
    /// 群成员
    /// </summary>
    public long fid { get; set; }
    /// <summary>
    /// 头衔
    /// </summary>
    public string name { get; set; }
}


public static class BuildPack
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
    public static byte[] BuildImage(long qq, long id, long fid, string img, byte index, HashSet<long> ids)
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
        if (ids != null)
        {
            string temp1 = "ids=";
            foreach (var item in ids)
            {
                temp1 += $"{item},";
            }
            temp1 = temp1[..^1];
            temp += temp1 + "&";
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
    public static byte[] BuildSound(long qq, long id, string sound, byte index, HashSet<long> ids)
    {
        string temp = $"id={id}&qq={qq}&sound={sound}";
        if (ids != null)
        {
            string temp1 = "ids=";
            foreach (var item in ids)
            {
                temp1 += $"{item},";
            }
            temp1 = temp1[..^1];
            temp += temp1 + "&";
        }
        byte[] data = Encoding.UTF8.GetBytes(temp + " ");
        data[^1] = index;
        return data;
    }
}

public record RobotConfig
{
    /// <summary>
    /// 机器人IP
    /// </summary>
    public string IP { get; set; }
    /// <summary>
    /// 机器人端口
    /// </summary>
    public int Port { get; set; }
    /// <summary>
    /// 监听的包
    /// </summary>
    public List<byte> Pack { get; set; }
    /// <summary>
    /// 插件名字
    /// </summary>
    public string Name { get; set; }
    /// <summary>
    /// 监听的群，可以为null
    /// </summary>
    public List<long> Groups { get; set; }
    /// <summary>
    /// 监听的qq号，可以为null
    /// </summary>
    public List<long> QQs { get; set; }
    /// <summary>
    /// 运行的qq，可以不设置
    /// </summary>
    public long RunQQ { get; set; }
    /// <summary>
    /// 重连时间
    /// </summary>
    public int Time { get; set; }
    /// <summary>
    /// 检测是否断开
    /// </summary>
    public bool Check { get; set; }
    /// <summary>
    /// 机器人事件回调函数
    /// </summary>
    public Action<int, object> CallAction { get; set; }
    /// <summary>
    /// 机器人日志回调函数
    /// </summary>
    public Action<LogType, string> LogAction { get; set; }
    /// <summary>
    /// 机器人状态回调函数
    /// </summary>
    public Action<StateType> StateAction { get; set; }
}

public enum LogType
{
    Log, Error
}
public enum StateType
{
    Disconnect, Connecting, Connect
}

public partial class RobotSDK
{
    public static readonly Dictionary<int, Type> PackType = new()
    {
        { 0, typeof(ReMessagePack)},
        { 1, typeof(BeforeImageUploadPack) },
        { 2, typeof(BotAvatarChangedPack) },
        { 3, typeof(BotGroupPermissionChangePack) },
        { 4, typeof(BotInvitedJoinGroupRequestEventPack) },
        { 5, typeof(BotJoinGroupEventAPack) },
        { 6, typeof(BotJoinGroupEventBPack) },
        { 7, typeof(BotLeaveEventAPack) },
        { 8, typeof(BotLeaveEventBPack) },
        { 9, typeof(BotMuteEventPack) },
        { 10, typeof(BotOfflineEventAPack) },
        { 11, typeof(BotOfflineEventBPack) },
        { 12, typeof(BotOfflineEventAPack) },
        { 13, typeof(BotOfflineEventAPack) },
        { 14, typeof(BotOfflineEventCPack) },
        { 15, typeof(BotOnlineEventPack) },
        { 16, typeof(BotReloginEventPack) },
        { 17, typeof(BotUnmuteEventPack) },
        { 18, typeof(FriendAddEventPack) },
        { 19, typeof(FriendAvatarChangedEventPack) },
        { 20, typeof(FriendDeleteEventPack) },
        { 21, typeof(FriendMessagePostSendEventPack) },
        { 22, typeof(FriendMessagePreSendEventPack) },
        { 23, typeof(FriendRemarkChangeEventPack) },
        { 24, typeof(GroupAllowAnonymousChatEventPack) },
        { 25, typeof(GroupAllowConfessTalkEventPack) },
        { 26, typeof(GroupAllowMemberInviteEventPack) },
        //{ 27, typeof(GroupEntranceAnnouncementChangeEventPack) },
        { 28, typeof(GroupMessagePostSendEventPack) },
        { 29, typeof(GroupMessagePreSendEventPack) },
        { 30, typeof(GroupMuteAllEventPack) },
        { 31, typeof(GroupNameChangeEventPack) },
        { 32, typeof(ImageUploadEventAPack) },
        { 33, typeof(ImageUploadEventBPack) },
        { 34, typeof(MemberCardChangeEventPack) },
        { 35, typeof(InviteMemberJoinEventPack) },
        { 36, typeof(MemberJoinEventPack) },
        { 37, typeof(MemberJoinRequestEventPack) },
        { 38, typeof(MemberLeaveEventAPack) },
        { 39, typeof(MemberLeaveEventBPack) },
        { 40, typeof(MemberMuteEventPack) },
        { 41, typeof(MemberPermissionChangeEventPack) },
        { 42, typeof(MemberSpecialTitleChangeEventPack) },
        { 43, typeof(MemberUnmuteEventPack) },
        { 44, typeof(MessageRecallEventAPack) },
        { 45, typeof(MessageRecallEventBPack) },
        { 46, typeof(NewFriendRequestEventPack) },
        { 47, typeof(TempMessagePostSendEventPack) },
        { 48, typeof(TempMessagePreSendEventPack) },
        { 49, typeof(GroupMessageEventPack) },
        { 50, typeof(TempMessageEventPack) },
        { 51, typeof(FriendMessageEventPack) },
        { 52, typeof(SendGroupMessagePack) },
        { 53, typeof(SendGroupPrivateMessagePack) },
        { 54, typeof(SendFriendMessagePack) },
        { 55, typeof(ReListGroupPack) },
        { 56, typeof(ReListFriendPack) },
        { 57, typeof(ReListMemberPack) },
        { 58, typeof(ReGroupSettingPack) },
        { 59, typeof(EventCallPack) },
        //60无
        { 61, typeof(SendGroupImagePack) },
        { 62, typeof(SendGroupPrivateImagePack)},
        { 63, typeof(SendFriendImagePack)},
        { 64, typeof(GroupKickMemberPack) },
        { 65, typeof(GroupMuteMemberPack) },
        { 66, typeof(GroupUnmuteMemberPack) },
        { 67, typeof(GroupMuteAllPack) },
        { 68, typeof(GroupUnmuteAllPack) },
        { 69, typeof(GroupSetMemberCardPack) },
        { 70, typeof(GroupSetNamePack) },
        { 71, typeof(ReCallMessagePack) },
        { 72, typeof(FriendInputStatusChangedEventPack) },
        { 73, typeof(FriendNickChangedEventPack) },
        { 74, typeof(SendGroupSoundPack) },
        { 75, typeof(SendGroupImageFilePack) },
        { 76, typeof(SendGroupPrivateImageFilePack) },
        { 77, typeof(SendFriendImageFilePack) },
        { 78, typeof(SendGroupSoundFilePack) },
        { 79, typeof(MemberJoinRetrieveEventPack) },
        { 80, typeof(BotJoinGroupEventRetrieveEventPack) },
        { 81, typeof(NudgedEventPack) },
        { 82, typeof(NudgedEventPack) },
        { 83, typeof(SendFriendNudgePack) },
        { 84, typeof(SendGroupMemberNudgePack) },
        { 85, typeof(GroupTalkativeChangePack) },
        { 86, typeof(OtherClientOnlineEventPack) },
        { 87, typeof(OtherClientOfflineEventPack) },
        { 88, typeof(OtherClientMessageEventPack) },
        { 89, typeof(GroupMessageSyncEventPack) },
        { 90, typeof(ReGetImageUrlPack) },
        { 91, typeof(ReMemberInfoPack) },
        { 92, typeof(ReFriendInfoPack) },
        { 93, typeof(SendMusicSharePack) },
        { 94, typeof(GroupSetEssenceMessagePack) },
        { 95, typeof(MessageBuffPack) },
        { 96, typeof(SendFriendDicePack) },
        { 97, typeof(SendGroupDicePack) },
        { 98, typeof(SendGroupPrivateDicePack) },
        { 99, typeof(GroupAddFilePack) },
        { 100, typeof(GroupDeleteFilePack) },
        { 101, typeof(ReGroupFilesPack) },
        { 102, typeof(GroupMoveFilePack) },
        { 103, typeof(GroupRenameFilePack) },
        { 104, typeof(GroupAddDirPack) },
        { 105, typeof(GroupDeleteDirPack) },
        { 106, typeof(GroupRenameDirPack) },
        { 107, typeof(GroupDownloadFilePack) },
        { 108, typeof(GroupSetAdminPack) },
        { 109, typeof(ReGroupAnnouncementsPack) },
        { 110, typeof(GroupAddAnnouncementPack) },
        { 111, typeof(GroupDeleteAnnouncementPack) },
        { 112, typeof(SendFriendSoundFilePack) },
        { 113, typeof(GroupDisbandPack) },
        { 114, typeof(GroupSetAllowMemberInvitePack) },
        { 115, typeof(GroupSetAnonymousChatEnabledPack) },
        { 116, typeof(StrangerMessageEventPack) },
        { 117, typeof(SendStrangerMessagePack) },
        { 118, typeof(SendStrangerImageFilePack) },
        { 119, typeof(SendStrangerDicePack) },
        { 120, typeof(SendStrangerNudgePack) },
        { 121, typeof(SendStrangerSoundFilePack) },
        { 122, typeof(StrangerMessagePreSendEventPack) },
        { 123, typeof(StrangerMessagePostSendEventPack) },
        { 124, typeof(StrangerRelationChangePack) },
        { 125, typeof(StrangerRelationChangePack) },
        { 126, typeof(SendFriendSoundPack) },
        { 128, typeof(ReFriendGroupPack)},
        { 129, typeof(ReListFriendGroupPack)},
        { 130, typeof(FriendGroupCreatePack)},
        { 131, typeof(FriendGroupRenamePack)},
        { 132, typeof(FriendGroupMovePack)},
        { 133, typeof(FriendGroupDeletePack)}
    };
}

public interface IColorMiraiPipe
{
    void AddSend(PackBase pack, int index);
    void ReConnect();
    void SendStop();
    void Stop();
    void StartRead();
}

public partial class RobotSDK
{
    private record RobotTask
    {
        public int Index { get; set; }
        public object Data { get; set; }
    }
    /// <summary>
    /// 机器人登录的QQ号列表
    /// </summary>
    public List<long> QQs { get; internal set; }
    /// <summary>
    /// 是否正在运行
    /// </summary>
    public bool IsRun { get; internal set; }
    /// <summary>
    /// 是否连接
    /// </summary>
    public bool IsConnect { get; internal set; }

    public RobotConfig Config { get; private set; }
    public StartPack PackStart { get; private set; }

    /// <summary>
    /// 第一次连接检查
    /// </summary>
    public bool IsFirst = true;
    /// <summary>
    /// 自动重连次数
    /// </summary>

    public int Times = 0;

    private partial bool CallTop(int index, object data);
    private Thread DoThread;
    private ConcurrentBag<RobotTask> QueueRead;

    private IColorMiraiPipe Pipe;

    /// <summary>
    /// 设置链接方式
    /// </summary>
    /// <param name="pipe"></param>
    public void SetPipe(IColorMiraiPipe pipe)
    {
        Pipe = pipe;
    }

    /// <summary>
    /// 设置配置
    /// </summary>
    /// <param name="Config">机器人配置</param>
    public void Set(RobotConfig Config)
    {
        this.Config = Config;

        PackStart = new()
        {
            name = Config.Name,
            reg = Config.Pack,
            groups = Config.Groups,
            qqList = Config.QQs,
            qq = Config.RunQQ
        };
    }
    /// <summary>
    /// 启动机器人
    /// </summary>
    public void Start()
    {
        if (DoThread?.IsAlive == true)
            return;
        QueueRead = new();
        DoThread = new(Read);
        IsRun = true;
        DoThread.Start();
        Pipe.StartRead();
    }

    private void Read()
    {
        while (IsRun)
        {
            try
            {
                if (QueueRead.TryTake(out RobotTask task))
                {
                    if (task.Index == 60)
                        continue;
                    if (CallTop(task.Index, task.Data))
                        continue;
                    if (PackType.TryGetValue(task.Index, out var type))
                    {
                        Config.CallAction(task.Index, Convert.ChangeType(task.Data, type));
                    }
                    else
                    {
                        LogError($"不认识的数据包{task.Index}");
                    }
                }
                Thread.Sleep(10);
            }
            catch (Exception e)
            {
                LogError(e);
            }
        }
    }

    /// <summary>
    /// 添加读取的包
    /// </summary>
    /// <param name="pack">解析后的信息</param>
    /// <param name="index">包ID</param>
    public void AddRead(object pack, int index)
    {
        QueueRead.Add(new()
        {
            Data = pack,
            Index = index
        });
    }

    /// <summary>
    /// 添加数据包
    /// </summary>
    /// <param name="data">数据包</param>
    /// <param name="index">包ID</param>
    internal void AddSend(PackBase pack, byte index)
    {
        Pipe.AddSend(pack, index);
    }

    /// <summary>
    /// 停止机器人
    /// </summary>
    public void Stop()
    {
        LogOut("机器人正在断开");
        IsRun = false;
        Pipe.Stop();
        LogOut("机器人已断开");
    }
    internal void LogError(Exception e)
    {
        Config.LogAction(LogType.Error, "机器人错误\n" + e.ToString());
    }
    internal void LogError(string a)
    {
        Config.LogAction(LogType.Error, "机器人错误:" + a);
    }
    internal void LogOut(string a)
    {
        Config.LogAction(LogType.Log, a);
    }
}
