package coloryr.colormirai.demo.sdk;

import coloryr.colormirai.demo.sdk.api.*;
import coloryr.colormirai.demo.sdk.enums.FriendCallType;
import coloryr.colormirai.demo.sdk.enums.GroupCallType;
import coloryr.colormirai.demo.sdk.enums.MusicKind;
import coloryr.colormirai.demo.sdk.enums.SendToType;
import coloryr.colormirai.demo.sdk.pack.from.*;
import coloryr.colormirai.demo.sdk.pack.re.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotTop extends RobotBase {
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
    protected boolean callTop(byte index, Object data) {
        switch (index) {
            case 55: {
                ReListGroupPack pack = (ReListGroupPack) data;
                if (getGroupsMap.containsKey(pack.qq)) {
                    IListGroup action = getGroupsMap.remove(pack.qq);
                    action.res(pack);
                }
                return true;
            }
            case 56: {
                ReListFriendPack pack = (ReListFriendPack) data;
                if (getFriendsMap.containsKey(pack.qq)) {
                    IListFriend action = getFriendsMap.remove(pack.qq);
                    action.res(pack);
                }
                return true;
            }
            case 57: {
                ReListMemberPack pack = (ReListMemberPack) data;
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
                ReGroupSettingPack pack = (ReGroupSettingPack) data;
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
                ReGetImageUrlPack pack = (ReGetImageUrlPack) data;
                if (getImageUrlMap.containsKey(pack.uuid)) {
                    IImageUrls action = getImageUrlMap.remove(pack.uuid);
                    action.res(pack.url);
                }
                return true;
            }
            case 91: {
                ReMemberInfoPack pack = (ReMemberInfoPack) data;
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
                ReFriendInfoPack pack = (ReFriendInfoPack) data;
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
                ReGroupFilesPack pack = (ReGroupFilesPack) data;
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
                ReGroupAnnouncementsPack pack = (ReGroupAnnouncementsPack) data;
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
        addSend(new GetPack() {{
            this.qq = qq_;
        }}, (byte) 55);
    }

    /**
     * 56 [插件]获取好友列表
     *
     * @param qq_ qq号
     * @param res 获取成功后回调
     */
    public void getFriends(long qq_, IListFriend res) {
        getFriendsMap.put(qq_, res);
        addSend(new GetPack() {{
            this.qq = qq_;
        }}, (byte) 56);
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
        addSend(new GroupGetMemberInfoPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, (byte) 57);
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
        addSend(new GroupGetSettingPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, (byte) 58);
    }

    /**
     * 52 [插件]发送群消息
     *
     * @param qq_      qq号
     * @param group_   群号
     * @param message_ 消息
     */
    public void sendGroupMessage(long qq_, long group_, List<String> message_) {
        addSend(new SendGroupMessagePack() {{
            this.qq = qq_;
            this.id = group_;
            this.message = message_;
        }}, (byte) 52);
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
        addSend(new SendGroupPrivateMessagePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.message = message_;
        }}, (byte) 53);
    }

    /**
     * 54 [插件]发送好友消息
     *
     * @param qq_      qq号
     * @param friend_  好友QQ号
     * @param message_ 消息
     */
    public void sendFriendMessage(long qq_, long friend_, List<String> message_) {
        addSend(new SendFriendMessagePack() {{
            this.qq = qq_;
            this.id = friend_;
            this.message = message_;
        }}, (byte) 54);
    }

    /**
     * 59 [插件]回应事件
     *
     * @param qq_      qq号
     * @param eventid_ 事件ID
     * @param dofun_   操作方式
     * @param arg_     附加参数
     */
    public void eventCall(long qq_, long eventid_, int dofun_, List<String> arg_) {
        addSend(new EventCallPack() {{
            this.qq = qq_;
            this.eventid = eventid_;
            this.dofun = dofun_;
            this.arg = arg_;
        }}, (byte) 59);
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
        eventCall(qq_, eventid_, type.ordinal(), new ArrayList<String>() {{
            this.add(String.valueOf(blackList));
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
        eventCall(qq_, eventid_, type.ordinal(), new ArrayList<String>() {{
            this.add("true");
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
        eventCall(qq_, eventid_, type.ordinal(), new ArrayList<String>() {{
            this.add(String.valueOf(blackList));
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
        addSend(new GroupKickMemberPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, (byte) 64);
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
        addSend(new GroupMuteMemberPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.time = time_;
        }}, (byte) 65);
    }

    /**
     * 66 [插件]解除禁言
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     */
    public void groupUnmuteMember(long qq_, long group_, long member_) {
        addSend(new GroupUnmuteMemberPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, (byte) 66);
    }

    /**
     * 67 [插件]开启全员禁言
     *
     * @param qq_    qq号
     * @param group_ 群号
     */
    public void groupMuteAll(long qq_, long group_) {
        addSend(new GroupMuteAllPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, (byte) 67);
    }

    /**
     * 68 [插件]关闭全员禁言
     *
     * @param qq_    qq号
     * @param group_ 群号
     */
    public void groupUnmuteAll(long qq_, long group_) {
        addSend(new GroupUnmuteAllPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, (byte) 68);
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
        addSend(new GroupSetMemberCardPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.card = card_;
        }}, (byte) 69);
    }

    /**
     * 70 [插件]设置群名
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param name_  群名字
     */
    public void groupSetName(long qq_, long group_, String name_) {
        addSend(new GroupSetNamePack() {{
            this.qq = qq_;
            this.id = group_;
            this.name = name_;
        }}, (byte) 70);
    }

    /**
     * 71 [插件]撤回消息
     *
     * @param qq_ qq号
     * @param id_ 消息ID
     */
    public void reCallMessage(long qq_, int id_) {
        addSend(new ReCallMessagePack() {{
            this.qq = qq_;
            this.id = id_;
        }}, (byte) 71);
    }

    /**
     * 75 [插件]从本地文件加载图片发送到群
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param file_  文件位置
     */
    public void sendGroupImageFile(long qq_, long group_, String file_) {
        addSend(new SendGroupImageFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, (byte) 75);
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
        addSend(new SendGroupPrivateImageFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.file = file_;
        }}, (byte) 76);
    }

    /**
     * 77 [插件]从本地文件加载图片发送到朋友
     *
     * @param qq_    qq号
     * @param group_ 好友QQ号
     * @param file_  文件位置
     */
    public void sendFriendImageFile(long qq_, long group_, String file_) {
        addSend(new SendFriendImageFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, (byte) 77);
    }

    /**
     * 78 [插件]从本地文件加载语音发送到群
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param file_  文件位置
     */
    public void sendGroupSoundFile(long qq_, long group_, String file_) {
        addSend(new SendGroupSoundFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, (byte) 78);
    }

    /**
     * 83 [插件]发送朋友戳一戳
     *
     * @param qq_     qq号
     * @param friend_ 好友QQ号
     */
    public void sendFriendNudge(long qq_, long friend_) {
        addSend(new SendFriendNudgePack() {{
            this.qq = qq_;
            this.id = friend_;
        }}, (byte) 83);
    }

    /**
     * 84 [插件]发送群成员戳一戳
     *
     * @param qq_     qq号
     * @param group_  群号
     * @param member_ 群员
     */
    public void sendGroupMemberNudge(long qq_, long group_, long member_) {
        addSend(new SendGroupMemberNudgePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, (byte) 84);
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
        addSend(new GetImageUrlPack() {{
            this.qq = qq_;
            this.uuid = uuid_;
        }}, (byte) 90);
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
        addSend(new GetMemberInfoPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
        }}, (byte) 91);
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
        addSend(new GetFriendInfoPack() {{
            this.qq = qq_;
            this.id = friend_;
        }}, (byte) 92);
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
        addSend(new SendMusicSharePack() {{
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
        }}, (byte) 93);
    }

    /**
     * 94 [插件]设置群精华消息
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param ids1_   消息ID
     * @param ids2_   消息ID
     */
    public void groupSetEssenceMessage(long qq_, long group_, int[] ids1_, int[] ids2_) {
        addSend(new GroupSetEssenceMessagePack() {{
            this.qq = qq_;
            this.id = group_;
            this.ids1 = ids1_;
            this.ids2 = ids2_;
        }}, (byte) 94);
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
        addSend(new MessageBuffPack() {{
            this.qq = qq_;
            this.id = id_;
            this.fid = fid_;
            this.type = type_.ordinal();
            this.imgurl = file;
            this.text = message_;
            this.send = send_;
        }}, (byte) 95);
    }

    /**
     * 96 [插件]发送朋友骰子
     *
     * @param qq_     qq号
     * @param friend_ 好友QQ号
     * @param dice_   点数
     */
    public void sendFriendDice(long qq_, long friend_, int dice_) {
        addSend(new SendFriendDicePack() {{
            this.qq = qq_;
            this.id = friend_;
            this.dice = dice_;
        }}, (byte) 96);
    }

    /**
     * 97 [插件]发送群骰子
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param dice_  点数
     */
    public void sendGroupDice(long qq_, long group_, int dice_) {
        addSend(new SendGroupDicePack() {{
            this.qq = qq_;
            this.id = group_;
            this.dice = dice_;
        }}, (byte) 97);
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
        addSend(new SendGroupPrivateDicePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.dice = dice_;
        }}, (byte) 98);
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
        addSend(new GroupAddFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
            this.name = name_;
        }}, (byte) 99);
    }

    /**
     * 100 [插件]删除群文件
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param fid_   群文件ID
     */
    public void groupDeleteFile(long qq_, long group_, String fid_) {
        addSend(new GroupDeleteFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
        }}, (byte) 100);
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
        addSend(new GroupGetFilesPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, (byte) 101);
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
        addSend(new GroupMoveFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
            this.dir = dir_;
        }}, (byte) 102);
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
        addSend(new GroupRenameFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
            this.now = name_;
        }}, (byte) 103);
    }

    /**
     * 104 [插件]创新群文件文件夹
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param dir_   文件夹名字
     */
    public void groupAddDir(long qq_, long group_, String dir_) {
        addSend(new GroupAddDirPack() {{
            this.qq = qq_;
            this.id = group_;
            this.dir = dir_;
        }}, (byte) 104);
    }

    /**
     * 105 [插件]删除群文件文件夹
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param dir_   文件夹名字
     */
    public void groupRemoveDir(long qq_, long group_, String dir_) {
        addSend(new GroupDeleteDirPack() {{
            this.qq = qq_;
            this.id = group_;
            this.dir = dir_;
        }}, (byte) 105);
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
        addSend(new GroupRenameDirPack() {{
            this.qq = qq_;
            this.id = group_;
            this.old = old_;
            this.now = now_;
        }}, (byte) 106);
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
        addSend(new GroupDownloadFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
            this.dir = file_;
        }}, (byte) 107);
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
        addSend(new GroupSetAdminPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = member_;
            this.type = set_;
        }}, (byte) 108);
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
        addSend(new GroupGetAnnouncementsPack() {{
            this.qq = qq_;
            this.id = group_;
        }}, (byte) 109);
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
        addSend(new GroupAddAnnouncementPack() {{
            this.qq = qq_;
            this.id = group_;
            this.imageFile = imageFile_;
            this.sendToNewMember = sendToNewMember_;
            this.isPinned = isPinned_;
            this.showEditCard = showEditCard_;
            this.showPopup = showPopup_;
            this.requireConfirmation = requireConfirmation_;
            this.text = text_;
        }}, (byte) 110);
    }

    /**
     * 111 [插件]删除群公告
     *
     * @param qq_    qq号
     * @param group_ 群号
     * @param fid_   公告ID
     */
    public void groupRemoveAnnouncement(long qq_, long group_, String fid_) {
        addSend(new GroupDeleteAnnouncementPack() {{
            this.qq = qq_;
            this.id = group_;
            this.fid = fid_;
        }}, (byte) 111);
    }

    /**
     * 112 [插件]发送好友语言文件
     *
     * @param qq_    qq号
     * @param group_ 好友QQ号
     * @param file_  文件路径
     */
    public void sendFriendSoundFile(long qq_, long group_, String file_) {
        addSend(new SendFriendSoundFilePack() {{
            this.qq = qq_;
            this.id = group_;
            this.file = file_;
        }}, (byte) 112);
    }

    /**
     * 117 [插件]发送陌生人消息
     *
     * @param qq_      qq号
     * @param stranger 陌生人QQ号
     * @param message_ 消息
     */
    public void sendStrangerMessage(long qq_, long stranger, List<String> message_) {
        addSend(new SendStrangerMessagePack() {{
            qq = qq_;
            id = stranger;
            message = message_;
        }}, (byte) 117);
    }

    /**
     * 118 [插件]从本地文件加载图片发送到陌生人
     *
     * @param qq_      qq号
     * @param stranger 陌生人QQ号
     * @param file     文件路径
     */
    public void sendStrangerImageFile(long qq_, long stranger, String file) {
        sendStrangerImageFile(qq_, stranger, file, null);
    }

    /**
     * 118 [插件]从本地文件加载图片发送到陌生人
     *
     * @param qq_      qq号
     * @param stranger 陌生人QQ号
     * @param file_    文件路径
     * @param ids_     陌生人QQ号组
     */
    public void sendStrangerImageFile(long qq_, long stranger, String file_, List<Long> ids_) {
        addSend(new SendStrangerImageFilePack() {{
            qq = qq_;
            id = stranger;
            file = file_;
            ids = ids_;
        }}, (byte) 118);
    }

    /**
     * 119 [插件]发送陌生人骰子
     *
     * @param qq_      qq号
     * @param stranger 陌生人QQ号
     * @param dice_    点数
     */
    public void sendStrangerDice(long qq_, long stranger, int dice_) {
        addSend(new SendStrangerDicePack() {{
            qq = qq_;
            id = stranger;
            dice = dice_;
        }}, (byte) 119);
    }

    /**
     * 120 [插件]发送陌生人戳一戳
     *
     * @param qq_      qq号
     * @param stranger 陌生人QQ号
     */
    public void sendStrangerNudge(long qq_, long stranger) {
        addSend(new SendStrangerNudgePack() {{
            qq = qq_;
            id = stranger;
        }}, (byte) 120);
    }

    /**
     * 121 [插件]从本地文件加载语音发送到陌生人
     *
     * @param qq       qq号
     * @param stranger 陌生人QQ号
     * @param file     文件路径
     */
    public void SendStrangerSoundFile(long qq, long stranger, String file) {
        SendStrangerSoundFile(qq, stranger, file, null);
    }

    /**
     * 121 [插件]从本地文件加载语音发送到陌生人
     *
     * @param qq_      qq号
     * @param stranger 陌生人QQ号
     * @param file_    文件路径
     * @param ids_     陌生人QQ号组
     */
    public void SendStrangerSoundFile(long qq_, long stranger, String file_, List<Long> ids_) {
        addSend(new SendStrangerSoundFilePack() {{
            qq = qq_;
            id = stranger;
            file = file_;
            ids = ids_;
        }}, (byte) 121);
    }

    /**
     * 126 [插件]发送好友语音
     *
     * @param qq   qq号
     * @param id   QQ号
     * @param data 语音内容
     */
    public void sendFriendSound(long qq, long id, byte[] data) {
        sendFriendSound(qq, id, data, null);
    }

    /**
     * 126 [插件]发送好友语音
     *
     * @param qq_   qq号
     * @param id    QQ号
     * @param data_ 语音内容
     * @param ids_  QQ号组
     */
    public void sendFriendSound(long qq_, long id, byte[] data_, List<Long> ids_) {
        addSend(new SendFriendSoundPack() {{
            qq = qq_;
            id = id;
            data = data_;
            ids = ids_;
        }}, (byte) 126);
    }
}
