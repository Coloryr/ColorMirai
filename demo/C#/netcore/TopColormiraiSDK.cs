using System;
using System.Collections.Generic;

namespace ColoryrSDK;

public partial class RobotSDK
{
    private enum CallType
    {
        GetFriends, GetMembers, GetGroupSetting, GetGroups, GetImageUrl,
        GetMemberInfo, GetFriendInfo, GetGroupFiles, GetGroupAnnouncements,
        GetFriendGroups, GetFriendGroup
    }
    private record QQGroup : QQCall
    {
        public QQGroup(CallType type) : base(type) { }
        public long Group { get; set; }
    }
    private record QQMember : QQCall
    {
        public QQMember(CallType type) : base(type) { }
        public long Group { get; set; }
        public long Member { get; set; }
    }
    private record QQFriend : QQCall
    {
        public QQFriend(CallType type) : base(type) { }
        public long Friend { get; set; }
    }
    private record QQCall
    {
        public QQCall(CallType type) { Type = type; }
        public long QQ { get; set; }
        public string UUID { get; set; }
        public CallType Type { get; set; }
    }
    private readonly Dictionary<string, QQCall> UUIDs = new();
    private readonly Dictionary<QQCall, Action<ReListFriendPack>> GetFriendsMap = new();
    private readonly Dictionary<QQGroup, Action<ReListMemberPack>> GetMembersMap = new();
    private readonly Dictionary<QQGroup, Action<ReGroupSettingPack>> GetGroupSettingMap = new();
    private readonly Dictionary<QQCall, Action<ReListGroupPack>> GetGroupsMap = new();
    private readonly Dictionary<string, Action<string>> GetImageUrlMap = new();
    private readonly Dictionary<QQMember, Action<ReMemberInfoPack>> GetMemberInfoMap = new();
    private readonly Dictionary<QQFriend, Action<ReFriendInfoPack>> GetFriendInfoMap = new();
    private readonly Dictionary<QQGroup, Action<ReGroupFilesPack>> GetGroupFilesMap = new();
    private readonly Dictionary<QQGroup, Action<ReGroupAnnouncementsPack>> GetGroupAnnouncementsMap = new();
    private readonly Dictionary<QQCall, Action<ReListFriendGroupPack>> GetFriendGroupsMap = new();
    private readonly Dictionary<QQFriend, Action<ReFriendGroupPack>> GetFriendGroupMap = new();
    private partial bool CallTop(int index, object data)
    {
        switch (index)
        {
            case 0:
                {
                    var pack = data as ReMessagePack;
                    if (!string.IsNullOrWhiteSpace(pack.uuid) 
                        && UUIDs.TryGetValue(pack.uuid, out var call))
                    {
                        //TODO 是否要产生回调
                        //switch (call.Type)
                        //{
                        //    case CallType.GetFriends:
                        //        if (GetFriendsMap.Remove(call, out var temp)) { temp(null); }
                        //        break;
                        //    case CallType.GetMembers:
                        //        if (GetMembersMap.Remove(call as QQGroup, out var temp1)) { temp1(null); }
                        //        break;
                        //    case CallType.GetGroupSetting:
                        //        if (GetGroupSettingMap.Remove(call as QQGroup, out var temp2)) { temp2(null); }
                        //        break;
                        //    case CallType.GetGroupSetting:
                        //        if (GetGroupSettingMap.Remove(call as QQGroup, out var temp2)) { temp2(null); }
                        //        break;
                        //}
                    }
                    return false;
                }
            case 55:
                {
                    var pack = data as ReListGroupPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) &&
                        GetGroupsMap.Remove(key, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 56:
                {
                    var pack = data as ReListFriendPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) &&
                        GetFriendsMap.Remove(key, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 57:
                {
                    var pack = data as ReListMemberPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetMembersMap.Remove(key as QQGroup, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 58:
                {
                    var pack = data as ReGroupSettingPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetGroupSettingMap.Remove(key as QQGroup, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 90:
                {
                    var pack = data as ReGetImageUrlPack;
                    if (GetImageUrlMap.Remove(pack.uuid, out var action))
                    {
                        action(pack.url);
                    }
                    return true;
                }
            case 91:
                {
                    var pack = data as ReMemberInfoPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetMemberInfoMap.Remove(key as QQMember, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 92:
                {
                    var pack = data as ReFriendInfoPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetFriendInfoMap.Remove(key as QQFriend, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 101:
                {
                    var pack = data as ReGroupFilesPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetGroupFilesMap.Remove(key as QQGroup, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 109:
                {
                    var pack = data as ReGroupAnnouncementsPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetGroupAnnouncementsMap.Remove(key as QQGroup, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 128:
                {
                    var pack = data as ReFriendGroupPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetFriendGroupMap.Remove(key as QQFriend, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            case 129:
                {
                    var pack = data as ReListFriendGroupPack;
                    if (UUIDs.TryGetValue(pack.uuid, out var key) && 
                        GetFriendGroupsMap.Remove(key, out var action))
                    {
                        action(pack);
                    }
                    return true;
                }
            default:
                return false;
        }
    }
    private string GenUUID
    {
        get
        {
            string uuid;
            do
            {
                uuid = Guid.NewGuid().ToString().Replace("-", "").ToLower();
            } while (UUIDs.ContainsKey(uuid));
            return uuid;
        }
    }
    /// <summary>
    /// 55 [插件]获取群列表
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="res">获取成功后回调</param>
    public void GetGroups(long qq, Action<ReListGroupPack> res)
    {
        var key = new QQCall(CallType.GetGroups) { QQ = qq, UUID = GenUUID };
        GetGroupsMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GetPack()
        {
            qq = qq,
            uuid = key.UUID
        }, 55);
    }
    /// <summary>
    /// 56 [插件]获取好友列表
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="res">获取成功后回调</param>
    public void GetFriends(long qq, Action<ReListFriendPack> res)
    {
        var key = new QQCall(CallType.GetFriends) { QQ = qq, UUID = GenUUID };
        GetFriendsMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GetPack()
        {
            qq = qq,
            uuid = key.UUID
        }, 56);
    }
    /// <summary>
    /// 57 [插件]获取群成员
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="fast">只获取关键信息</param>
    /// <param name="res">获取成功后回调</param>
    public void GetMembers(long qq, long group, bool fast, Action<ReListMemberPack> res)
    {
        var key = new QQGroup(CallType.GetMembers) { QQ = qq, Group = group, UUID = GenUUID };
        GetMembersMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GroupGetMemberInfoPack()
        {
            qq = qq,
            id = group,
            fast = fast,
            uuid = key.UUID
        }, 57);
    }
    /// <summary>
    /// 58 [插件]获取群设置
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="res">获取成功后回调</param>
    public void GetGroupSetting(long qq, long group, Action<ReGroupSettingPack> res)
    {
        var key = new QQGroup(CallType.GetGroupSetting) { QQ = qq, Group = group, UUID = GenUUID };
        GetGroupSettingMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GroupGetSettingPack()
        {
            qq = qq,
            id = group,
            uuid = key.UUID
        }, 58);
    }
    /// <summary>
    /// 54 [插件]发送好友消息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="friend">好友QQ号</param>
    /// <param name="message">消息</param>
    public void SendFriendMessage(long qq, long friend, List<string> message, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendFriendMessagePack()
        {
            qq = qq,
            id = friend,
            message = message,
            ids = ids
        }, 54);
    }
    /// <summary>
    /// 52 [插件]发送群消息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="message">消息</param>
    public void SendGroupMessage(long qq, long group, List<string> message, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendGroupMessagePack()
        {
            qq = qq,
            id = group,
            message = message,
            ids = ids
        }, 52);
    }

    /// <summary>
    /// 53 [插件]发送私聊消息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    /// <param name="message">消息</param>
    public void SendGroupTempMessage(long qq, long group, long member, List<string> message, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendGroupPrivateMessagePack()
        {
            qq = qq,
            id = group,
            fid = member,
            message = message,
            ids = ids
        }, 53);
    }

    /// <summary>
    /// 59 [插件]回应事件
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="eventid">事件ID</param>
    /// <param name="dofun">操作方式</param>
    /// <param name="arg">附加参数</param>
    public void EventCall(long qq, long eventid, int dofun, List<string> arg)
    {
        AddSend(new EventCallPack()
        {
            qq = qq,
            eventid = eventid,
            dofun = dofun,
            arg = arg
        }, 59);
    }

    public enum GroupCallType
    {
        /// <summary>
        /// 同意
        /// </summary>
        accept = 0,
        /// <summary>
        /// 拒绝
        /// </summary>
        reject,
        /// <summary>
        /// 不理睬
        /// </summary>
        ignore
    }

    public enum FriendCallType
    {
        /// <summary>
        /// 同意
        /// </summary>
        accept = 0,
        /// <summary>
        /// 拒绝
        /// </summary>
        reject
    }

    /// <summary>
    /// 同意邀请加群
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="eventid">事件ID</param>
    /// <param name="accept">是否同意</param>
    public void BotInvitedJoinGroupRequestCall(long qq, long eventid, bool accept)
    {
        EventCall(qq, eventid, accept ? 0 : 1, null);
    }

    /// <summary>
    /// 同意成员入群
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="eventid">事件ID</param>
    /// <param name="type">操作类型</param>
    /// <param name="blackList">是否加入黑名单</param>
    /// <param name="message">拒绝理由</param>
    public void MemberJoinRequestCall(long qq, long eventid, GroupCallType type, bool blackList = false, string message = "")
    {
        EventCall(qq, eventid, (int)type, new() { blackList.ToString().ToLower(), message });
    }

    /// <summary>
    /// 同意新好友申请
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="eventid">事件ID</param>
    /// <param name="type">操作类型</param>
    /// <param name="blackList">是否加入黑名单</param>
    public void NewFriendRequestCall(long qq, long eventid, FriendCallType type, bool blackList = false)
    {
        EventCall(qq, eventid, (int)type, new() { blackList.ToString().ToLower() });
    }

    /// <summary>
    /// 64 [插件]删除群员
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    public void GroupDeleteMember(long qq, long group, long member, bool black)
    {
        AddSend(new GroupKickMemberPack()
        {
            qq = qq,
            id = group,
            fid = member,
            black = black
        }, 64);
    }

    /// <summary>
    /// 65 [插件]禁言群员
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    /// <param name="time">禁言时间</param>
    public void GroupMuteMember(long qq, long group, long member, int time)
    {
        AddSend(new GroupMuteMemberPack()
        {
            qq = qq,
            id = group,
            fid = member,
            time = time
        }, 65);
    }

    /// <summary>
    /// 66 [插件]解除禁言
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    public void GroupUnmuteMember(long qq, long group, long member)
    {
        AddSend(new GroupUnmuteMemberPack()
        {
            qq = qq,
            id = group,
            fid = member
        }, 66);
    }

    /// <summary>
    /// 67 [插件]开启全员禁言
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    public void GroupMuteAll(long qq, long group)
    {
        AddSend(new GroupMuteAllPack()
        {
            qq = qq,
            id = group
        }, 67);
    }

    /// <summary>
    /// 68 [插件]关闭全员禁言
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    public void GroupUnmuteAll(long qq, long group)
    {
        AddSend(new GroupUnmuteAllPack()
        {
            qq = qq,
            id = group
        }, 68);
    }

    /// <summary>
    /// 69 [插件]设置群名片
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    /// <param name="card">群名片</param>
    public void GroupSetMember(long qq, long group, long member, string card)
    {
        AddSend(new GroupSetMemberCardPack()
        {
            qq = qq,
            id = group,
            fid = member,
            card = card
        }, 69);
    }

    /// <summary>
    /// 70 [插件]设置群名
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="name">群名字</param>
    public void GroupSetName(long qq, long group, string name)
    {
        AddSend(new GroupSetNamePack()
        {
            qq = qq,
            id = group,
            name = name
        }, 70);
    }

    /// <summary>
    /// 71 [插件]撤回消息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="ids1">消息ID</param>
    /// <param name="ids2">消息ID</param>
    /// <param name="kind">消息类型</param>
    public void ReCallMessage(long qq, int[] ids1, int[] ids2, MessageSourceKind kind)
    {
        AddSend(new ReCallMessagePack()
        {
            qq = qq,
            ids1 = ids1,
            ids2 = ids2,
            kind = kind
        }, 71);
    }

    /// <summary>
    /// 75 [插件]从本地文件加载图片发送到群
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="file">文件位置</param>
    public void SendGroupImageFile(long qq, long group, string file, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendGroupImageFilePack()
        {
            qq = qq,
            id = group,
            file = file,
            ids = ids
        }, 75);
    }

    /// <summary>
    /// 76 [插件]从本地文件加载图片发送到群私聊
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    /// <param name="file">文件位置</param>
    public void SendGroupPrivateImageFile(long qq, long group, long member, string file)
    {
        AddSend(new SendGroupPrivateImageFilePack()
        {
            qq = qq,
            id = group,
            fid = member,
            file = file
        }, 76);
    }

    /// <summary>
    /// 77 [插件]从本地文件加载图片发送到朋友
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="friend">好友QQ号</param>
    /// <param name="file">文件位置</param>
    public void SendFriendImageFile(long qq, long friend, string file, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendFriendImageFilePack()
        {
            qq = qq,
            id = friend,
            file = file,
            ids = ids
        }, 77);
    }

    /// <summary>
    /// 78 [插件]从本地文件加载语音发送到群
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="file">文件位置</param>
    public void SendGroupSoundFile(long qq, long group, string file, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendGroupSoundFilePack()
        {
            qq = qq,
            id = group,
            file = file,
            ids = ids
        }, 78);
    }

    /// <summary>
    /// 83 [插件]发送朋友戳一戳
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="friend">好友QQ号</param>
    public void SendFriendNudge(long qq, long friend)
    {
        AddSend(new SendFriendNudgePack()
        {
            qq = qq,
            id = friend
        }, 83);
    }

    /// <summary>
    /// 84 [插件]发送群成员戳一戳
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    public void SendGroupMemberNudge(long qq, long group, long member)
    {
        AddSend(new SendGroupMemberNudgePack()
        {
            qq = qq,
            id = group,
            fid = member
        }, 84);
    }

    /// <summary>
    /// 90 [插件]获取图片Url
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="uuid">图片UUID</param>
    /// <param name="res">返回回调</param>
    public void GetImageUrls(long qq, string uuid, Action<string> res)
    {
        GetImageUrlMap.Add(uuid, res);
        AddSend(new GetImageUrlPack()
        {
            qq = qq,
            uuid = uuid
        }, 90);
    }
    /// <summary>
    /// 91 [插件]获取群成员信息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群员</param>
    /// <param name="res">返回回调</param>
    public void GetMemberInfo(long qq, long group, long member, Action<ReMemberInfoPack> res)
    {
        var key = new QQMember(CallType.GetMemberInfo) { QQ = qq, Group = group, Member = member, UUID = GenUUID };
        GetMemberInfoMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GetMemberInfoPack()
        {
            qq = qq,
            id = group,
            fid = member,
            uuid = key.UUID
        }, 91);
    }
    /// <summary>
    /// 92 [插件]获取朋友信息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="friend">群号</param>
    /// <param name="res">返回回调</param>
    public void GetFriendInfo(long qq, long friend, Action<ReFriendInfoPack> res)
    {
        var key = new QQFriend(CallType.GetFriendInfo) { QQ = qq, Friend = friend, UUID = GenUUID };
        GetFriendInfoMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GetFriendInfoPack()
        {
            qq = qq,
            id = friend,
            uuid = key.UUID
        }, 92);
    }

    public enum MusicKind
    {
        NeteaseCloudMusic = 1,
        QQMusic, MiguMusic, KugouMusic, KuwoMusic
    }

    public enum SendToType
    {
        Friend, Group, Member
    }

    /// <summary>
    /// 93 [插件]发送音乐分享
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">发送目标</param>
    /// <param name="fid">发送目标</param>
    /// <param name="kind">音乐类型</param>
    /// <param name="type">目标类型</param>
    /// <param name="title">标题</param>
    /// <param name="summary">概要</param>
    /// <param name="jumpUrl">跳转Url</param>
    /// <param name="pictureUrl">图片Url</param>
    /// <param name="musicUrl">音乐Url</param>
    public void SendMusicShare(long qq, long id, long fid, MusicKind kind,
        SendToType type, string title, string summary, string jumpUrl,
        string pictureUrl, string musicUrl, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendMusicSharePack()
        {
            qq = qq,
            id = id,
            fid = fid,
            type = (int)kind,
            type1 = (int)type,
            title = title,
            summary = summary,
            jumpUrl = jumpUrl,
            pictureUrl = pictureUrl,
            musicUrl = musicUrl,
            ids = ids
        }, 93);
    }

    /// <summary>
    /// 94 [插件]设置群精华消息
    /// </summary>
    /// <param name="pack">消息事件包</param>
    public void GroupSetEssenceMessage(GroupMessageEventPack pack)
    {
        GroupSetEssenceMessage(pack.qq, pack.id, pack.ids1, pack.ids2);
    }

    /// <summary>
    /// 94 [插件]设置群精华消息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="ids1">消息ID</param>
    /// <param name="ids2">消息ID</param>
    public void GroupSetEssenceMessage(long qq, long group, int[] ids1, int[] ids2)
    {
        AddSend(new GroupSetEssenceMessagePack()
        {
            qq = qq,
            id = group,
            ids1 = ids1,
            ids2 = ids2
        }, 94);
    }

    /// <summary>
    /// 95 [插件]消息队列
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">发送目标</param>
    /// <param name="fid">发送目标</param>
    /// <param name="type">发送目标类型</param>
    /// <param name="file">图片文件位置</param>
    /// <param name="message">消息内容</param>
    /// <param name="send">是否发送</param>
    public void MessageBuff(long qq, long id, long fid, SendToType type,
        string file, List<string> message, bool send, byte[] img = null)
    {
        AddSend(new MessageBuffPack()
        {
            qq = qq,
            id = id,
            fid = fid,
            type = (int)type,
            imgurl = file,
            text = message,
            send = send,
            imgData = img
        }, 95);
    }

    /// <summary>
    /// 96 [插件]发送朋友骰子
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="friend">好友QQ号</param>
    /// <param name="dice">点数</param>
    public void SendFriendDice(long qq, long friend, int dice)
    {
        AddSend(new SendFriendDicePack()
        {
            qq = qq,
            id = friend,
            dice = dice
        }, 96);
    }

    /// <summary>
    /// 97 [插件]发送群骰子
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="dice">点数</param>
    public void SendGroupDice(long qq, long group, int dice)
    {
        AddSend(new SendGroupDicePack()
        {
            qq = qq,
            id = group,
            dice = dice
        }, 97);
    }

    /// <summary>
    /// 98 [插件]发送群私聊骰子
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群成员</param>
    /// <param name="dice">点数</param>
    public void SendGroupPrivateDice(long qq, long group, long member, int dice)
    {
        AddSend(new SendGroupPrivateDicePack()
        {
            qq = qq,
            id = group,
            fid = member,
            dice = dice
        }, 98);
    }

    /// <summary>
    /// 99 [插件]上传群文件
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="file">文件路径</param>
    /// <param name="name">群文件名称</param>
    public void GroupAddFilePack(long qq, long group, string file, string name)
    {
        AddSend(new GroupAddFilePack()
        {
            qq = qq,
            id = group,
            file = file,
            name = name
        }, 99);
    }

    /// <summary>
    /// 100 [插件]删除群文件
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="fid">群文件ID</param>
    public void GroupDeleteFile(long qq, long group, string fid)
    {
        AddSend(new GroupDeleteFilePack()
        {
            qq = qq,
            id = group,
            fid = fid
        }, 100);
    }

    /// <summary>
    /// 101 [插件]获取群文件
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="res">回调</param>
    public void GroupGetFiles(long qq, long group, Action<ReGroupFilesPack> res)
    {
        var key = new QQGroup(CallType.GetGroupFiles) { QQ = qq, Group = group, UUID = GenUUID };
        GetGroupFilesMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GroupGetFilesPack()
        {
            qq = qq,
            id = group,
            uuid = key.UUID
        }, 101);
    }

    /// <summary>
    /// 102 [插件]移动群文件
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="fid">文件ID</param>
    /// <param name="dir">新的路径</param>
    public void GroupMoveFile(long qq, long group, string fid, string dir)
    {
        AddSend(new GroupMoveFilePack()
        {
            qq = qq,
            id = group,
            fid = fid,
            dir = dir
        }, 102);
    }

    /// <summary>
    /// 103 [插件]重命名群文件
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="fid">文件ID</param>
    /// <param name="name">新文件名</param>
    public void GroupRemoveFile(long qq, long group, string fid, string name)
    {
        AddSend(new GroupRenameFilePack()
        {
            qq = qq,
            id = group,
            fid = fid,
            now = name
        }, 103);
    }

    /// <summary>
    /// 104 [插件]创新群文件文件夹
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="dir">文件夹名字</param>
    public void GroupAddDir(long qq, long group, string dir)
    {
        AddSend(new GroupAddDirPack()
        {
            qq = qq,
            id = group,
            dir = dir
        }, 104);
    }

    /// <summary>
    /// 105 [插件]删除群文件文件夹
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="dir">文件夹名字</param>
    public void GroupRemoveDir(long qq, long group, string dir)
    {
        AddSend(new GroupDeleteDirPack()
        {
            qq = qq,
            id = group,
            dir = dir
        }, 105);
    }
    /// <summary>
    /// 106 [插件]重命名群文件文件夹
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="old">旧的名字</param>
    /// <param name="now">新的名字</param>
    public void GroupRenameDir(long qq, long group, string old, string now)
    {
        AddSend(new GroupRenameDirPack()
        {
            qq = qq,
            id = group,
            old = old,
            now = now
        }, 106);
    }

    /// <summary>
    /// 107 [插件]下载群文件到指定位置
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="fid">文件ID</param>
    /// <param name="file">下载到的位置</param>
    public void GroupDownloadFile(long qq, long group, string fid, string file)
    {
        AddSend(new GroupDownloadFilePack()
        {
            qq = qq,
            id = group,
            fid = fid,
            dir = file
        }, 107);
    }

    /// <summary>
    /// 108 [插件]设置取消管理员
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="member">群成员QQ号</param>
    /// <param name="set">是否设置</param>
    public void GroupSetAdmin(long qq, long group, long member, bool set)
    {
        AddSend(new GroupSetAdminPack()
        {
            qq = qq,
            id = group,
            fid = member,
            type = set
        }, 108);
    }

    /// <summary>
    /// 109 [插件]获取群公告
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="res">回调</param>
    public void GroupGetAnnouncements(long qq, long group, Action<ReGroupAnnouncementsPack> res)
    {
        var key = new QQGroup(CallType.GetGroupAnnouncements) { QQ = qq, Group = group, UUID = GenUUID };
        GetGroupAnnouncementsMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GroupGetAnnouncementsPack()
        {
            qq = qq,
            id = group,
            uuid = key.UUID
        }, 109);
    }

    /// <summary>
    /// 110 [插件]设置群公告
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="imageFile">图片路径</param>
    /// <param name="sendToNewMember">发送给新群员</param>
    /// <param name="isPinned">顶置</param>
    /// <param name="showEditCard">显示能够引导群成员修改昵称的窗口</param>
    /// <param name="showPopup">使用弹窗</param>
    /// <param name="requireConfirmation">需要群成员确认</param>
    /// <param name="text">公告内容</param>
    public void GroupAddAnnouncement(long qq, long group, string imageFile, string text,
        bool sendToNewMember = false, bool isPinned = false, bool showEditCard = false,
        bool showPopup = false, bool requireConfirmation = false)
    {
        AddSend(new GroupAddAnnouncementPack()
        {
            qq = qq,
            id = group,
            imageFile = imageFile,
            sendToNewMember = sendToNewMember,
            isPinned = isPinned,
            showEditCard = showEditCard,
            showPopup = showPopup,
            requireConfirmation = requireConfirmation,
            text = text
        }, 110);
    }

    /// <summary>
    /// 111 [插件]删除群公告
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="group">群号</param>
    /// <param name="fid">公告ID</param>
    public void GroupRemoveAnnouncement(long qq, long group, string fid)
    {
        AddSend(new GroupDeleteAnnouncementPack()
        {
            qq = qq,
            id = group,
            fid = fid
        }, 111);
    }

    /// <summary>
    /// 112 [插件]发送好友语言文件
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="friend">好友QQ号</param>
    /// <param name="file">文件路径</param>
    public void SendFriendSoundFile(long qq, long friend, string file, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendFriendSoundFilePack()
        {
            qq = qq,
            id = friend,
            file = file,
            ids = ids
        }, 112);
    }

