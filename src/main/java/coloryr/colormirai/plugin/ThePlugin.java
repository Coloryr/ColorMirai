package coloryr.colormirai.plugin;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.obj.BuffObj;
import coloryr.colormirai.plugin.obj.PluginPack;
import coloryr.colormirai.plugin.obj.SendPackObj;
import coloryr.colormirai.plugin.pack.from.*;
import coloryr.colormirai.plugin.pack.re.*;
import coloryr.colormirai.robot.*;
import coloryr.colormirai.robot.event.EventCall;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.GroupSettings;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.announcement.OnlineAnnouncement;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class ThePlugin {
    private final IPluginSocket socket;
    private final Queue<PluginPack> tasks = new ConcurrentLinkedDeque<>();
    private final List<Long> groups = new ArrayList<>();
    private final List<Long> qqList = new ArrayList<>();
    private final Map<String, BuffObj> messageBuff = new ConcurrentHashMap<>();

    private final Thread doRead;
    private final Semaphore semaphore = new Semaphore(0, false);

    private String name;
    private long runQQ;
    private List<Integer> events = null;

    private boolean allEvent = false;
    private boolean isRun;

    public ThePlugin(IPluginSocket Socket) {
        this.socket = Socket;
        doRead = new Thread(this::run);
    }

    public void addGroup(List<Long> list) {
        groups.addAll(list);
    }

    public void addQQs(List<Long> list) {
        qqList.addAll(list);
    }

    public void setName(String name) {
        this.name = name;
        doRead.setName("Plugin[" + name + "]MessageThread");
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public void setEvents(List<Integer> events) {
        this.events = events;
        if (events.isEmpty())
            allEvent = true;
    }

    public void setRunQQ(long runQQ) {
        this.runQQ = runQQ;
    }

    public void startRead() {
        doRead.start();
    }

    public void addPack(PluginPack obj) {
        tasks.add(obj);
        semaphore.release();
    }

    public boolean isRun() {
        return isRun;
    }

    public String getName() {
        return name;
    }

    public String getReg() {
        if (events.size() == 0)
            return "全部事件";
        StringBuilder stringBuilder = new StringBuilder();
        for (int item : events) {
            stringBuilder.append(item).append(",");
        }
        String data = stringBuilder.toString();
        return data.substring(0, data.length() - 1);
    }

    private void run() {
        while (isRun) {
            try {
                semaphore.acquire();
                while (!tasks.isEmpty()) {
                    PluginPack task = tasks.poll();
                    switch (task.index) {
                        //27 [插件]下载文件
                        //好友文件还未支持
//                        case 27: {
//                            DownloadFilePack pack = (DownloadFilePack) task.pack;
//                            ReDownloadFilePack pack1 = BotDownload.download(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.internalId, pack.name, pack.size, pack.local, pack.fid, pack.qid, pack.uuid);
//                            if (socket.send(pack1, 27))
//                                close();
//                            break;
//                        }
                        //52 [插件]发送群消息
                        case 52: {
                            SendGroupMessagePack pack = (SendGroupMessagePack) task.pack;
                            BotSendMessage.sendGroupMessage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message, pack.ids);
                            break;
                        }
                        //53 [插件]发送私聊消息
                        case 53: {
                            SendGroupPrivateMessagePack pack = (SendGroupPrivateMessagePack) task.pack;
                            BotSendMessage.sendGroupPrivateMessage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.message, pack.ids);
                            break;
                        }
                        //54 [插件]发送好友消息
                        case 54: {
                            SendFriendMessagePack pack = (SendFriendMessagePack) task.pack;
                            BotSendMessage.sendFriendMessage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message, pack.ids);
                            break;
                        }
                        //55 [插件]获取群列表
                        case 55: {
                            GetPack pack = (GetPack) task.pack;
                            List<GroupInfo> data = BotGetData.getGroups(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ);
                            if (data == null)
                                break;
                            ReListGroupPack pack31 = new ReListGroupPack();
                            pack31.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack31.groups = data;
                            pack31.uuid = pack.uuid;
                            if (socket.send(pack31, 55))
                                close();
                            break;
                        }
                        //56 [插件]获取好友列表
                        case 56: {
                            GetPack pack = (GetPack) task.pack;
                            List<ReFriendInfoPack> data = BotGetData.getFriends(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ);
                            if (data == null)
                                break;
                            ReListFriendPack pack1 = new ReListFriendPack();
                            pack1.friends = data;
                            pack1.uuid = pack.uuid;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (socket.send(pack1, 56))
                                close();
                            break;
                        }
                        //57 [插件]获取群成员
                        case 57: {
                            GroupGetMemberInfoPack pack = (GroupGetMemberInfoPack) task.pack;
                            List<ReMemberInfoPack> data = BotGetData.getMembers(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fast);
                            if (data == null)
                                break;
                            ReListMemberPack pack1 = new ReListMemberPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.members = data;
                            pack1.id = pack.id;
                            pack1.uuid = pack.uuid;
                            if (socket.send(pack1, 57))
                                close();
                            break;
                        }
                        //58 [插件]获取群设置
                        case 58: {
                            GroupGetSettingPack pack = (GroupGetSettingPack) task.pack;
                            GroupSettings data = BotGetData.getGroupInfo(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                break;
                            ReGroupSettingPack pack1 = new ReGroupSettingPack();
                            pack1.setting = data;
                            pack1.id = pack.id;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.uuid = pack.uuid;
                            if (socket.send(pack1, 58))
                                close();
                            break;
                        }
                        //59 [插件]回应事件
                        case 59: {
                            EventCallPack pack = (EventCallPack) task.pack;
                            EventCall.doEvent(runQQ == 0 ? pack.qq : runQQ, pack.eventid, pack.dofun, pack.arg);
                            break;
                        }
                        //61 [插件]发送图片到群
                        case 61: {
                            SendGroupImagePack pack = (SendGroupImagePack) task.pack;
                            BotSendImage.sendGroupImage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, pack.ids);
                            break;
                        }
                        //62 [插件]发送图片到私聊
                        case 62: {
                            SendGroupPrivateImagePack pack = (SendGroupPrivateImagePack) task.pack;
                            BotSendImage.sendGroupPrivateImage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.data, pack.ids);
                            break;
                        }
                        //63 [插件]发送图片到朋友
                        case 63: {
                            SendFriendImagePack pack = (SendFriendImagePack) task.pack;
                            BotSendImage.sendFriendImage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, null);
                            break;
                        }
                        //64 [插件]删除群员
                        case 64: {
                            GroupKickMemberPack pack = (GroupKickMemberPack) task.pack;
                            BotGroupDo.deleteGroupMember(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.black);
                            break;
                        }
                        //65 [插件]禁言群员
                        case 65: {
                            GroupMuteMemberPack pack = (GroupMuteMemberPack) task.pack;
                            BotGroupDo.muteGroupMember(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.time);
                            break;
                        }
                        //66 [插件]解除禁言
                        case 66: {
                            GroupUnmuteMemberPack pack = (GroupUnmuteMemberPack) task.pack;
                            BotGroupDo.unmuteGroupMember(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //67 [插件]开启全员禁言
                        case 67: {
                            GroupMuteAllPack pack = (GroupMuteAllPack) task.pack;
                            BotGroupDo.groupMuteAll(this, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //68 [插件]关闭全员禁言
                        case 68: {
                            GroupUnmuteAllPack pack = (GroupUnmuteAllPack) task.pack;
                            BotGroupDo.groupUnmuteAll(this, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //69 [插件]设置群名片
                        case 69: {
                            GroupSetMemberCardPack pack = (GroupSetMemberCardPack) task.pack;
                            BotGroupDo.setGroupMemberCard(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.card);
                            break;
                        }
                        //70 [插件]设置群名
                        case 70: {
                            GroupSetNamePack pack = (GroupSetNamePack) task.pack;
                            BotGroupDo.setGroupName(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.name);
                            break;
                        }
                        //71 [插件]撤回消息
                        case 71: {
                            ReCallMessagePack pack = (ReCallMessagePack) task.pack;
                            BotStart.reCall(runQQ == 0 ? pack.qq : runQQ, pack.ids1, pack.ids2, pack.kind);
                            break;
                        }
                        //74 [插件]发送语音到群
                        case 74: {
                            SendGroupSoundPack pack = (SendGroupSoundPack) task.pack;
                            BotSendSound.sendGroupSound(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, pack.ids);
                            break;
                        }
                        //75 [插件]从文件加载图片发送到群
                        case 75: {
                            SendGroupImageFilePack pack = (SendGroupImageFilePack) task.pack;
                            BotSendImage.sendGroupImageFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //76 [插件]从文件加载图片发送到群私聊
                        case 76: {
                            SendGroupPrivateImageFilePack pack = (SendGroupPrivateImageFilePack) task.pack;
                            BotSendImage.sendGroupPrivateImageFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.file, pack.ids);
                            break;
                        }
                        //77 [插件]从文件加载图片发送到朋友
                        case 77: {
                            SendFriendImageFilePack pack = (SendFriendImageFilePack) task.pack;
                            BotSendImage.sendFriendImageFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //78 [插件]从文件加载语音发送到群
                        case 78: {
                            SendGroupSoundFilePack pack = (SendGroupSoundFilePack) task.pack;
                            BotSendSound.sendGroupSoundFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //83 [插件]发送私聊戳一戳
                        case 83: {
                            SendFriendNudgePack pack = (SendFriendNudgePack) task.pack;
                            BotSendNudge.sendFriendNudge(this, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //84 [插件]发送群戳一戳
                        case 84: {
                            SendGroupMemberNudgePack pack = (SendGroupMemberNudgePack) task.pack;
                            BotSendNudge.sendGroupNudge(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //90 [插件]获取图片Url
                        case 90: {
                            GetImageUrlPack pack = (GetImageUrlPack) task.pack;
                            String data4 = BotGetData.getImg(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ, pack.uuid);
                            if (data4 == null)
                                break;
                            ReGetImageUrlPack pack1 = new ReGetImageUrlPack();
                            pack1.uuid = pack.uuid;
                            pack1.url = data4;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (socket.send(pack1, 90))
                                close();
                            break;
                        }
                        //91 [插件]获取群成员信息
                        case 91: {
                            GetMemberInfoPack pack = (GetMemberInfoPack) task.pack;
                            ReMemberInfoPack pack1 = BotGetData.getMemberInfo(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            if (pack1 == null)
                                break;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.id = pack.id;
                            pack1.fid = pack.fid;
                            pack1.uuid = pack.uuid;
                            if (socket.send(pack1, 91))
                                close();
                            break;
                        }
                        //92 [插件]获取朋友信息
                        case 92: {
                            GetFriendInfoPack pack = (GetFriendInfoPack) task.pack;
                            ReFriendInfoPack pack1 = BotGetData.getFriend(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (pack1 == null)
                                break;
                            pack1.uuid = pack.uuid;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (socket.send(pack1, 92))
                                close();
                            break;
                        }
                        //93 [插件]发送音乐分享
                        case 93: {
                            SendMusicSharePack pack = (SendMusicSharePack) task.pack;
                            if (pack.type1 == 0) {
                                BotSendMusicShare.sendFriendMusicShare(this, runQQ == 0 ? pack.qq : runQQ, pack.id,
                                        pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl,
                                        pack.ids);
                            } else if (pack.type1 == 1) {
                                BotSendMusicShare.sendMusicShareGroup(this, runQQ == 0 ? pack.qq : runQQ, pack.id,
                                        pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl,
                                        pack.ids);
                            } else if (pack.type1 == 2) {
                                BotSendMusicShare.sendMusicShareMember(this, runQQ == 0 ? pack.qq : runQQ, pack.id,
                                        pack.fid, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl,
                                        pack.musicUrl, pack.ids);
                            }
                            break;
                        }
                        //94 [插件]设置群精华消息
                        case 94: {
                            GroupSetEssenceMessagePack pack = (GroupSetEssenceMessagePack) task.pack;
                            BotGroupDo.setEssenceMessage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.ids1, pack.ids2);
                            break;
                        }
                        //95 [插件]消息队列
                        case 95: {
                            addBuff((MessageBuffPack) task.pack);
                            break;
                        }
                        //96 [插件]发送朋友骰子
                        case 96: {
                            SendFriendDicePack pack = (SendFriendDicePack) task.pack;
                            BotSendDice.sendFriendDice(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                        }
                        //97 [插件]发送群骰子
                        case 97: {
                            SendGroupDicePack pack = (SendGroupDicePack) task.pack;
                            BotSendDice.sendGroupDice(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                        }
                        //98 [插件]发送群私聊骰子
                        case 98: {
                            SendGroupPrivateDicePack pack = (SendGroupPrivateDicePack) task.pack;
                            BotSendDice.sendGroupPrivateDice(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dice);
                        }
                        //99 [插件]上传群文件
                        case 99: {
                            GroupAddFilePack pack = (GroupAddFilePack) task.pack;
                            BotGroupFile.addFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.name);
                            break;
                        }
                        //100 [插件]删除群文件
                        case 100: {
                            GroupDeleteFilePack pack = (GroupDeleteFilePack) task.pack;
                            BotGroupFile.deleteFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //101 [插件]获取群文件
                        case 101: {
                            GroupGetFilesPack pack = (GroupGetFilesPack) task.pack;
                            List<GroupFileInfo> data = BotGroupFile.getFiles(this, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                return;
                            ReGroupFilesPack pack1 = new ReGroupFilesPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.id = pack.id;
                            pack1.files = data;
                            pack1.uuid = pack.uuid;
                            if (socket.send(pack1, 101))
                                close();
                            break;
                        }
                        //102 [插件]移动群文件
                        case 102: {
                            GroupMoveFilePack pack = (GroupMoveFilePack) task.pack;
                            BotGroupFile.moveFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dir);
                            break;
                        }
                        //103 [插件]重命名群文件
                        case 103: {
                            GroupRenameFilePack pack = (GroupRenameFilePack) task.pack;
                            BotGroupFile.renameFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.now);
                            break;
                        }
                        //104 [插件]创新群文件文件夹
                        case 104: {
                            GroupAddDirPack pack = (GroupAddDirPack) task.pack;
                            BotGroupFile.addGroupDir(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //105 [插件]删除群文件文件夹
                        case 105: {
                            GroupDeleteDirPack pack = (GroupDeleteDirPack) task.pack;
                            BotGroupFile.removeGroupDir(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //106 [插件]重命名群文件文件夹
                        case 106: {
                            GroupRenameDirPack pack = (GroupRenameDirPack) task.pack;
                            BotGroupFile.renameGroupDir(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.old, pack.now);
                            break;
                        }
                        //107 [插件]下载群文件到指定位置
                        case 107: {
                            GroupDownloadFilePack pack = (GroupDownloadFilePack) task.pack;
                            BotGroupFile.downloadGroupFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dir);
                            break;
                        }
                        //108 [插件]设置取消管理员
                        case 108: {
                            GroupSetAdminPack pack = (GroupSetAdminPack) task.pack;
                            BotGroupDo.setAdmin(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.type);
                            break;
                        }
                        //109 [插件]获取群公告
                        case 109: {
                            GroupGetAnnouncementsPack pack = (GroupGetAnnouncementsPack) task.pack;
                            List<OnlineAnnouncement> data = BotGroupDo.getAnnouncements(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.uuid);
                            if (data == null)
                                return;
                            List<GroupAnnouncement> list = new ArrayList<>();
                            for (OnlineAnnouncement item : data) {
                                GroupAnnouncement item1 = new GroupAnnouncement();
                                item1.senderId = item.getSenderId();
                                item1.fid = item.getFid();
                                item1.allConfirmed = item.getAllConfirmed();
                                item1.confirmedMembersCount = item.getConfirmedMembersCount();
                                item1.publicationTime = item.getPublicationTime();
                                item1.content = item.getContent();
                                if (item.getParameters().getImage() != null)
                                    item1.image = item.getParameters().getImage().getId();
                                item1.sendToNewMember = item.getParameters().getSendToNewMember();
                                item1.isPinned = item.getParameters().isPinned();
                                item1.showEditCard = item.getParameters().getShowEditCard();
                                item1.showPopup = item.getParameters().getShowPopup();
                                item1.requireConfirmation = item.getParameters().getRequireConfirmation();
                                list.add(item1);
                            }
                            ReGroupAnnouncementsPack pack1 = new ReGroupAnnouncementsPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.id = pack.id;
                            pack1.list = list;
                            pack1.uuid = pack.uuid;
                            if (socket.send(pack1, 109))
                                close();
                            break;
                        }
                        //110 [插件]设置群公告
                        case 110: {
                            GroupAddAnnouncementPack pack = (GroupAddAnnouncementPack) task.pack;
                            BotGroupDo.setAnnouncement(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.imageFile, pack.sendToNewMember, pack.isPinned, pack.showEditCard, pack.showPopup, pack.requireConfirmation, pack.text);
                            break;
                        }
                        //111 [插件]删除群公告
                        case 111: {
                            GroupDeleteAnnouncementPack pack = (GroupDeleteAnnouncementPack) task.pack;
                            BotGroupDo.deleteAnnouncement(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //112 [插件]发送好友语言文件
                        case 112: {
                            SendFriendSoundFilePack pack = (SendFriendSoundFilePack) task.pack;
                            BotSendSound.sendFriendFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //114 [插件]设置允许群员邀请好友入群的状态
                        case 114: {
                            GroupSetAllowMemberInvitePack pack = (GroupSetAllowMemberInvitePack) task.pack;
                            BotGroupDo.setAllowMemberInvite(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.enable);
                            break;
                        }
                        //115 [插件]设置允许匿名聊天
                        case 115: {
                            GroupSetAnonymousChatEnabledPack pack = (GroupSetAnonymousChatEnabledPack) task.pack;
                            BotGroupDo.setAnonymousChatEnabled(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.enable);
                            break;
                        }
                        //117 [插件]发送陌生人消息
                        case 117: {
                            SendStrangerMessagePack pack = (SendStrangerMessagePack) task.pack;
                            BotSendMessage.sendStrangerMessage(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message, pack.ids);
                            break;
                        }
                        //118 [插件]从文件加载图片发送到陌生人
                        case 118: {
                            SendStrangerImageFilePack pack = (SendStrangerImageFilePack) task.pack;
                            BotSendImage.sendStrangerImageFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //119 [插件]发送陌生人骰子
                        case 119: {
                            SendStrangerDicePack pack = (SendStrangerDicePack) task.pack;
                            BotSendDice.sendStrangerDice(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                            break;
                        }
                        //120 [插件]发送陌生人戳一戳
                        case 120: {
                            SendStrangerNudgePack pack = (SendStrangerNudgePack) task.pack;
                            BotSendNudge.sendStrangerNudge(this, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //121 [插件]从文件加载语音发送到陌生人
                        case 121: {
                            SendStrangerSoundFilePack pack = (SendStrangerSoundFilePack) task.pack;
                            BotSendSound.sendStrangerFile(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //126 [插件]发送好友语音
                        case 126: {
                            SendFriendSoundPack pack = (SendFriendSoundPack) task.pack;
                            BotSendSound.sendFriend(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, pack.ids);
                            break;
                        }
                        //128 [插件]获取好友分组信息
                        case 128: {
                            GetFriendGroupPack pack = (GetFriendGroupPack) task.pack;
                            FriendGroupInfo info = BotGetData.getFriendGroup(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            ReFriendGroupPack pack1 = new ReFriendGroupPack();
                            pack1.uuid = pack.uuid;
                            pack1.qq = pack.qq;
                            pack1.info = info;
                            if (socket.send(pack1, 128))
                                close();
                            break;
                        }
                        //129 [插件]获取所有好友分组信息
                        case 129: {
                            GetPack pack = (GetPack) task.pack;
                            List<FriendGroupInfo> list = BotGetData.getFriendGroups(this, pack.uuid, runQQ == 0 ? pack.qq : runQQ);
                            ReListFriendGroupPack pack1 = new ReListFriendGroupPack();
                            pack1.uuid = pack.uuid;
                            pack1.qq = pack.qq;
                            pack1.infos = list;
                            if (socket.send(pack1, 129))
                                close();
                            break;
                        }
                        //130 [插件]创建好友分组
                        case 130: {
                            FriendGroupCreatePack pack = (FriendGroupCreatePack) task.pack;
                            BotFriendGroup.create(this, runQQ == 0 ? pack.qq : runQQ, pack.name);
                            break;
                        }
                        //131 [插件]修改好友分组名
                        case 131: {
                            FriendGroupRenamePack pack = (FriendGroupRenamePack) task.pack;
                            BotFriendGroup.rename(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.name);
                            break;
                        }
                        //132 [插件]移动好友到分组
                        case 132: {
                            FriendGroupMovePack pack = (FriendGroupMovePack) task.pack;
                            BotFriendGroup.move(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //133 [插件]删除好友分组
                        case 133: {
                            FriendGroupDeletePack pack = (FriendGroupDeletePack) task.pack;
                            BotFriendGroup.delete(this, runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //134 [插件]修改群成员头衔
                        case 134: {
                            GroupMemberEditSpecialTitlePack pack = (GroupMemberEditSpecialTitlePack) task.pack;
                            BotGroupDo.setMemberSpecialTitle(this, runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.name);
                            break;
                        }
                        default: {
                            ColorMiraiMain.logger.error("不知道的包" + task.index);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                if (!isRun)
                    break;
                ColorMiraiMain.logger.error("数据处理发生异常", e);
                close();
            }
        }
    }

    public void addBuff(MessageBuffPack temp) throws IOException {
        if (temp == null)
            return;
        temp.qq = runQQ == 0 ? temp.qq : runQQ;
        Bot bot = BotStart.getBots().get(temp.qq);
        if (bot == null) {
            ColorMiraiMain.logger.warn("不存在QQ:" + temp.qq);
            return;
        }
        BuffObj item = null;
        if (temp.type == 0) {
            if (temp.id == 0) {
                return;
            }
            item = messageBuff.get("F:" + temp.id);
            if (item == null) {
                item = new BuffObj();
                Friend contact = bot.getFriend(temp.id);
                if (contact == null) {
                    ColorMiraiMain.logger.warn("QQ:" + temp.qq + " 不存在朋友:" + temp.id);
                    return;
                }
                item.contact = contact;
                item.message = MessageUtils.newChain();
            }
        }
        if (temp.type == 1) {
            if (temp.id == 0) {
                return;
            }
            item = messageBuff.get("G:" + temp.id);
            if (item == null) {
                item = new BuffObj();
                Group contact = bot.getGroup(temp.id);
                if (contact == null) {
                    ColorMiraiMain.logger.warn("QQ:" + temp.qq + " 不存在群:" + temp.id);
                    return;
                }
                item.contact = contact;
                item.message = MessageUtils.newChain();
            }
        }
        if (temp.type == 2) {
            if (temp.id == 0 || temp.fid == 0) {
                return;
            }
            item = messageBuff.get("G:" + temp.id + "F:" + temp.id);
            if (item == null) {
                item = new BuffObj();
                Group group = bot.getGroup(temp.id);
                if (group == null) {
                    ColorMiraiMain.logger.warn("QQ:" + temp.qq + " 不存在群:" + temp.id);
                    return;
                }
                NormalMember contact = group.get(temp.fid);
                if (contact == null) {
                    ColorMiraiMain.logger.warn("QQ:" + temp.qq + " 群:" + temp.id + " 不存在成员:" + temp.fid);
                    return;
                }
                item.contact = contact;
                item.message = MessageUtils.newChain();
            }
        }
        if (item == null) {
            return;
        }
        for (String item1 : temp.text) {
            if (item1.startsWith("[mirai:")) {
                item.message = item.message.plus(MiraiCode.deserializeMiraiCode(item1));
            } else {
                item.message = item.message.plus(item1);
            }
        }
        if (temp.imgurl != null && !temp.imgurl.isEmpty()) {
            Image image = BotUpload.upImage(bot, temp.imgurl);
            item.message = item.message.plus(image);
        }
        if (temp.imgData != null && temp.imgData.length != 0) {
            Image image = BotUpload.upImage(bot, temp.imgData);
            item.message = item.message.plus(image);
        }
        if (temp.send) {
            item.contact.sendMessage(item.message);
        } else {
            if (temp.type == 0) {
                messageBuff.put("F:" + temp.id, item);
            } else if (temp.type == 1) {
                messageBuff.put("G:" + temp.id, item);
            } else if (temp.type == 2) {
                messageBuff.put("G:" + temp.id + "F:" + temp.id, item);
            }
        }
    }

    public void callEvent(SendPackObj task, Object pack, int index) {
        if (runQQ != 0 && task.runqq != runQQ)
            return;
        if (groups.size() != 0 && task.group != 0 && !groups.contains(task.group))
            return;
        if (qqList.size() != 0 && task.qq != 0 && !qqList.contains(task.qq))
            return;
        if (allEvent || events.contains(task.index) || task.index == 60) {
            if (socket.send(pack, index))
                close();
        }
    }

    public void close() {
        try {
            isRun = false;
            socket.close();
            semaphore.release();
            PluginUtils.removePlugin(name);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件断开失败", e);
        }
    }

    public void sendPluginMessage(long qq, String uuid, String msg) {
        ReMessagePack pack = new ReMessagePack();
        pack.qq = qq;
        pack.uuid = uuid;
        pack.msg = msg;
        addPack(new PluginPack(pack, 0));
    }
}
