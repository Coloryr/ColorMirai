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
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.contact.announcement.OnlineAnnouncement;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageUtils;

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
    private final Semaphore semaphore = new Semaphore(10, false);

    private String name;
    private long runQQ;
    private List<Integer> events = null;
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
            return "无";
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
                            BotSendMessage.sendGroupMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message, pack.ids);
                            break;
                        }
                        //53 [插件]发送私聊消息
                        case 53: {
                            SendGroupPrivateMessagePack pack = (SendGroupPrivateMessagePack) task.pack;
                            BotSendMessage.sendGroupPrivateMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.message);
                            break;
                        }
                        //54 [插件]发送好友消息
                        case 54: {
                            SendFriendMessagePack pack = (SendFriendMessagePack) task.pack;
                            BotSendMessage.sendFriendMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message, pack.ids);
                            break;
                        }
                        //55 [插件]获取群列表
                        case 55: {
                            GetPack pack = (GetPack) task.pack;
                            List<GroupInfo> data = BotGetData.getGroups(runQQ == 0 ? pack.qq : runQQ);
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
                            List<ReFriendInfoPack> data = BotGetData.getFriends(runQQ == 0 ? pack.qq : runQQ);
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
                            List<ReMemberInfoPack> data = BotGetData.getMembers(runQQ == 0 ? pack.qq : runQQ, pack.id);
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
                            GroupSettings data = BotGetData.getGroupInfo(runQQ == 0 ? pack.qq : runQQ, pack.id);
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
                            BotSendImage.sendGroupImage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, pack.ids);
                            break;
                        }
                        //62 [插件]发送图片到私聊
                        case 62: {
                            SendGroupPrivateImagePack pack = (SendGroupPrivateImagePack) task.pack;
                            BotSendImage.sendGroupPrivateImage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.data);
                            break;
                        }
                        //63 [插件]发送图片到朋友
                        case 63: {
                            SendFriendImagePack pack = (SendFriendImagePack) task.pack;
                            BotSendImage.sendFriendImage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, null);
                            break;
                        }
                        //64 [插件]删除群员
                        case 64: {
                            GroupKickMemberPack pack = (GroupKickMemberPack) task.pack;
                            BotGroupDo.deleteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.black);
                            break;
                        }
                        //65 [插件]禁言群员
                        case 65: {
                            GroupMuteMemberPack pack = (GroupMuteMemberPack) task.pack;
                            BotGroupDo.muteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.time);
                            break;
                        }
                        //66 [插件]解除禁言
                        case 66: {
                            GroupUnmuteMemberPack pack = (GroupUnmuteMemberPack) task.pack;
                            BotGroupDo.unmuteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //67 [插件]开启全员禁言
                        case 67: {
                            GroupMuteAllPack pack = (GroupMuteAllPack) task.pack;
                            BotGroupDo.groupMuteAll(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //68 [插件]关闭全员禁言
                        case 68: {
                            GroupUnmuteAllPack pack = (GroupUnmuteAllPack) task.pack;
                            BotGroupDo.groupUnmuteAll(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //69 [插件]设置群名片
                        case 69: {
                            GroupSetMemberCardPack pack = (GroupSetMemberCardPack) task.pack;
                            BotGroupDo.setGroupMemberCard(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.card);
                            break;
                        }
                        //70 [插件]设置群名
                        case 70: {
                            GroupSetNamePack pack = (GroupSetNamePack) task.pack;
                            BotGroupDo.setGroupName(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.name);
                            break;
                        }
                        //71 [插件]撤回消息
                        case 71: {
                            ReCallMessagePack pack = (ReCallMessagePack) task.pack;
                            BotStart.reCall(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //74 [插件]发送语音到群
                        case 74: {
                            SendGroupSoundPack pack = (SendGroupSoundPack) task.pack;
                            BotSendSound.sendGroupSound(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, pack.ids);
                            break;
                        }
                        //75 [插件]从文件加载图片发送到群
                        case 75: {
                            SendGroupImageFilePack pack = (SendGroupImageFilePack) task.pack;
                            BotSendImage.sendGroupImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //76 [插件]从文件加载图片发送到群私聊
                        case 76: {
                            SendGroupPrivateImageFilePack pack = (SendGroupPrivateImageFilePack) task.pack;
                            BotSendImage.sendGroupPrivateImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.file);
                            break;
                        }
                        //77 [插件]从文件加载图片发送到朋友
                        case 77: {
                            SendFriendImageFilePack pack = (SendFriendImageFilePack) task.pack;
                            BotSendImage.sendFriendImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //78 [插件]从文件加载语音发送到群
                        case 78: {
                            SendGroupSoundFilePack pack = (SendGroupSoundFilePack) task.pack;
                            BotSendSound.sendGroupSoundFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //83 [插件]发送私聊戳一戳
                        case 83: {
                            SendFriendNudgePack pack = (SendFriendNudgePack) task.pack;
                            BotSendNudge.sendFriendNudge(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //84 [插件]发送群戳一戳
                        case 84: {
                            SendGroupMemberNudgePack pack = (SendGroupMemberNudgePack) task.pack;
                            BotSendNudge.sendGroupNudge(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //90 [插件]获取图片Url
                        case 90: {
                            GetImageUrlPack pack = (GetImageUrlPack) task.pack;
                            String data4 = BotGetData.getImg(runQQ == 0 ? pack.qq : runQQ, pack.uuid);
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
                            ReMemberInfoPack pack1 = BotGetData.getMemberInfo(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
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
                            ReFriendInfoPack pack1 = BotGetData.getFriend(runQQ == 0 ? pack.qq : runQQ, pack.id);
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
                                BotSendMusicShare.sendFriendMusicShare(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            } else if (pack.type1 == 1) {
                                BotSendMusicShare.sendMusicShareGroup(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            } else if (pack.type1 == 2) {
                                BotSendMusicShare.sendMusicShareMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            }
                            break;
                        }
                        //94 [插件]设置群精华消息
                        case 94: {
                            GroupSetEssenceMessagePack pack = (GroupSetEssenceMessagePack) task.pack;
                            BotGroupDo.setEssenceMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.mid);
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
                            BotSendDice.sendFriendDice(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                        }
                        //97 [插件]发送群骰子
                        case 97: {
                            SendGroupDicePack pack = (SendGroupDicePack) task.pack;
                            BotSendDice.sendGroupDice(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                        }
                        //98 [插件]发送群私聊骰子
                        case 98: {
                            SendGroupPrivateDicePack pack = (SendGroupPrivateDicePack) task.pack;
                            BotSendDice.sendGroupPrivateDice(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dice);
                        }
                        //99 [插件]上传群文件
                        case 99: {
                            GroupAddFilePack pack = (GroupAddFilePack) task.pack;
                            BotGroupFile.addFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.name);
                            break;
                        }
                        //100 [插件]删除群文件
                        case 100: {
                            GroupDeleteFilePack pack = (GroupDeleteFilePack) task.pack;
                            BotGroupFile.deleteFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //101 [插件]获取群文件
                        case 101: {
                            GroupGetFilesPack pack = (GroupGetFilesPack) task.pack;
                            List<GroupFileInfo> data = BotGroupFile.getFiles(runQQ == 0 ? pack.qq : runQQ, pack.id);
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
                            BotGroupFile.moveFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dir);
                            break;
                        }
                        //103 [插件]重命名群文件
                        case 103: {
                            GroupRenameFilePack pack = (GroupRenameFilePack) task.pack;
                            BotGroupFile.renameFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.now);
                            break;
                        }
                        //104 [插件]创新群文件文件夹
                        case 104: {
                            GroupAddDirPack pack = (GroupAddDirPack) task.pack;
                            BotGroupFile.addGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //105 [插件]删除群文件文件夹
                        case 105: {
                            GroupDeleteDirPack pack = (GroupDeleteDirPack) task.pack;
                            BotGroupFile.removeGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //106 [插件]重命名群文件文件夹
                        case 106: {
                            GroupRenameDirPack pack = (GroupRenameDirPack) task.pack;
                            BotGroupFile.renameGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.old, pack.now);
                            break;
                        }
                        //107 [插件]下载群文件到指定位置
                        case 107: {
                            GroupDownloadFilePack pack = (GroupDownloadFilePack) task.pack;
                            BotGroupFile.downloadGroupFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dir);
                            break;
                        }
                        //108 [插件]设置取消管理员
                        case 108: {
                            GroupSetAdminPack pack = (GroupSetAdminPack) task.pack;
                            BotGroupDo.setAdmin(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.type);
                            break;
                        }
                        //109 [插件]获取群公告
                        case 109: {
                            GroupGetAnnouncementsPack pack = (GroupGetAnnouncementsPack) task.pack;
                            List<OnlineAnnouncement> data = BotGroupDo.getAnnouncements(runQQ == 0 ? pack.qq : runQQ, pack.id);
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
                            BotGroupDo.setAnnouncement(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.imageFile, pack.sendToNewMember, pack.isPinned, pack.showEditCard, pack.showPopup, pack.requireConfirmation, pack.text);
                            break;
                        }
                        //111 [插件]删除群公告
                        case 111: {
                            GroupDeleteAnnouncementPack pack = (GroupDeleteAnnouncementPack) task.pack;
                            BotGroupDo.deleteAnnouncement(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //112 [插件]发送好友语言文件
                        case 112: {
                            SendFriendSoundFilePack pack = (SendFriendSoundFilePack) task.pack;
                            BotSendSound.sendFriendFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //114 [插件]设置允许群员邀请好友入群的状态
                        case 114: {
                            GroupSetAllowMemberInvitePack pack = (GroupSetAllowMemberInvitePack) task.pack;
                            BotGroupDo.setAllowMemberInvite(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.enable);
                            break;
                        }
                        //115 [插件]设置允许匿名聊天
                        case 115: {
                            GroupSetAnonymousChatEnabledPack pack = (GroupSetAnonymousChatEnabledPack) task.pack;
                            BotGroupDo.setAnonymousChatEnabled(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.enable);
                            break;
                        }
                        //117 [插件]发送陌生人消息
                        case 117: {
                            SendStrangerMessagePack pack = (SendStrangerMessagePack) task.pack;
                            BotSendMessage.sendStrangerMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message);
                            break;
                        }
                        //118 [插件]从文件加载图片发送到陌生人
                        case 118: {
                            SendStrangerImageFilePack pack = (SendStrangerImageFilePack) task.pack;
                            BotSendImage.sendStrangerImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //119 [插件]发送陌生人骰子
                        case 119: {
                            SendStrangerDicePack pack = (SendStrangerDicePack) task.pack;
                            BotSendDice.sendStrangerDice(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                            break;
                        }
                        //120 [插件]发送陌生人戳一戳
                        case 120: {
                            SendStrangerNudgePack pack = (SendStrangerNudgePack) task.pack;
                            BotSendNudge.sendStrangerNudge(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //121 [插件]从文件加载语音发送到陌生人
                        case 121: {
                            SendStrangerSoundFilePack pack = (SendStrangerSoundFilePack) task.pack;
                            BotSendSound.sendStrangerFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //126 [插件]发送好友语音
                        case 126: {
                            SendFriendSoundPack pack = (SendFriendSoundPack) task.pack;
                            BotSendSound.sendFriend(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.data, pack.ids);
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

    public void addBuff(MessageBuffPack temp) {
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
            if (image != null)
                item.message = item.message.plus(image);
        }
        if (temp.imgData != null && temp.imgData.length != 0) {
            Image image = BotUpload.upImage(bot, temp.imgData);
            item.message = item.message.plus(image);
        }
        if (temp.send) {
            MessageReceipt<Contact> message = item.contact.sendMessage(item.message);
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = temp.qq;
            int[] temp1 = obj.source.getIds();
            if (temp1.length != 0 && temp1[0] != -1) {
                obj.id = temp1[0];
            }
            BotStart.addMessage(temp.qq, obj.id, obj);
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
        if (events.contains((int) task.index) || task.index == 60) {
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
}