    /// <summary>
    /// 117 [插件]发送陌生人消息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="stranger">陌生人QQ号</param>
    /// <param name="message">消息</param>
    public void SendStrangerMessage(long qq, long stranger, List<string> message, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendStrangerMessagePack()
        {
            qq = qq,
            id = stranger,
            message = message,
            ids = ids
        }, 117);
    }

    /// <summary>
    /// 118 [插件]从本地文件加载图片发送到陌生人
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="stranger">陌生人QQ号</param>
    /// <param name="file">文件路径</param>
    /// <param name="ids">陌生人QQ号组</param>
    public void SendStrangerImageFile(long qq, long stranger, string file, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendStrangerImageFilePack()
        {
            qq = qq,
            id = stranger,
            file = file,
            ids = ids
        }, 118);
    }

    /// <summary>
    /// 119 [插件]发送陌生人骰子
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="stranger">陌生人QQ号</param>
    /// <param name="dice">点数</param>
    public void SendStrangerDice(long qq, long stranger, int dice)
    {
        AddSend(new SendStrangerDicePack()
        {
            qq = qq,
            id = stranger,
            dice = dice
        }, 119);
    }

    /// <summary>
    /// 120 [插件]发送陌生人戳一戳
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="stranger">陌生人QQ号</param>
    public void SendStrangerNudge(long qq, long stranger)
    {
        AddSend(new SendStrangerNudgePack()
        {
            qq = qq,
            id = stranger
        }, 120);
    }

    /// <summary>
    /// 121 [插件]从本地文件加载语音发送到陌生人
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="stranger">陌生人QQ号</param>
    /// <param name="file">文件路径</param>
    /// <param name="ids">陌生人QQ号组</param>
    public void SendStrangerSoundFile(long qq, long stranger, string file, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendStrangerSoundFilePack()
        {
            qq = qq,
            id = stranger,
            file = file,
            ids = ids
        }, 121);
    }

    /// <summary>
    /// 126 [插件]发送好友语音
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">QQ号</param>
    /// <param name="data">语音内容</param>
    /// <param name="ids">QQ号组</param>
    public void SendFriendSound(long qq, long id, byte[] data, HashSet<long> ids = null)
    {
        ids ??= new();
        AddSend(new SendFriendSoundPack()
        {
            qq = qq,
            id = id,
            data = data,
            ids = ids
        }, 126);
    }

    /// <summary>
    /// 128 [插件]获取好友分组信息
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">分组ID</param>
    public void GetFriendGroup(long qq, int id, Action<ReFriendGroupPack> res)
    {
        var key = new QQFriend(CallType.GetFriendGroup) { QQ = qq, Friend = id, UUID = GenUUID };
        GetFriendGroupMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GetFriendGroupPack()
        {
            qq = qq,
            id = id,
            uuid = key.UUID
        }, 128);
    }

    /// <summary>
    /// 129 [插件]获取所有好友分组信息
    /// </summary>
    /// <param name="qq">qq号</param>
    public void GetFriendGroups(long qq, Action<ReListFriendGroupPack> res)
    {
        var key = new QQCall(CallType.GetFriendGroups) { QQ = qq, UUID = GenUUID };
        GetFriendGroupsMap.Add(key, res);
        UUIDs.Add(key.UUID, key);
        AddSend(new GetPack()
        {
            qq = qq,
            uuid = key.UUID
        }, 129);
    }

    /// <summary>
    /// 130 [插件]创建好友分组
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="name">分组名</param>
    public void FriendGroupCreate(long qq, string name)
    {
        AddSend(new FriendGroupCreatePack()
        {
            qq = qq,
            name = name
        }, 130);
    }

    /// <summary>
    /// 131 [插件]修改好友分组名
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">分组id</param>
    /// <param name="name">新的分组名</param>
    public void FriendGroupRename(long qq, int id, string name)
    {
        AddSend(new FriendGroupRenamePack()
        {
            qq = qq,
            id = id,
            name = name,
        }, 131);
    }

    /// <summary>
    /// 132 [插件]移动好友到分组
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">分组id</param>
    /// <param name="fid">好友qq号</param>
    public void FriendGroupMove(long qq, int id, long fid)
    {
        AddSend(new FriendGroupMovePack()
        {
            qq = qq,
            id = id,
            fid = fid
        }, 132);
    }

    /// <summary>
    /// 133 [插件]删除好友分组
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">分组id</param>
    public void FriendGroupDelete(long qq, int id)
    {
        AddSend(new FriendGroupDeletePack()
        {
            qq = qq,
            id = id
        }, 133);
    }

    /// <summary>
    /// 134 [插件]修改群成员头衔
    /// </summary>
    /// <param name="qq">qq号</param>
    /// <param name="id">群号</param>
    /// <param name="fid">群员号</param>
    /// <param name="name">头衔</param>
    public void GroupMemberSetSpecialTitle(long qq, long id, long fid, string name) 
    {
        AddSend(new GroupMemberEditSpecialTitlePack()
        {
            qq = qq,
            id = id,
            fid = fid,
            name = name
        }, 134);
    }

    /// <summary>
    /// 构建一个回复指令
    /// </summary>
    /// <param name="pack">群消息数据包</param>
    /// <returns>指令</returns>
    public static string BuildQuoteReply(GroupMessageEventPack pack)
    {
        return BuildQuoteReply(pack.ids1, pack.ids2);
    }

    /// <summary>
    /// 构建一个回复指令
    /// </summary>
    /// <param name="pack">朋友消息数据包</param>
    /// <returns>指令</returns>
    public static string BuildQuoteReply(FriendMessageEventPack pack)
    {
        return BuildQuoteReply(pack.ids1, pack.ids2);
    }

    public static string BuildQuoteReply(int[] ids1, int[] ids2)
    {
        string temp = $"quote:";
        temp += $"{ids1.Length},";
        foreach (var item in ids1)
        {
            temp += $"{item},";
        }
        temp += $"{ids2.Length},";
        foreach (var item in ids2)
        {
            temp += $"{item},";
        }
        return temp;
    }
}
