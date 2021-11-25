package coloryr.colormirai.demo.sdk;

import coloryr.colormirai.demo.sdk.api.*;
import coloryr.colormirai.demo.sdk.enums.FriendCallType;
import coloryr.colormirai.demo.sdk.enums.GroupCallType;
import coloryr.colormirai.demo.sdk.enums.MusicKind;
import coloryr.colormirai.demo.sdk.enums.SendToType;
import coloryr.colormirai.demo.sdk.pack.from.*;
import coloryr.colormirai.demo.sdk.pack.re.*;
import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopRobot extends BaseRobot {
    private final Map<Long, IListFriend> getFriendsMap = new HashMap<>();
    private final Map<QQGroup, IListMember> getMembersMap = new HashMap<>();
    private final Map<QQGroup, IGroupSetting> getGroupSettingMap = new HashMap<>();
    private final Map<Long, IListGroup> getGroupsMap = new HashMap<>();
    private final Map<String, IImageUrls> getImageUrlMap = new HashMap<>();
    private final Map<QQMember, IMemberInfo> getMemberInfoMap = new HashMap<>();
    private final Map<QQFriend, IFriendInfo> getFriendInfoMap = new HashMap<>();
    private final Map<QQGroup, IGroupFiles> getGroupFilesMap = new HashMap<>();
    private final Map<QQGroup, IGroupAnnouncements> getGroupAnnouncementsMap = new HashMap<>();

    @Override
    protected boolean callTop(byte index, String data) {
        switch (index) {
            case 55: {
                ListGroupPack pack = JSON.parseObject(data, ListGroupPack.class);
                if (getGroupsMap.containsKey(pack.qq)) {
                    IListGroup action = getGroupsMap.remove(pack.qq);
                    action.res(pack);
                }
                return true;
            }
            case 56: {
                ListFriendPack pack = JSON.parseObject(data, ListFriendPack.class);
                if (getFriendsMap.containsKey(pack.qq)) {
                    IListFriend action = getFriendsMap.remove(pack.qq);
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
                if (getMembersMap.containsKey(key)) {
                    IListMember action = getMembersMap.remove(key);
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
                if (getGroupSettingMap.containsKey(key)) {
                    IGroupSetting action = getGroupSettingMap.remove(key);
                    action.res(pack);
                }
                return true;
            }
            case 90: {
                ReImagePack pack = JSON.parseObject(data, ReImagePack.class);
                if (getImageUrlMap.containsKey(pack.uuid)) {
                    IImageUrls action = getImageUrlMap.remove(pack.uuid);
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
                if (getMemberInfoMap.containsKey(key)) {
                    IMemberInfo action = getMemberInfoMap.remove(key);
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
                if (getFriendInfoMap.containsKey(key)) {
                    IFriendInfo action = getFriendInfoMap.remove(key);
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
                if (getGroupFilesMap.containsKey(key)) {
                    IGroupFiles action = getGroupFilesMap.remove(key);
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
                if (getGroupAnnouncementsMap.containsKey(key)) {
                    IGroupAnnouncements action = getGroupAnnouncementsMap.remove(key);
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
    public void getGroups(long qq_, IListGroup res) {
        getGroupsMap.put(qq_, res);
        byte[] data = BuildPack.build(new GetPack() {{
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
    public void getFriends(long qq_, IListFriend res) {
        getFriendsMap.put(qq_, res);
        byte[] data = BuildPack.build(new GetPack() {{
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
    public void getMembers(long qq_, long group_, IListMember res) {
        QQGroup key = new QQGroup() {{
            this.qq = qq_;
            this.group = group_;
        }};
        getMembersMap.put(key, res);
        byte[] data = BuildPack.build(new GroupGetMemberInfoPack() {{
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
    public void getGroupSetting(long qq_, long group_, IGroupSetting res) {
        QQGroup key = new QQGroup() {{
            this.qq = qq_;
            this.group = group_;
        }};
        getGroupSettingMap.put(key, res);
        byte[] data = BuildPack.build(new GroupGetSettingPack() {{
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
    public void sendGroupMessage(long qq_, long group_, List<String> message_) {
        byte[] data = BuildPack.build(new SendGroupMessagePack() {{
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
    public void sendGroupTempMessage(long qq_, long group_, long member_, List<String> message_) {
        byte[] data = BuildPack.build(new SendGroupPrivateMessagePack() {{
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
    public void sendFriendMessage(long qq_, long friend_, List<String> message_) {
        byte[] data = BuildPack.build(new SendFriendMessagePack() {{
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
    public void eventCall(long qq_, long eventid_, int dofun_, List<Object> arg_) {
        byte[] data = BuildPack.build(new EventCallPack() {{
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
    public void memberJoinRequestCall(long qq_, long eventid_, GroupCallType type) {
        this.memberJoinRequestCall(qq_, eventid_, type, false, "");
    }

    /**
     * 同意成员入群
     *
     * @param qq_       qq号
     * @param eventid_  事件ID
     * @param type      操作类型
     * @param blackList 是否加入黑名单
     */
    public void memberJoinRequestCall(long qq_, long eventid_, GroupCallType type, boolean blackList) {
        this.memberJoinRequestCall(qq_, eventid_, type, blackList, "");
    }

    /**
     * 同意成员入群
     *
     * @param qq_      qq号
     * @param eventid_ 事件ID
     * @param type     操作类型
     * @param message  拒绝理由
     */
    public void memberJoinRequestCall(long qq_, long eventid_, GroupCallType type, String message) {
        this.memberJoinRequestCall(qq_, eventid_, type, false, message);
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
    public void memberJoinRequestCall(long qq_, long eventid_, GroupCallType type, boolean blackList, String message) {
        eventCall(qq_, eventid_, type.ordinal(), new ArrayList<Object>() {{
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
    public void newFriendRequestCall(long qq_, long eventid_, FriendCallType type) {
        eventCall(qq_, eventid_, type.ordinal(), new ArrayList<Object>() {{
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
    public void newFriendRequestCall(long qq_, long eventid_, FriendCallType type, boolean blackList) {
        eventCall(qq_, eventid_, type.ordinal(), new ArrayList<Object>() {{
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
    public void groupDeleteMember(long qq_, long group_, long member_) {
        byte[] data = BuildPack.build(new GroupKickMemberPack() {{
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
    public void groupMuteMember(long qq_, long group_, long member_, int time_) {
        byte[] data = BuildPack.build(new GroupMuteMemberPack() {{
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
    public void groupUnmuteMember(long qq_, long group_, long member_) {
        byte[] data = BuildPack.build(new GroupUnmuteMemberPack() {{
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
    public void groupMuteAll(long qq_, long group_) {
        byte[] data = BuildPack.build(new GroupMuteAllPack() {{
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
    public void groupUnmuteAll(long qq_, long group_) {
        byte[] data = BuildPack.build(new GroupUnmuteAllPack() {{
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
    public void groupSetMember(long qq_, long group_, long member_, String card_) {
        byte[] data = BuildPack.build(new GroupSetMemberCard() {{
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
    public void groupSetName(long qq_, long group_, String name_) {
        byte[] data = BuildPack.build(new GroupSetNamePack() {{
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
    public void reCallMessage(long qq_, int id_) {
        byte[] data = BuildPack.build(new ReCallMessagePack() {{
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
    public void sendGroupImageFile(long qq_, long group_, String file_) {
        byte[] data = BuildPack.build(new SendGroupImageFilePack() {{
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
    public void sendGroupPrivateImageFile(long qq_, long group_, long member_, String file_) {
        byte[] data = BuildPack.build(new SendGroupPrivateImageFilePack() {{
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
    public void sendFriendImageFile(long qq_, long group_, String file_) {
        byte[] data = BuildPack.build(new SendFriendImageFilePack() {{
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
    public void sendGroupSoundFile(long qq_, long group_, String file_) {
        byte[] data = BuildPack.build(new SendGroupSoundFilePack() {{
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
    public void sendFriendNudge(long qq_, long friend_) {
        byte[] data = BuildPack.build(new SendFriendNudgePack() {{
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
    public void sendGroupMemberNudge(long qq_, long group_, long member_) {
        byte[] data = BuildPack.build(new SendGroupMemberNudgePack() {{
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
    public void getImageUrls(long qq_, String uuid_, IImageUrls res) {
        getImageUrlMap.put(uuid_, res);
        byte[] data = BuildPack.build(new GetImageUrlPack() {{
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
    public void getMemberInfo(long qq_, long group_, long member_, IMemberInfo res) {
        QQMember key = new QQMember() {{
            this.qq = qq_;
            this.member = member_;
            this.group = group_;
        }};
        getMemberInfoMap.put(key, res);
        byte[] data = BuildPack.build(new GetMemberInfo() {{
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
    public void getFriendInfo(long qq_, long friend_, IFriendInfo res) {
        QQFriend key = new QQFriend() {{
            this.qq = qq_;
            this.friend = friend_;
        }};
        getFriendInfoMap.put(key, res);
        byte[] data = BuildPack.build(new GetFriendInfoPack() {{
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
    public void sendMusicShare(long qq_, long id_, long fid_, MusicKind kind_,
                               SendToType type_, String title_, String summary_,
                               String jumpUrl_, String pictureUrl_, String musicUrl_) {
        byte[] data = BuildPack.build(new SendMusicSharePack() {{
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
    public void groupSetEssenceMessage(long qq_, long group_, int mid_) {
        byte[] data = BuildPack.build(new GroupSetEssenceMessagePack() {{
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
    public void messageBuff(long qq_, long id_, long fid_, SendToType type_, String file, List<String> message_, boolean send_) {
        byte[] data = BuildPack.build(new MessageBuffPack() {{
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
    public void sendFriendDice(long qq_, long friend_, int dice_) {
        byte[] data = BuildPack.build(new SendFriendDicePack() {{
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
    public void sendGroupDice(long qq_, long group_, int dice_) {
        byte[] data = BuildPack.build(new SendGroupDicePack() {{
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
    public void sendGroupPrivateDice(long qq_, long group_, long member_, int dice_) {
        byte[] data = BuildPack.build(new SendGroupPrivateDicePack() {{
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
    public void groupAddFilePack(long qq_, long group_, String file_, String name_) {
        byte[] data = BuildPack.build(new GroupAddFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
            this.name = name_;
        }}, 99);
        addTask(data);
    }

    /**
     * 100 [插件]删除群文件
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param fid_   群文件ID
     */
    public void groupDeleteFile(long qq_, long group_, String fid_) {
        byte[] data = BuildPack.build(new GroupDeleteFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
        }}, 100);
        addTask(data);
    }

    /**
     * 101 [插件]获取群文件
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param res    回调
     */
    public void groupGetFiles(long qq_, long group_, IGroupFiles res) {
        QQGroup key = new QQGroup() {{
            this.qq = qq_;
            this.group = group_;
        }};
        getGroupFilesMap.put(key, res);
        byte[] data = BuildPack.build(new GroupGetFilesPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, 101);
        addTask(data);
    }

    /**
     * 102 [插件]移动群文件
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param fid_   文件ID
     * @param dir_   新的路径
     */
    public void groupMoveFile(long qq_, long group_, String fid_, String dir_) {
        byte[] data = BuildPack.build(new GroupMoveFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
            this.dir = dir_;
        }}, 102);
        addTask(data);
    }

    /**
     * 103 [插件]重命名群文件
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param fid_   文件ID
     * @param name_  新文件名
     */
    public void groupRemoveFile(long qq_, long group_, String fid_, String name_) {
        byte[] data = BuildPack.build(new GroupRenameFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
            this.now = name_;
        }}, 103);
        addTask(data);
    }

    /**
     * 104 [插件]创新群文件文件夹
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param dir_   文件夹名字
     */
    public void groupAddDir(long qq_, long group_, String dir_) {
        byte[] data = BuildPack.build(new GroupAddDirPack() {{
            this.qq = qq_;
            this.id = group_;
            this.dir = dir_;
        }}, 104);
        addTask(data);
    }

    /**
     * 105 [插件]删除群文件文件夹
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param dir_   文件夹名字
     */
    public void groupRemoveDir(long qq_, long group_, String dir_) {
        byte[] data = BuildPack.build(new GroupDeleteDirPack() {{
            this.qq = qq_;
            this.id = group_;
            this.dir = dir_;
        }}, 105);
        addTask(data);
    }

    /**
     * 106 [插件]重命名群文件文件夹
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param old_   旧的名字
     * @param now_   新的名字
     */
    public void groupRenameDir(long qq_, long group_, String old_, String now_) {
        byte[] data = BuildPack.build(new GroupRenameDirPack() {{
            this.qq = qq_;
            this.id = group_;
            this.old = old_;
            this.now = now_;
        }}, 106);
        addTask(data);
    }

    /**
     * 107 [插件]下载群文件到指定位置
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param fid_   文件ID
     * @param file_  下载到的位置
     */
    public void groupDownloadFile(long qq_, long group_, String fid_, String file_) {
        byte[] data = BuildPack.build(new GroupDownloadFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
            this.dir = file_;
        }}, 107);
        addTask(data);
    }

    /**
     * 108 [插件]设置取消管理员
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群成员QQ号
     * @param set_    是否设置
     */
    public void groupSetAdmin(long qq_, long group_, long member_, boolean set_) {
        byte[] data = BuildPack.build(new GroupSetAdminPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.type = set_;
        }}, 108);
        addTask(data);
    }

    /**
     * 109 [插件]获取群公告
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param res    回调
     */
    public void groupGetAnnouncements(long qq_, long group_, IGroupAnnouncements res) {
        QQGroup key = new QQGroup() {{
            this.qq = qq_;
            this.group = group_;
        }};
        getGroupAnnouncementsMap.put(key, res);
        byte[] data = BuildPack.build(new GroupGetAnnouncementsPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, 109);
        addTask(data);
    }

    /**
     * 110 [插件]设置群公告
     *
     * @param qq_                  qq号
     * @param group_               群号
     * @param imageFile_           图片路径
     * @param sendToNewMember_     发送给新群员
     * @param isPinned_            顶置
     * @param showEditCard_        显示能够引导群成员修改昵称的窗口
     * @param showPopup_           使用弹窗
     * @param requireConfirmation_ 需要群成员确认
     * @param text_                公告内容
     */
    public void groupAddAnnouncement(long qq_, long group_, String imageFile_, boolean sendToNewMember_,
                                     boolean isPinned_, boolean showEditCard_, boolean showPopup_,
                                     boolean requireConfirmation_, String text_) {
        byte[] data = BuildPack.build(new GroupAddAnnouncementPack() {{
            this.qq = qq_;
            this.id = group_;
            this.imageFile = imageFile_;
            this.sendToNewMember = sendToNewMember_;
            this.isPinned = isPinned_;
            this.showEditCard = showEditCard_;
            this.showPopup = showPopup_;
            this.requireConfirmation = requireConfirmation_;
            this.text = text_;
        }}, 110);
        addTask(data);
    }

    /**
     * 111 [插件]删除群公告
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param fid_   公告ID
     */
    public void groupRemoveAnnouncement(long qq_, long group_, String fid_) {
        byte[] data = BuildPack.build(new GroupDeleteAnnouncementPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
        }}, 111);
        addTask(data);
    }

    /**
     * 112 [插件]发送好友语言文件
     *
     * @param qq_    qq号
     * @param group_ 好友QQ号
     * @param file_  文件路径
     */
    public void sendFriendSoundFile(long qq_, long group_, String file_) {
        byte[] data = BuildPack.build(new SendFriendSoundFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, 112);
        addTask(data);
    }
}
