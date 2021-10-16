package Color_yr.ColorMirai.plugin;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.event.EventCall;
import Color_yr.ColorMirai.pack.PackDo;
import Color_yr.ColorMirai.pack.from.*;
import Color_yr.ColorMirai.pack.re.*;
import Color_yr.ColorMirai.plugin.socket.obj.BuffObj;
import Color_yr.ColorMirai.plugin.socket.obj.RePackObj;
import Color_yr.ColorMirai.plugin.socket.obj.SendPackObj;
import Color_yr.ColorMirai.plugin.socket.obj.SocketObj;
import Color_yr.ColorMirai.robot.*;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.GroupSettings;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.announcement.OnlineAnnouncement;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.QuoteReply;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThePlugin {
    private final SocketObj Socket;
    private final List<RePackObj> Tasks = new CopyOnWriteArrayList<>();
    private final List<Long> Groups = new CopyOnWriteArrayList<>();
    private final List<Long> QQs = new CopyOnWriteArrayList<>();
    private final Map<String, BuffObj> MessageBuff = new ConcurrentHashMap<>();

    private final Thread doRead;

    private String name;
    private long runQQ;
    private List<Integer> Events = null;
    private boolean isRun;

    public ThePlugin(SocketObj Socket) {
        this.Socket = Socket;
        doRead = new Thread(this::startRead);
        new Thread(this::start).start();
    }

    public String getName() {
        return name;
    }

    public String getReg() {
        if (Events.size() == 0)
            return "无";
        StringBuilder stringBuilder = new StringBuilder();
        for (int item : Events) {
            stringBuilder.append(item).append(",");
        }
        String data = stringBuilder.toString();
        return data.substring(0, data.length() - 1);
    }

    private void startRead() {
        while (isRun) {
            try {
                if (!Tasks.isEmpty()) {
                    RePackObj task = Tasks.remove(0);
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
                            if (Socket.send(PackDo.BuildPack(pack31, 55)))
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
                            if (Socket.send(PackDo.BuildPack(pack1, 56)))
                                close();
                            break;
                        }
                        //57 [插件]获取群成员
                        case 57: {
                            GetGroupMemberInfoPack pack = JSON.parseObject(task.data, GetGroupMemberInfoPack.class);
                            List<MemberInfoPack> data = BotGetData.getMembers(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                break;
                            ListMemberPack pack1 = new ListMemberPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.members = data;
                            if (Socket.send(PackDo.BuildPack(pack1, 57)))
                                close();
                            break;
                        }
                        //58 [插件]获取群设置
                        case 58: {
                            GetGroupSettingPack pack = JSON.parseObject(task.data, GetGroupSettingPack.class);
                            GroupSettings data = BotGetData.getGroupInfo(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                break;
                            GroupSettingPack pack1 = new GroupSettingPack();
                            pack1.setting = data;
                            pack1.id = pack.id;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (Socket.send(PackDo.BuildPack(data, 58)))
                                close();
                            break;
                        }
                        //59 [插件]回应事件
                        case 59: {
                            EventCallPack pack = JSON.parseObject(task.data, EventCallPack.class);
                            EventCall.DoEvent(runQQ == 0 ? pack.qq : runQQ, pack.eventid, pack.dofun, pack.arg);
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
                                    BotSendImage.sendFriendImage(runQQ == 0 ? qq : runQQ, id, formdata.get("img"));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //64 [插件]删除群员
                        case 64: {
                            DeleteGroupMemberPack pack = JSON.parseObject(task.data, DeleteGroupMemberPack.class);
                            BotGroupDo.DeleteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.black);
                            break;
                        }
                        //65 [插件]禁言群员
                        case 65: {
                            GroupMemberMutePack pack = JSON.parseObject(task.data, GroupMemberMutePack.class);
                            BotGroupDo.MuteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.time);
                            break;
                        }
                        //66 [插件]解除禁言
                        case 66: {
                            GroupMemberUnmutePack pack = JSON.parseObject(task.data, GroupMemberUnmutePack.class);
                            BotGroupDo.UnmuteGroupMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //67 [插件]开启全员禁言
                        case 67: {
                            GroupMuteAllPack pack = JSON.parseObject(task.data, GroupMuteAllPack.class);
                            BotGroupDo.GroupMuteAll(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //68 [插件]关闭全员禁言
                        case 68: {
                            GroupUnmuteAllPack pack = JSON.parseObject(task.data, GroupUnmuteAllPack.class);
                            BotGroupDo.GroupUnmuteAll(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //69 [插件]设置群名片
                        case 69: {
                            SetGroupMemberCard pack = JSON.parseObject(task.data, SetGroupMemberCard.class);
                            BotGroupDo.SetGroupMemberCard(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.card);
                            break;
                        }
                        //70 [插件]设置群名
                        case 70: {
                            SetGroupNamePack pack = JSON.parseObject(task.data, SetGroupNamePack.class);
                            BotGroupDo.SetGroupName(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.name);
                            break;
                        }
                        //71 [插件]撤回消息
                        case 71: {
                            ReCallMessagePack pack = JSON.parseObject(task.data, ReCallMessagePack.class);
                            BotStart.ReCall(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //74 [插件]发送语音到群
                        case 74: {
                            Map<String, String> formdata = PackDo.parseDataFromPack(task.data);
                            if (formdata.containsKey("id") && formdata.containsKey("sound") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    BotSendSound.SendGroupSound(runQQ == 0 ? qq : runQQ, id, formdata.get("sound"));
                                } catch (Exception e) {
                                    ColorMiraiMain.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        }
                        //75 [插件]从本地文件加载图片发送到群
                        case 75: {
                            SendGroupImageFilePack pack = JSON.parseObject(task.data, SendGroupImageFilePack.class);
                            BotSendImage.sendGroupImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file);
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
                            BotSendImage.sendFriendImageFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file);
                            break;
                        }
                        //78 [插件]从本地文件加载语音发送到群
                        case 78: {
                            SendGroupSoundFilePack pack = JSON.parseObject(task.data, SendGroupSoundFilePack.class);
                            BotSendSound.SendGroupSoundFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file);
                            break;
                        }
                        //83 [插件]发送私聊戳一戳
                        case 83: {
                            FriendNudgePack pack = JSON.parseObject(task.data, FriendNudgePack.class);
                            BotSendNudge.SendNudge(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            break;
                        }
                        //84 [插件]发送群戳一戳
                        case 84: {
                            GroupMemberNudgePack pack = JSON.parseObject(task.data, GroupMemberNudgePack.class);
                            BotSendNudge.SendNudge(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                            break;
                        }
                        //90 [插件]获取图片Url
                        case 90: {
                            GetImageUrlPack pack = JSON.parseObject(task.data, GetImageUrlPack.class);
                            String data4 = BotGetData.GetImg(runQQ == 0 ? pack.qq : runQQ, pack.uuid);
                            if (data4 == null)
                                break;
                            ReImagePack pack1 = new ReImagePack();
                            pack1.uuid = pack.uuid;
                            pack1.url = data4;
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            if (Socket.send(PackDo.BuildPack(pack1, 90)))
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
                            if (Socket.send(PackDo.BuildPack(pack1, 91)))
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
                            if (Socket.send(PackDo.BuildPack(pack1, 92)))
                                close();
                            break;
                        }
                        //93 [插件]发送音乐分享
                        case 93: {
                            MusicSharePack pack = JSON.parseObject(task.data, MusicSharePack.class);
                            if (pack.type1 == 0) {
                                BotSendMusicShare.SendMusicShare(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            } else if (pack.type1 == 1) {
                                BotSendMusicShare.SendMusicShareGroup(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            } else if (pack.type1 == 2) {
                                BotSendMusicShare.SendMusicShareMember(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.type, pack.title, pack.summary, pack.jumpUrl, pack.pictureUrl, pack.musicUrl);
                            }
                            break;
                        }
                        //94 [插件]设置群精华消息
                        case 94: {
                            GroupEssenceMessagePack pack = JSON.parseObject(task.data, GroupEssenceMessagePack.class);
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
                            AddGroupFilePack pack = JSON.parseObject(task.data, AddGroupFilePack.class);
                            BotGroupFile.addFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file, pack.name);
                            break;
                        }
                        //100 [插件]删除群文件
                        case 100: {
                            DeleteGroupFilePack pack = JSON.parseObject(task.data, DeleteGroupFilePack.class);
                            BotGroupFile.deleteFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.name);
                            break;
                        }
                        //101 [插件]获取群文件
                        case 101: {
                            GetGroupFilesPack pack = JSON.parseObject(task.data, GetGroupFilesPack.class);
                            List<String> data = BotGroupFile.getFiles(runQQ == 0 ? pack.qq : runQQ, pack.id);
                            if (data == null)
                                return;
                            GroupFilesPack pack1 = new GroupFilesPack();
                            pack1.qq = runQQ == 0 ? pack.qq : runQQ;
                            pack1.id = pack.id;
                            pack1.files = data;
                            if (Socket.send(PackDo.BuildPack(pack1, 101)))
                                close();
                            break;
                        }
                        //102 [插件]移动群文件
                        case 102: {
                            MoveGroupFilePack pack = JSON.parseObject(task.data, MoveGroupFilePack.class);
                            BotGroupFile.moveFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.old, pack.dir);
                            break;
                        }
                        //103 [插件]重命名群文件
                        case 103: {
                            RemoveGroupFilePack pack = JSON.parseObject(task.data, RemoveGroupFilePack.class);
                            BotGroupFile.renameFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.old, pack.now);
                            break;
                        }
                        //104 [插件]创新群文件文件夹
                        case 104: {
                            AddGroupDirPack pack = JSON.parseObject(task.data, AddGroupDirPack.class);
                            BotGroupFile.addGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //105 [插件]删除群文件文件夹
                        case 105: {
                            RemoveGroupDirPack pack = JSON.parseObject(task.data, RemoveGroupDirPack.class);
                            BotGroupFile.removeGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.dir);
                            break;
                        }
                        //106 [插件]重命名群文件文件夹
                        case 106: {
                            RenameGroupDirPack pack = JSON.parseObject(task.data, RenameGroupDirPack.class);
                            BotGroupFile.renameGroupDir(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.old, pack.now);
                            break;
                        }
                        //107 [插件]下载群文件到指定位置
                        case 107: {
                            DownloadGroupFilePack pack = JSON.parseObject(task.data, DownloadGroupFilePack.class);
                            BotGroupFile.downloadGroupFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.name, pack.dir);
                            break;
                        }
                        //108 [插件]设置取消管理员
                        case 108: {
                            SetGroupAdminPack pack = JSON.parseObject(task.data, SetGroupAdminPack.class);
                            BotGroupDo.setAdmin(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid, pack.type);
                            break;
                        }
                        //109 [插件]获取群公告
                        case 109: {
                            GetGroupAnnouncementsPack pack = JSON.parseObject(task.data, GetGroupAnnouncementsPack.class);
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
                            if (Socket.send(PackDo.BuildPack(pack1, 109)))
                                close();
                            break;
                        }
                        //110 [插件]设置群公告
                        case 110: {
                            SetGetGroupAnnouncementsPack pack = JSON.parseObject(task.data, SetGetGroupAnnouncementsPack.class);
                            BotGroupDo.setAnnouncement(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.imageFile, pack.sendToNewMember, pack.isPinned, pack.showEditCard, pack.showPopup, pack.requireConfirmation, pack.text);
                        }
                        //111 [插件]删除群公告
                        case 111: {
                            DeleteGroupAnnouncementsPack pack = JSON.parseObject(task.data, DeleteGroupAnnouncementsPack.class);
                            BotGroupDo.deleteAnnouncement(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.fid);
                        }
                        //112 [插件]发送好友语言文件
                        case 112: {
                            SendFriendSoundFilePack pack = JSON.parseObject(task.data, SendFriendSoundFilePack.class);
                            BotSendSound.SendFriendFile(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.file);
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
            while ((pack = Socket.Read()) == null) {
                Thread.sleep(10);
            }
            StartPack StartPack = JSON.parseObject(pack.data, StartPack.class);
            if (StartPack.Name != null && StartPack.Reg != null) {
                name = StartPack.Name;
                Events = StartPack.Reg;
                if (StartPack.Groups != null) {
                    Groups.addAll(StartPack.Groups);
                }
                if (StartPack.QQs != null) {
                    QQs.addAll(StartPack.QQs);
                }
                if (StartPack.RunQQ != 0 && !BotStart.getBotsKey().contains(StartPack.RunQQ)) {
                    ColorMiraiMain.logger.warn("插件连接失败，没有运行的QQ：" + StartPack.RunQQ);
                    Socket.close();
                    return;
                }
                runQQ = StartPack.RunQQ;
                PluginUtils.addPlugin(name, this);
                String data = JSON.toJSONString(BotStart.getBotsKey());
                Socket.send(data.getBytes(ColorMiraiMain.SendCharset));
            } else {
                ColorMiraiMain.logger.warn("插件连接初始化失败");
                Socket.close();
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
                pack = Socket.Read();
                if (pack != null) {
                    Tasks.add(pack);
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
            if (fromPack.containsKey("img")) {
                temp.img = fromPack.get("img");
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
            item = MessageBuff.get("F:" + temp.id);
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
            item = MessageBuff.get("G:" + temp.id);
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
            item = MessageBuff.get("G:" + temp.id + "F:" + temp.id);
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
            if (item1.startsWith("at:")) {
                if (temp.type == 1) {
                    Group group1 = bot.getGroup(temp.id);
                    if (group1 == null)
                        continue;
                    NormalMember member = group1.get(Long.parseLong(item1.replace("at:", "")));
                    if (member == null)
                        continue;
                    item.message = item.message.plus(new At(member.getId()));
                } else {
                    item1 = item1.replace("at:", "");
                    item.message = item.message.plus(item1);
                }
            } else if (item1.startsWith("quote:")) {
                int id = Integer.parseInt(item1.replace("quote:", ""));
                MessageSaveObj call = BotStart.getMessage(temp.qq, id);
                if (call == null || call.source == null)
                    continue;
                QuoteReply quote = new QuoteReply(call.source);
                item.message = item.message.plus(quote);
            } else {
                item.message = item.message.plus(item1);
            }
        }
        if (temp.img != null && !temp.img.isEmpty()) {
            try {
                ByteArrayInputStream stream = new ByteArrayInputStream(ColorMiraiMain.decoder.decode(temp.img));
                ExternalResource image = ExternalResource.createAutoCloseable(ExternalResource.create(stream));
                item.message = item.message.plus(item.contact.uploadImage(image));
                stream.close();
            } catch (IOException e) {
                ColorMiraiMain.logger.error("消息队列添加图片失败", e);
                e.printStackTrace();
            }
        }
        if (temp.imgurl != null && !temp.imgurl.isEmpty()) {
            try {
                FileInputStream stream = new FileInputStream(temp.imgurl);
                ExternalResource image = ExternalResource.createAutoCloseable(ExternalResource.create(stream));
                item.message = item.message.plus(item.contact.uploadImage(image));
                stream.close();
            } catch (IOException e) {
                ColorMiraiMain.logger.error("消息队列添加图片失败", e);
                e.printStackTrace();
            }
        }
        if (temp.send) {
            MessageReceipt message = item.contact.sendMessage(item.message);
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
                MessageBuff.put("F:" + temp.id, item);
            } else if (temp.type == 1) {
                MessageBuff.put("G:" + temp.id, item);
            } else if (temp.type == 2) {
                MessageBuff.put("G:" + temp.id + "F:" + temp.id, item);
            }
        }
    }

    public void callEvent(SendPackObj task, byte[] data) {
        if (runQQ != 0 && task.runqq != runQQ)
            return;
        if (Groups.size() != 0 && task.group != 0 && !Groups.contains(task.group))
            return;
        if (QQs.size() != 0 && task.qq != 0 && !QQs.contains(task.qq))
            return;
        if (Events.contains((int) task.index) || task.index == 60) {
            if (Socket.send(data))
                close();
        }
    }

    public void close() {
        try {
            isRun = false;
            Socket.close();
            PluginUtils.removePlugin(name);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件断开失败", e);
        }
    }
}
