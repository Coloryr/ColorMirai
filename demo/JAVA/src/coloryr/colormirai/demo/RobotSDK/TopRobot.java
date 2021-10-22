package coloryr.colormirai.demo.RobotSDK;

import coloryr.colormirai.demo.RobotSDK.api.*;
import coloryr.colormirai.demo.RobotSDK.enums.*;
import coloryr.colormirai.demo.RobotSDK.pack.from.*;
import coloryr.colormirai.demo.RobotSDK.pack.re.*;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class QQGroup {
    public long qq;
    public long group;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQGroup) {
            QQGroup group = (QQGroup) obj;
            return group.group == this.group &&
                    group.qq == this.qq;
        }
        return false;
    }
}

class QQMember {
    public long qq;
    public long group;
    public long member;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQMember) {
            QQMember member = (QQMember) obj;
            return member.group == this.group &&
                    member.member == this.member &&
                    member.qq == this.qq;
        }
        return false;
    }
}

class QQFriend {
    public long qq;
    public long friend;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof QQFriend) {
            QQFriend friend = (QQFriend) obj;
            return friend.friend == this.friend &&
                    friend.qq == this.qq;
        }
        return false;
    }
}

public class TopRobot extends BaseRobot {
    private final Map<Long, IListFriend> GetFriendsMap = new HashMap<>();
    private final Map<QQGroup, IListMember> GetMembersMap = new HashMap<>();
    private final Map<QQGroup, IGroupSetting> GetGroupSettingMap = new HashMap<>();
    private final Map<Long, IListGroup> GetGroupsMap = new HashMap<>();
    private final Map<String, IImageUrls> GetImageUrlMap = new HashMap<>();
    private final Map<QQMember, IMemberInfo> GetMemberInfoMap = new HashMap<>();
    private final Map<QQFriend, IFriendInfo> GetFriendInfoMap = new HashMap<>();
    private final Map<QQGroup, IGroupFiles> GetGroupFilesMap = new HashMap<>();
    private final Map<QQGroup, IGroupAnnouncements> GetGroupAnnouncementsMap = new HashMap<>();

