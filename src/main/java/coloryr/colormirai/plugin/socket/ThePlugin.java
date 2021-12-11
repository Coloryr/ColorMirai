package coloryr.colormirai.plugin.socket;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.socket.obj.BuffObj;
import coloryr.colormirai.plugin.socket.obj.RePackObj;
import coloryr.colormirai.plugin.socket.obj.SendPackObj;
import coloryr.colormirai.plugin.socket.pack.PackDo;
import coloryr.colormirai.plugin.socket.pack.from.*;
import coloryr.colormirai.plugin.socket.pack.re.*;
import coloryr.colormirai.robot.*;
import coloryr.colormirai.robot.event.EventCall;
import com.alibaba.fastjson.JSON;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThePlugin {
    private final PluginSocket socket;
    private final List<RePackObj> tasks = new CopyOnWriteArrayList<>();
    private final List<Long> groups = new CopyOnWriteArrayList<>();
    private final List<Long> qqList = new CopyOnWriteArrayList<>();
    private final Map<String, BuffObj> messageBuff = new ConcurrentHashMap<>();

    private final Thread doRead;

    private String name;
    private long runQQ;
    private List<Integer> events = null;
    private boolean isRun;

    public ThePlugin(PluginSocket Socket) {
        this.socket = Socket;
        doRead = new Thread(this::startRead);
        new Thread(this::start).start();
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

    private void startRead() {
        while (isRun) {
            try {
                if (!tasks.isEmpty()) {
                    RePackObj task = tasks.remove(0);
                    switch (task.index) {
                        //52 [插件]发送群消息
                        case 52: {
                            SendGroupMessagePack pack = JSON.parseObject(task.data, SendGroupMessagePack.class);
                            BotSendMessage.sendGroupMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message);
                            break;
                        }
                        //53 [插件]发送私聊消息
                        case 53: {
                            SendGroupPrivateMessagePack pack = JSON.parseObject(task.data, SendGroupPrivateMessagePack.class);
                            BotSendMessage.sendGroupPrivateMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.message);
                            break;
                        }
                        //54 [插件]发送好友消息
                        case 54: {
                            SendFriendMessagePack pack = JSON.parseObject(task.data, SendFriendMessagePack.class);
                            BotSendMessage.sendFriendMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message);
                            break;
                        }
                        //55 [插件]获取群列表
                        case 55: {
                            GetPack pack = JSON.parseObject(task.data, GetPack.class);
                            List<GroupInfo> data = BotGetData.getGroups(runQQ == 0 ? pack.qq : runQQ);
                            if (data == null)
                                break;
                            ListGroupPack pack31 = new ListGroupPack();
                            pack31.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack31.groups = data;
                            if (socket.send(PackDo.buildPack(pack31, 55)))
                                close();
                            break;
                        }
                        //56 [插件]获取好友列表
                        case 56: {
                            GetPack pack = JSON.parseObject(task.data, GetPack.class);
                            List<FriendInfoPack> data = BotGetData.getFriends(runQQ == 0 ? pack.qq : runQQ);
                            if (data == null)
                                break;
                            ListFriendPack pack1 = new ListFriendPack();
                            pack1.friends = data;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (socket.send(PackDo.buildPack(pack1, 56)))
                                close();
                            break;
                        }
                        //57 [插件]获取群成员
                        case 57: {
                            GroupGetMemberInfoPack pack = JSON.parseObject(task.data, GroupGetMemberInfoPack.class);
                            List<MemberInfoPack> data = BotGetData.getMembers(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                break;
                            ListMemberPack pack1 = new ListMemberPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.members = data;
                            if (socket.send(PackDo.buildPack(pack1, 57)))
                                close();
                            break;
                        }
                        //58 [插件]获取群设置
                        case 58: {
                            GroupGetSettingPack pack = JSON.parseObject(task.data, GroupGetSettingPack.class);
                            GroupSettings data = BotGetData.getGroupInfo(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                break;
                            GroupSettingPack pack1 = new GroupSettingPack();
                            pack1.setting = data;
                            pack1.id = pack.id;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (socket.send(PackDo.buildPack(data, 58)))
                                close();
                            break;
                        }
                        //59 [插件]回应事件
                        case 59: {
                            EventCallPack pack = JSON.parseObject(task.data, EventCallPack.class);
                            EventCall.doEvent(runQQ == 0 ? pack.qq : runQQ, pack.eventid, pack.dofun, pack.arg);
                            break;
                        }
                        //61 [插件]发送图片到群
                        case 61: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(task.data);
                            if (formdata.containsKey("id") && formdata.containsKey("img") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    BotSendImage.sendGroupImage(runQQ == 0 ? qq : runQQ, id, formdata.get("img"));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //62 [插件]发送图片到私聊
                        case 62: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(task.data);
                            if (formdata.containsKey("id") && formdata.containsKey("fid") && formdata.containsKey("img") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long fid = Long.parseLong(formdata.get("fid"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    BotSendImage.sendGroupPrivateImage(runQQ == 0 ? qq : runQQ, id, fid, formdata.get("img"));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //63 [插件]发送图片到朋友
                        case 63: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(task.data);
                            if (formdata.containsKey("id") && formdata.containsKey("img")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    BotSendImage.sendFriendImage(runQQ == 0 ? qq : runQQ, id, formdata.get("img"), null);
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //64 [插件]删除群员
                        case 64: {
                            GroupKickMemberPack pack = JSON.parseObject(task.data, GroupKickMemberPack.class);
                            BotGroupDo.deleteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.black);
                            break;
                        }
                        //65 [插件]禁言群员
                        case 65: {
                            GroupMuteMemberPack pack = JSON.parseObject(task.data, GroupMuteMemberPack.class);
                            BotGroupDo.muteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.time);
                            break;
                        }
                        //66 [插件]解除禁言
                        case 66: {
                            GroupUnmuteMemberPack pack = JSON.parseObject(task.data, GroupUnmuteMemberPack.class);
                            BotGroupDo.unmuteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //67 [插件]开启全员禁言
                        case 67: {
                            GroupMuteAllPack pack = JSON.parseObject(task.data, GroupMuteAllPack.class);
                            BotGroupDo.groupMuteAll(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //68 [插件]关闭全员禁言
                        case 68: {
                            GroupUnmuteAllPack pack = JSON.parseObject(task.data, GroupUnmuteAllPack.class);
                            BotGroupDo.groupUnmuteAll(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //69 [插件]设置群名片
                        case 69: {
                            GroupSetMemberCard pack = JSON.parseObject(task.data, GroupSetMemberCard.class);
                            BotGroupDo.setGroupMemberCard(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.card);
                            break;
                        }
                        //70 [插件]设置群名
                        case 70: {
                            GroupSetNamePack pack = JSON.parseObject(task.data, GroupSetNamePack.class);
                            BotGroupDo.setGroupName(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.name);
                            break;
                        }
                        //71 [插件]撤回消息
                        case 71: {
                            ReCallMessagePack pack = JSON.parseObject(task.data, ReCallMessagePack.class);
                            BotStart.reCall(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //74 [插件]发送语音到群
                        case 74: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(task.data);
                            if (formdata.containsKey("id") && formdata.containsKey("sound") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    BotSendSound.sendGroupSound(runQQ == 0 ? qq : runQQ, id, formdata.get("sound"));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //75 [插件]从本地文件加载图片发送到群
                        case 75: {
                            SendGroupImageFilePack pack = JSON.parseObject(task.data, SendGroupImageFilePack.class);
                            BotSendImage.sendGroupImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //76 [插件]从本地文件加载图片发送到群私聊
                        case 76: {
                            SendGroupPrivateImageFilePack pack = JSON.parseObject(task.data, SendGroupPrivateImageFilePack.class);
                            BotSendImage.sendGroupPrivateImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.file);
                            break;
                        }
                        //77 [插件]从本地文件加载图片发送到朋友
                        case 77: {
                            SendFriendImageFilePack pack = JSON.parseObject(task.data, SendFriendImageFilePack.class);
                            BotSendImage.sendFriendImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //78 [插件]从本地文件加载语音发送到群
                        case 78: {
                            SendGroupSoundFilePack pack = JSON.parseObject(task.data, SendGroupSoundFilePack.class);
                            BotSendSound.sendGroupSoundFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                            break;
                        }
                        //83 [插件]发送私聊戳一戳
                        case 83: {
                            SendFriendNudgePack pack = JSON.parseObject(task.data, SendFriendNudgePack.class);
                            BotSendNudge.sendNudge(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //84 [插件]发送群戳一戳
                        case 84: {
                            SendGroupMemberNudgePack pack = JSON.parseObject(task.data, SendGroupMemberNudgePack.class);
                            BotSendNudge.sendNudge(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //90 [插件]获取图片Url
                        case 90: {
                            GetImageUrlPack pack = JSON.parseObject(task.data, GetImageUrlPack.class);
                            String data4 = BotGetData.getImg(runQQ == 0 ? pack.qq : runQQ, pack.uuid);
                            if (data4 == null)
                                break;
                            ReImagePack pack1 = new ReImagePack();
                            pack1.uuid = pack.uuid;
                            pack1.url = data4;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (socket.send(PackDo.buildPack(pack1, 90)))
                                close();
                            break;
                        }
                        //91 [插件]获取群成员信息
                        case 91: {
                            GetMemberInfo pack = JSON.parseObject(task.data, GetMemberInfo.class);
                            MemberInfoPack pack1 = BotGetData.getMemberInfo(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            if (pack1 == null)
                                break;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.id = pack.id;
                            pack1.fid = pack.fid;
                            if (socket.send(PackDo.buildPack(pack1, 91)))
                                close();
                            break;
                        }
                        //92 [插件]获取朋友信息
                        case 92: {
                            GetFriendInfoPack pack = JSON.parseObject(task.data, GetFriendInfoPack.class);
                            FriendInfoPack pack1 = BotGetData.getFriend(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (pack1 == null)
                                break;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (socket.send(PackDo.buildPack(pack1, 92)))
                                close();
                            break;
                        }
                        //93 [插件]发送音乐分享
                        case 93: {
                            SendMusicSharePack pack = JSON.parseObject(task.data, SendMusicSharePack.class);
                            if (pack.type1 == 0) {
                                BotSendMusicShare.sendMusicShare(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            } else if (pack.type1 == 1) {
                                BotSendMusicShare.sendMusicShareGroup(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            } else if (pack.type1 == 2) {
                                BotSendMusicShare.sendMusicShareMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            }
                            break;
                        }
                        //94 [插件]设置群精华消息
                        case 94: {
                            GroupSetEssenceMessagePack pack = JSON.parseObject(task.data, GroupSetEssenceMessagePack.class);
                            BotGroupDo.setEssenceMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.mid);
                            break;
                        }
                        //95 [插件]消息队列
                        case 95: {
                            addBuff(task.data);
                            break;
                        }
                        //96 [插件]发送朋友骰子
                        case 96: {
                            SendFriendDicePack pack = JSON.parseObject(task.data, SendFriendDicePack.class);
                            BotSendDice.sendFriendDice(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                        }
                        //97 [插件]发送群骰子
                        case 97: {
                            SendGroupDicePack pack = JSON.parseObject(task.data, SendGroupDicePack.class);
                            BotSendDice.sendGroupDice(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dice);
                        }
                        //98 [插件]发送群私聊骰子
                        case 98: {
                            SendGroupPrivateDicePack pack = JSON.parseObject(task.data, SendGroupPrivateDicePack.class);
                            BotSendDice.sendGroupPrivateDice(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dice);
                        }
                        //99 [插件]上传群文件
                        case 99: {
                            GroupAddFilePack pack = JSON.parseObject(task.data, GroupAddFilePack.class);
                            BotGroupFile.addFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.name);
                            break;
                        }
                        //100 [插件]删除群文件
                        case 100: {
                            GroupDeleteFilePack pack = JSON.parseObject(task.data, GroupDeleteFilePack.class);
                            BotGroupFile.deleteFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //101 [插件]获取群文件
                        case 101: {
                            GroupGetFilesPack pack = JSON.parseObject(task.data, GroupGetFilesPack.class);
                            List<GroupFileInfo> data = BotGroupFile.getFiles(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                return;
                            GroupFilesPack pack1 = new GroupFilesPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.id = pack.id;
                            pack1.files = data;
                            if (socket.send(PackDo.buildPack(pack1, 101)))
                                close();
                            break;
                        }
                        //102 [插件]移动群文件
                        case 102: {
                            GroupMoveFilePack pack = JSON.parseObject(task.data, GroupMoveFilePack.class);
                            BotGroupFile.moveFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dir);
                            break;
                        }
                        //103 [插件]重命名群文件
                        case 103: {
                            GroupRenameFilePack pack = JSON.parseObject(task.data, GroupRenameFilePack.class);
                            BotGroupFile.renameFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.now);
                            break;
                        }
                        //104 [插件]创新群文件文件夹
                        case 104: {
                            GroupAddDirPack pack = JSON.parseObject(task.data, GroupAddDirPack.class);
                            BotGroupFile.addGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //105 [插件]删除群文件文件夹
                        case 105: {
                            GroupDeleteDirPack pack = JSON.parseObject(task.data, GroupDeleteDirPack.class);
                            BotGroupFile.removeGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //106 [插件]重命名群文件文件夹
                        case 106: {
                            GroupRenameDirPack pack = JSON.parseObject(task.data, GroupRenameDirPack.class);
                            BotGroupFile.renameGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.old, pack.now);
                            break;
                        }
                        //107 [插件]下载群文件到指定位置
                        case 107: {
                            GroupDownloadFilePack pack = JSON.parseObject(task.data, GroupDownloadFilePack.class);
                            BotGroupFile.downloadGroupFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.dir);
                            break;
                        }
                        //108 [插件]设置取消管理员
                        case 108: {
                            GroupSetAdminPack pack = JSON.parseObject(task.data, GroupSetAdminPack.class);
                            BotGroupDo.setAdmin(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.type);
                            break;
                        }
                        //109 [插件]获取群公告
                        case 109: {
                            GroupGetAnnouncementsPack pack = JSON.parseObject(task.data, GroupGetAnnouncementsPack.class);
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
                            GroupAnnouncementsPack pack1 = new GroupAnnouncementsPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.id = pack.id;
                            pack1.list = list;
                            if (socket.send(PackDo.buildPack(pack1, 109)))
                                close();
                            break;
                        }
                        //110 [插件]设置群公告
                        case 110: {
                            GroupAddAnnouncementPack pack = JSON.parseObject(task.data, GroupAddAnnouncementPack.class);
                            BotGroupDo.setAnnouncement(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.imageFile, pack.sendToNewMember, pack.isPinned, pack.showEditCard, pack.showPopup, pack.requireConfirmation, pack.text);
                        }
                        //111 [插件]删除群公告
                        case 111: {
                            GroupDeleteAnnouncementPack pack = JSON.parseObject(task.data, GroupDeleteAnnouncementPack.class);
                            BotGroupDo.deleteAnnouncement(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                        }
                        //112 [插件]发送好友语言文件
                        case 112: {
                            SendFriendSoundFilePack pack = JSON.parseObject(task.data, SendFriendSoundFilePack.class);
                            BotSendSound.sendFriendFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.ids);
                        }
                        //114 [插件]设置允许群员邀请好友入群的状态
                        case 114: {
                            GroupSetAllowMemberInvitePack pack = JSON.parseObject(task.data, GroupSetAllowMemberInvitePack.class);
                            BotGroupDo.setAllowMemberInvite(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.enable);
                        }
                        //115 [插件]设置允许匿名聊天
                        case 115: {
                            GroupSetAnonymousChatEnabledPack pack = JSON.parseObject(task.data, GroupSetAnonymousChatEnabledPack.class);
                            BotGroupDo.setAnonymousChatEnabled(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.enable);
                        }
                        //127 [插件]断开连接
                        case 127: {
                            close();
                            break;
                        }
                        //60 心跳包
                        case 60: {
                            break;
                        }
                        default: {
                            ColorMiraiMain.logger.error("不知道的包" + task.index);
                            break;
                        }
                    }
                }
                Thread.sleep(10);
            } catch (Exception e) {
                if (!isRun)
                    break;
                ColorMiraiMain.logger.error("数据处理发生异常", e);
                close();
            }
        }
    }

    private void start() {
        RePackObj pack;
        try {
            while ((pack = socket.Read()) == null) {
                Thread.sleep(10);
            }
            StartPack StartPack = JSON.parseObject(pack.data, StartPack.class);
            if (StartPack.Name != null && StartPack.Reg != null) {
                name = StartPack.Name;
                events = StartPack.Reg;
                if (StartPack.Groups != null) {
                    groups.addAll(StartPack.Groups);
                }
                if (StartPack.QQs != null) {
                    qqList.addAll(StartPack.QQs);
                }
                if (StartPack.RunQQ != 0 && !BotStart.getBotsKey().contains(StartPack.RunQQ)) {
                    ColorMiraiMain.logger.warn("插件连接失败，没有运行的QQ：" + StartPack.RunQQ);
                    socket.close();
                    return;
                }
                runQQ = StartPack.RunQQ;
                PluginUtils.addPlugin(name, this);
                String data = JSON.toJSONString(BotStart.getBotsKey());
                socket.send(data.getBytes(ColorMiraiMain.sendCharset));
            } else {
                ColorMiraiMain.logger.warn("插件连接初始化失败");
                socket.close();
                return;
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件连接初始化失败", e);
            return;
        }
        isRun = true;
        doRead.start();
        while (isRun) {
            try {
                pack = socket.Read();
                if (pack != null) {
                    tasks.add(pack);
                }
                Thread.sleep(10);
            } catch (Exception e) {
                if (!isRun)
                    break;
                ColorMiraiMain.logger.error("连接发生异常", e);
                close();
            }
        }
    }

    public void addBuff(String pack) {
        MessageBuffPack temp;
        if (pack.startsWith("{")) {
            temp = JSON.parseObject(pack, MessageBuffPack.class);
        } else {
            Map<String, String> fromPack = PackDo.parseDataFromPack(pack);
            temp = new MessageBuffPack();
            if (!fromPack.containsKey("qq")) {
                return;
            }
            if (!fromPack.containsKey("type")) {
                return;
            }
            temp.qq = Long.parseLong(fromPack.get("qq"));
            temp.type = Integer.parseInt(fromPack.get("type"));
            if (fromPack.containsKey("id"))
                temp.id = Long.parseLong(fromPack.get("id"));
            if (fromPack.containsKey("fid"))
                temp.id = Long.parseLong(fromPack.get("fid"));
            temp.text = new ArrayList<>();
            if (fromPack.containsKey("text")) {
                temp.text.add(fromPack.get("text"));
            }
            if (fromPack.containsKey("send")) {
                String data = fromPack.get("send");
                if (data.equalsIgnoreCase("true"))
                    temp.send = true;
            }
        }
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

    public void callEvent(SendPackObj task, byte[] data) {
        if (runQQ != 0 && task.runqq != runQQ)
            return;
        if (groups.size() != 0 && task.group != 0 && !groups.contains(task.group))
            return;
        if (qqList.size() != 0 && task.qq != 0 && !qqList.contains(task.qq))
            return;
        if (events.contains((int) task.index) || task.index == 60) {
            if (socket.send(data))
                close();
        }
    }

    public void close() {
        try {
            isRun = false;
            socket.close();
            PluginUtils.removePlugin(name);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件断开失败", e);
        }
    }
}