    @Override
    protected boolean CallTop(byte index, String data) {
        switch (index) {
            case 55: {
                ListGroupPack pack = JSON.parseObject(data, ListGroupPack.class);
                if (GetGroupsMap.containsKey(pack.qq)) {
                    IListGroup action = GetGroupsMap.remove(pack.qq);
                    action.res(pack);
                }
                return true;
            }
            case 56: {
                ListFriendPack pack = JSON.parseObject(data, ListFriendPack.class);
                if (GetFriendsMap.containsKey(pack.qq)) {
                    IListFriend action = GetFriendsMap.remove(pack.qq);
                    action.res(pack);
                }
                return true;
            }
            case 57: {
                ListMemberPack pack = JSON.parseObject(data, ListMemberPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetMembersMap.containsKey(key)) {
                    IListMember action = GetMembersMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 58: {
                GroupSettingPack pack = JSON.parseObject(data, GroupSettingPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetGroupSettingMap.containsKey(key)) {
                    IGroupSetting action = GetGroupSettingMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 90: {
                ReImagePack pack = JSON.parseObject(data, ReImagePack.class);
                if (GetImageUrlMap.containsKey(pack.uuid)) {
                    IImageUrls action = GetImageUrlMap.remove(pack.uuid);
                    action.res(pack.url);
                }
                return true;
            }
            case 91: {
                MemberInfoPack pack = JSON.parseObject(data, MemberInfoPack.class);
                QQMember key = new QQMember() {{
                    qq = pack.qq;
                    group = pack.id;
                    member = pack.fid;
                }};
                if (GetMemberInfoMap.containsKey(key)) {
                    IMemberInfo action = GetMemberInfoMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 92: {
                FriendInfoPack pack = JSON.parseObject(data, FriendInfoPack.class);
                QQFriend key = new QQFriend() {{
                    qq = pack.qq;
                    friend = pack.id;
                }};
                if (GetFriendInfoMap.containsKey(key)) {
                    IFriendInfo action = GetFriendInfoMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 101: {
                GroupFilesPack pack = JSON.parseObject(data, GroupFilesPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetGroupFilesMap.containsKey(key)) {
                    IGroupFiles action = GetGroupFilesMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 109: {
                GroupAnnouncementsPack pack = JSON.parseObject(data, GroupAnnouncementsPack.class);
                QQGroup key = new QQGroup() {{
                    qq = pack.qq;
                    group = pack.id;
                }};
                if (GetGroupAnnouncementsMap.containsKey(key)) {
                    IGroupAnnouncements action = GetGroupAnnouncementsMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            default:
                return false;
        }
    }

    /**
     * 55 [插件]获取群列表
     *
     * @param qq_ qq号
     * @param res 获取成功后回调
     */
    public void GetGroups(long qq_, IListGroup res) {
        GetGroupsMap.put(qq_, res);
        byte[] data = BuildPack.Build(new GetPack() {{
            this.qq = qq_;
        }}, 55);
        addTask(data);
    }

    /**
     * 56 [插件]获取好友列表
     *
     * @param qq_ qq号
     * @param res 获取成功后回调
     */
    public void GetFriends(long qq_, IListFriend res) {
        GetFriendsMap.put(qq_, res);
        byte[] data = BuildPack.Build(new GetPack() {{
            this.qq = qq_;
        }}, 56);
        addTask(data);
    }

    /**
     * 57 [插件]获取群成员
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param res    获取成功后回调
     */
    public void GetMembers(long qq_, long group_, IListMember res) {
        QQGroup key = new QQGroup() {{
            this.qq = qq_;
            this.group = group_;
        }};
        GetMembersMap.put(key, res);
        byte[] data = BuildPack.Build(new GroupGetMemberInfoPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, 57);
        addTask(data);
    }

    /**
     * 58 [插件]获取群设置
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param res    获取成功后回调
     */
    public void GetGroupSetting(long qq_, long group_, IGroupSetting res) {
        QQGroup key = new QQGroup() {{
            this.qq = qq_;
            this.group = group_;
        }};
        GetGroupSettingMap.put(key, res);
        byte[] data = BuildPack.Build(new GroupGetSettingPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, 58);
        addTask(data);
    }

    /**
     * 52 [插件]发送群消息
     *
     * @param qq_      qq号
     * @param group_   群号
     * @param message_ 消息
     */
    public void SendGroupMessage(long qq_, long group_, List<String> message_) {
        byte[] data = BuildPack.Build(new SendGroupMessagePack() {{
            this.qq = qq_;
            this.id = group_;
            this.message = message_;
        }}, 52);
        addTask(data);
    }

    /**
     * 53 [插件]发送私聊消息
     *
     * @param qq_      qq号
     * @param group_   群号
     * @param member_  群员
     * @param message_ 消息
     */
    public void SendGroupTempMessage(long qq_, long group_, long member_, List<String> message_) {
        byte[] data = BuildPack.Build(new SendGroupPrivateMessagePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.message = message_;
        }}, 53);
        addTask(data);
    }

    /**
     * 54 [插件]发送好友消息
     *
     * @param qq_      qq号
     * @param friend_  好友QQ号
     * @param message_ 消息
     */
    public void SendFriendMessage(long qq_, long friend_, List<String> message_) {
        byte[] data = BuildPack.Build(new SendFriendMessagePack() {{
            this.qq = qq_;
            this.id = friend_;
            this.message = message_;
        }}, 54);
        addTask(data);
    }

    /**
     * 59 [插件]回应事件
     *
     * @param qq_      qq号
     * @param eventid_ 事件ID
     * @param dofun_   操作方式
     * @param arg_     附加参数
     */
    public void EventCall(long qq_, long eventid_, int dofun_, List<Object> arg_) {
        byte[] data = BuildPack.Build(new EventCallPack() {{
            this.qq = qq_;
            this.eventid = eventid_;
            this.dofun = dofun_;
            this.arg = arg_;
        }}, 59);
        addTask(data);
    }

    /**
     * 同意成员入群
     *
     * @param qq_      qq号
     * @param eventid_ 事件ID
     * @param type     操作类型
     */
    public void MemberJoinRequestCall(long qq_, long eventid_, GroupCallType type) {
        this.MemberJoinRequestCall(qq_, eventid_, type, false, "");
    }

    /**
     * 同意成员入群
     *
     * @param qq_       qq号
     * @param eventid_  事件ID
     * @param type      操作类型
     * @param blackList 是否加入黑名单
     */
    public void MemberJoinRequestCall(long qq_, long eventid_, GroupCallType type, boolean blackList) {
        this.MemberJoinRequestCall(qq_, eventid_, type, blackList, "");
    }

    /**
     * 同意成员入群
     *
     * @param qq_      qq号
     * @param eventid_ 事件ID
     * @param type     操作类型
     * @param message  拒绝理由
     */
    public void MemberJoinRequestCall(long qq_, long eventid_, GroupCallType type, String message) {
        this.MemberJoinRequestCall(qq_, eventid_, type, false, message);
    }

    /**
     * 同意成员入群
     *
     * @param qq_       qq号
     * @param eventid_  事件ID
     * @param type      操作类型
     * @param blackList 是否加入黑名单
     * @param message   拒绝理由
     */
    public void MemberJoinRequestCall(long qq_, long eventid_, GroupCallType type, boolean blackList, String message) {
        EventCall(qq_, eventid_, type.ordinal(), new ArrayList<Object>() {{
            this.add(blackList);
            this.add(message);
        }});
    }

    /**
     * 同意新好友申请
     *
     * @param qq_      qq号
     * @param eventid_ 事件ID
     * @param type     操作类型
     */
    public void NewFriendRequestCall(long qq_, long eventid_, FriendCallType type) {
        EventCall(qq_, eventid_, type.ordinal(), new ArrayList<Object>() {{
            this.add(false);
        }});
    }

    /**
     * 同意新好友申请
     *
     * @param qq_       qq号
     * @param eventid_  事件ID
     * @param type      操作类型
     * @param blackList 是否加入黑名单
     */
    public void NewFriendRequestCall(long qq_, long eventid_, FriendCallType type, boolean blackList) {
        EventCall(qq_, eventid_, type.ordinal(), new ArrayList<Object>() {{
            this.add(blackList);
        }});
    }

    /**
     * 64 [插件]删除群员
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     */
    public void GroupDeleteMember(long qq_, long group_, long member_) {
        byte[] data = BuildPack.Build(new GroupKickMemberPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, 64);
        addTask(data);
    }

    /**
     * 65 [插件]禁言群员
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     * @param time_   禁言时间
     */
    public void GroupMuteMember(long qq_, long group_, long member_, int time_) {
        byte[] data = BuildPack.Build(new GroupMuteMemberPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.time = time_;
        }}, 65);
        addTask(data);
    }

    /**
     * 66 [插件]解除禁言
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     */
    public void GroupUnmuteMember(long qq_, long group_, long member_) {
        byte[] data = BuildPack.Build(new GroupUnmuteMemberPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, 66);
        addTask(data);
    }

    /**
     * 67 [插件]开启全员禁言
     *
     * @param qq_    qq号
     * @param group_ 群号
     */
    public void GroupMuteAll(long qq_, long group_) {
        byte[] data = BuildPack.Build(new GroupMuteAllPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, 67);
        addTask(data);
    }

    /**
     * 68 [插件]关闭全员禁言
     *
     * @param qq_    qq号
     * @param group_ 群号
     */
    public void GroupUnmuteAll(long qq_, long group_) {
        byte[] data = BuildPack.Build(new GroupUnmuteAllPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, 68);
        addTask(data);
    }

    /**
     * 69 [插件]设置群名片
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     * @param card_   群名片
     */
    public void GroupSetMember(long qq_, long group_, long member_, String card_) {
        byte[] data = BuildPack.Build(new GroupSetMemberCard() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.card = card_;
        }}, 69);
        addTask(data);
    }

    /**
     * 70 [插件]设置群名
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param name_  群名字
     */
    public void GroupSetName(long qq_, long group_, String name_) {
        byte[] data = BuildPack.Build(new GroupSetNamePack() {{
            this.qq = qq_;
            this.id = group_;
            this.name = name_;
        }}, 70);
        addTask(data);
    }

    /**
     * 71 [插件]撤回消息
     *
     * @param qq_ qq号
     * @param id_ 消息ID
     */
    public void ReCallMessage(long qq_, int id_) {
        byte[] data = BuildPack.Build(new ReCallMessagePack() {{
            this.qq = qq_;
            this.id = id_;
        }}, 71);
        addTask(data);
    }

    /**
     * 75 [插件]从本地文件加载图片发送到群
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param file_  文件位置
     */
    public void SendGroupImageFile(long qq_, long group_, String file_) {
        byte[] data = BuildPack.Build(new SendGroupImageFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, 75);
        addTask(data);
    }

    /**
     * 76 [插件]从本地文件加载图片 发送到群私聊
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     * @param file_   文件位置
     */
    public void SendGroupPrivateImageFile(long qq_, long group_, long member_, String file_) {
        byte[] data = BuildPack.Build(new SendGroupPrivateImageFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.file = file_;
        }}, 76);
        addTask(data);
    }

    /**
     * 77 [插件]从本地文件加载图片发送到朋友
     *
     * @param qq_    qq号
     * @param group_ 好友QQ号
     * @param file_  文件位置
     */
    public void SendFriendImageFile(long qq_, long group_, String file_) {
        byte[] data = BuildPack.Build(new SendFriendImageFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, 77);
        addTask(data);
    }

    /**
     * 78 [插件]从本地文件加载语音发送到群
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param file_  文件位置
     */
    public void SendGroupSoundFile(long qq_, long group_, String file_) {
        byte[] data = BuildPack.Build(new SendGroupSoundFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, 78);
        addTask(data);
    }

    /**
     * 83 [插件]发送朋友戳一戳
     *
     * @param qq_     qq号
     * @param friend_ 好友QQ号
     */
    public void SendFriendNudge(long qq_, long friend_) {
        byte[] data = BuildPack.Build(new SendFriendNudgePack() {{
            this.qq = qq_;
            this.id = friend_;
        }}, 83);
        addTask(data);
    }

    /**
     * 84 [插件]发送群成员戳一戳
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     */
    public void SendGroupMemberNudge(long qq_, long group_, long member_) {
        byte[] data = BuildPack.Build(new SendGroupMemberNudgePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, 84);
        addTask(data);
    }

    /**
     * 90 [插件]获取图片Url
     *
     * @param qq_   qq号
     * @param uuid_ 图片UUID
     * @param res   返回回调
     */
    public void GetImageUrls(long qq_, String uuid_, IImageUrls res) {
        GetImageUrlMap.put(uuid_, res);
        byte[] data = BuildPack.Build(new GetImageUrlPack() {{
            this.qq = qq_;
            this.uuid = uuid_;
        }}, 90);
        addTask(data);
    }

    /**
     * 91 [插件]获取群成员信息
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     * @param res     返回回调
     */
    public void GetMemberInfo(long qq_, long group_, long member_, IMemberInfo res) {
        QQMember key = new QQMember() {{
            this.qq = qq_;
            this.member = member_;
            this.group = group_;
        }};
        GetMemberInfoMap.put(key, res);
        byte[] data = BuildPack.Build(new GetMemberInfo() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, 91);
        addTask(data);
    }

    /**
     * 92 [插件]获取朋友信息
     *
     * @param qq_     qq号
     * @param friend_ 群号
     * @param res     返回回调
     */
    public void GetFriendInfo(long qq_, long friend_, IFriendInfo res) {
        QQFriend key = new QQFriend() {{
            this.qq = qq_;
            this.friend = friend_;
        }};
        GetFriendInfoMap.put(key, res);
        byte[] data = BuildPack.Build(new GetFriendInfoPack() {{
            this.qq = qq_;
            this.id = friend_;
        }}, 92);
        addTask(data);
    }

    /**
     * 93 [插件]发送音乐分享
     *
     * @param qq_         qq号
     * @param id_         发送目标
     * @param fid_        发送目标
     * @param kind_       音乐类型
     * @param type_       目标类型
     * @param title_      标题
     * @param summary_    概要
     * @param jumpUrl_    跳转Url
     * @param pictureUrl_ 图片Url
     * @param musicUrl_   音乐Url
     */
    public void SendMusicShare(long qq_, long id_, long fid_, MusicKind kind_,
                               SendToType type_, String title_, String summary_,
                               String jumpUrl_, String pictureUrl_, String musicUrl_) {
        byte[] data = BuildPack.Build(new SendMusicSharePack() {{
            this.qq = qq_;
            this.id = id_;
            this.fid = fid_;
            this.type = kind_.ordinal() + 1;
            this.type1 = type_.ordinal();
            this.title = title_;
            this.summary = summary_;
            this.jumpUrl = jumpUrl_;
            this.pictureUrl = pictureUrl_;
            this.musicUrl = musicUrl_;
        }}, 93);
        addTask(data);
    }

    /**
     * 94 [插件]设置群精华消息
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param mid_   消息ID
     */
    public void GroupSetEssenceMessage(long qq_, long group_, int mid_) {
        byte[] data = BuildPack.Build(new GroupSetEssenceMessagePack() {{
            this.qq = qq_;
            this.id = group_;
            this.mid = mid_;
        }}, 94);
        addTask(data);
    }

    /**
     * 95 [插件]消息队列
     *
     * @param qq_      qq号
     * @param id_      发送目标
     * @param fid_     发送目标
     * @param type_    发送目标类型
     * @param file     图片文件位置
     * @param message_ 消息内容
     * @param send_    是否发送
     */
    public void MessageBuff(long qq_, long id_, long fid_, SendToType type_, String file, List<String> message_, boolean send_) {
        byte[] data = BuildPack.Build(new MessageBuffPack() {{
            this.qq = qq_;
            this.id = id_;
            this.fid = fid_;
            this.type = type_.ordinal();
            this.imgurl = file;
            this.text = message_;
            this.send = send_;
        }}, 95);
        addTask(data);
    }

    /**
     * 96 [插件]发送朋友骰子
     *
     * @param qq_     qq号
     * @param friend_ 好友QQ号
     * @param dice_   点数
     */
    public void SendFriendDice(long qq_, long friend_, int dice_) {
        byte[] data = BuildPack.Build(new SendFriendDicePack() {{
            this.qq = qq_;
            this.id = friend_;
            this.dice = dice_;
        }}, 96);
        addTask(data);
    }

    /**
     * 97 [插件]发送群骰子
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param dice_  点数
     */
    public void SendGroupDice(long qq_, long group_, int dice_) {
        byte[] data = BuildPack.Build(new SendGroupDicePack() {{
            this.qq = qq_;
            this.id = group_;
            this.dice = dice_;
        }}, 97);
        addTask(data);
    }

    /**
     * 98 [插件]发送群私聊骰子
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群成员
     * @param dice_   点数
     */
    public void SendGroupPrivateDice(long qq_, long group_, long member_, int dice_) {
        byte[] data = BuildPack.Build(new SendGroupPrivateDicePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.dice = dice_;
        }}, 98);
        addTask(data);
    }

    /**
     * 99 [插件]上传群文件
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param file_  文件路径
     * @param name_  群文件名称
     */
    public void GroupAddFilePack(long qq_, long group_, String file_, String name_) {
        byte[] data = BuildPack.Build(new GroupAddFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
            this.name = name_;
        }}, 99);
        addTask(data);
    }
}
