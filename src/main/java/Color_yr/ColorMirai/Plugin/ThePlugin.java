package Color_yr.ColorMirai.Plugin;

import Color_yr.ColorMirai.EventDo.EventCall;
import Color_yr.ColorMirai.Pack.FromPlugin.*;
import Color_yr.ColorMirai.Pack.PackDo;
import Color_yr.ColorMirai.Pack.ReturnPlugin.FriendsPack;
import Color_yr.ColorMirai.Pack.ReturnPlugin.GroupsPack;
import Color_yr.ColorMirai.Pack.ReturnPlugin.MemberInfoPack;
import Color_yr.ColorMirai.Pack.ReturnPlugin.ReImagePack;
import Color_yr.ColorMirai.Plugin.Objs.RePackObj;
import Color_yr.ColorMirai.Plugin.Objs.SendPackObj;
import Color_yr.ColorMirai.Plugin.Objs.SocketObj;
import Color_yr.ColorMirai.Robot.BotStart;
import Color_yr.ColorMirai.Start;
import com.alibaba.fastjson.JSON;
import net.mamoe.mirai.contact.GroupSettings;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThePlugin {
    private final SocketObj Socket;
    private final List<RePackObj> Tasks = new CopyOnWriteArrayList<>();
    private final List<Long> Groups = new CopyOnWriteArrayList<>();
    private final List<Long> QQs = new CopyOnWriteArrayList<>();

    private final Thread read;
    private final Thread doRead;

    private String name;
    private long runQQ;
    private List<Integer> Events = null;
    private boolean isRun;

    public ThePlugin(SocketObj Socket) {
        this.Socket = Socket;
        read = new Thread(this::start);
        doRead = new Thread(this::startRead);
        read.start();
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
                        case 52:
                            SendGroupMessagePack pack = JSON.parseObject(task.data, SendGroupMessagePack.class);
                            BotStart.sendGroupMessage(runQQ == 0 ? pack.qq : runQQ, pack.id, pack.message);
                            break;
                        case 53:
                            SendGroupPrivateMessagePack pack1 = JSON.parseObject(task.data, SendGroupPrivateMessagePack.class);
                            BotStart.sendGroupPrivateMessage(runQQ == 0 ? pack1.qq : runQQ, pack1.id, pack1.fid, pack1.message);
                            break;
                        case 54:
                            SendFriendMessagePack pack2 = JSON.parseObject(task.data, SendFriendMessagePack.class);
                            BotStart.sendFriendMessage(runQQ == 0 ? pack2.qq : runQQ, pack2.id, pack2.message);
                            break;
                        case 55:
                            GetPack pack17 = JSON.parseObject(task.data, GetPack.class);
                            List<GroupsPack> data = BotStart.getGroups(runQQ == 0 ? pack17.qq : runQQ);
                            if (data == null)
                                break;
                            if (Socket.send(PackDo.BuildPack(data, 55)))
                                close();
                            break;
                        case 56:
                            GetPack pack18 = JSON.parseObject(task.data, GetPack.class);
                            List<FriendsPack> data1 = BotStart.getFriends(runQQ == 0 ? pack18.qq : runQQ);
                            if (data1 == null)
                                break;
                            if (Socket.send(PackDo.BuildPack(data1, 56)))
                                close();
                            break;
                        case 57:
                            GetGroupMemberInfoPack pack3 = JSON.parseObject(task.data, GetGroupMemberInfoPack.class);
                            List<MemberInfoPack> data2 = BotStart.getMembers(runQQ == 0 ? pack3.qq : runQQ, pack3.id);
                            if (data2 == null)
                                break;
                            if (Socket.send(PackDo.BuildPack(data2, 57)))
                                close();
                            break;
                        case 58:
                            GetGroupSettingPack pack4 = JSON.parseObject(task.data, GetGroupSettingPack.class);
                            GroupSettings data3 = BotStart.getGroupInfo(runQQ == 0 ? pack4.qq : runQQ, pack4.id);
                            if (data3 == null)
                                break;
                            if (Socket.send(PackDo.BuildPack(data3, 58)))
                                close();
                            break;
                        case 59:
                            EventCallPack pack5 = JSON.parseObject(task.data, EventCallPack.class);
                            EventCall.DoEvent(runQQ == 0 ? pack5.qq : runQQ, pack5.eventid, pack5.dofun, pack5.arg);
                            break;
                        case 61:
                            Map<String, String> formdata = PackDo.parseDataFromPack(task.data);
                            if (formdata.containsKey("id") && formdata.containsKey("img") && formdata.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata.get("id"));
                                    long qq = Long.parseLong(formdata.get("qq"));
                                    BotStart.sendGroupImage(runQQ == 0 ? qq : runQQ, id, formdata.get("img"));
                                } catch (Exception e) {
                                    Start.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        case 62:
                            Map<String, String> formdata1 = PackDo.parseDataFromPack(task.data);
                            if (formdata1.containsKey("id") && formdata1.containsKey("fid") && formdata1.containsKey("img") && formdata1.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata1.get("id"));
                                    long fid = Long.parseLong(formdata1.get("fid"));
                                    long qq = Long.parseLong(formdata1.get("qq"));
                                    BotStart.sendGroupPrivataImage(runQQ == 0 ? qq : runQQ, id, fid, formdata1.get("img"));
                                } catch (Exception e) {
                                    Start.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        case 63:
                            Map<String, String> formdata2 = PackDo.parseDataFromPack(task.data);
                            if (formdata2.containsKey("id") && formdata2.containsKey("img")) {
                                try {
                                    long id = Long.parseLong(formdata2.get("id"));
                                    long qq = Long.parseLong(formdata2.get("qq"));
                                    BotStart.sendFriendImage(runQQ == 0 ? qq : runQQ, id, formdata2.get("img"));
                                } catch (Exception e) {
                                    Start.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        case 64:
                            DeleteGroupMemberPack pack9 = JSON.parseObject(task.data, DeleteGroupMemberPack.class);
                            BotStart.DeleteGroupMember(runQQ == 0 ? pack9.qq : runQQ, pack9.id, pack9.fid);
                            break;
                        case 65:
                            MuteGroupMemberPack pack10 = JSON.parseObject(task.data, MuteGroupMemberPack.class);
                            BotStart.MuteGroupMember(runQQ == 0 ? pack10.qq : runQQ, pack10.id, pack10.fid, pack10.time);
                            break;
                        case 66:
                            UnmuteGroupMemberPack pack11 = JSON.parseObject(task.data, UnmuteGroupMemberPack.class);
                            BotStart.UnmuteGroupMember(runQQ == 0 ? pack11.qq : runQQ, pack11.id, pack11.fid);
                            break;
                        case 67:
                            GroupMuteAllPack pack12 = JSON.parseObject(task.data, GroupMuteAllPack.class);
                            BotStart.GroupMuteAll(runQQ == 0 ? pack12.qq : runQQ, pack12.id);
                            break;
                        case 68:
                            GroupUnmuteAllPack pack13 = JSON.parseObject(task.data, GroupUnmuteAllPack.class);
                            BotStart.GroupUnmuteAll(runQQ == 0 ? pack13.qq : runQQ, pack13.id);
                            break;
                        case 69:
                            SetGroupMemberCard pack14 = JSON.parseObject(task.data, SetGroupMemberCard.class);
                            BotStart.SetGroupMemberCard(runQQ == 0 ? pack14.qq : runQQ, pack14.id, pack14.fid, pack14.card);
                            break;
                        case 70:
                            SetGroupNamePack pack15 = JSON.parseObject(task.data, SetGroupNamePack.class);
                            BotStart.SetGroupName(runQQ == 0 ? pack15.qq : runQQ, pack15.id, pack15.name);
                            break;
                        case 71:
                            ReCallMessagePack pack16 = JSON.parseObject(task.data, ReCallMessagePack.class);
                            BotStart.ReCall(pack16.id);
                            break;
                        case 74:
                            Map<String, String> formdata3 = PackDo.parseDataFromPack(task.data);
                            if (formdata3.containsKey("id") && formdata3.containsKey("sound") && formdata3.containsKey("qq")) {
                                try {
                                    long id = Long.parseLong(formdata3.get("id"));
                                    long qq = Long.parseLong(formdata3.get("qq"));
                                    BotStart.SendGroupSound(runQQ == 0 ? qq : runQQ, id, formdata3.get("sound"));
                                } catch (Exception e) {
                                    Start.logger.error("解析发生错误", e);
                                }
                            }
                            break;
                        case 75:
                            LoadFileSendToGroupImagePack pack19 = JSON.parseObject(task.data, LoadFileSendToGroupImagePack.class);
                            BotStart.sendGroupImageFile(runQQ == 0 ? pack19.qq : runQQ, pack19.id, pack19.file);
                            break;
                        case 76:
                            LoadFileSendToGroupPrivateImagePack pack20 = JSON.parseObject(task.data, LoadFileSendToGroupPrivateImagePack.class);
                            BotStart.sendGroupPrivateImageFile(runQQ == 0 ? pack20.qq : runQQ, pack20.id, pack20.fid, pack20.file);
                            break;
                        case 77:
                            LoadFileSendToFriendImagePack pack21 = JSON.parseObject(task.data, LoadFileSendToFriendImagePack.class);
                            BotStart.sendFriendImageFile(runQQ == 0 ? pack21.qq : runQQ, pack21.id, pack21.file);
                            break;
                        case 78:
                            LoadFileSendToGroupSoundPack pack22 = JSON.parseObject(task.data, LoadFileSendToGroupSoundPack.class);
                            BotStart.SendGroupSoundFile(runQQ == 0 ? pack22.qq : runQQ, pack22.id, pack22.file);
                            break;
                        case 83:
                            FriendNudgePack pack23 = JSON.parseObject(task.data, FriendNudgePack.class);
                            BotStart.SendNudge(runQQ == 0 ? pack23.qq : runQQ, pack23.id);
                            break;
                        case 84:
                            MemberNudgePack pack24 = JSON.parseObject(task.data, MemberNudgePack.class);
                            BotStart.SendNudge(runQQ == 0 ? pack24.qq : runQQ, pack24.id, pack24.fid);
                            break;
                        case 90:
                            GetImageUrlPack pack25 = JSON.parseObject(task.data, GetImageUrlPack.class);
                            String data4 = BotStart.GetImg(pack25.qq, pack25.uuid);
                            if (data4 == null)
                                break;
                            ReImagePack obj = new ReImagePack();
                            obj.uuid = pack25.uuid;
                            obj.url = data4;
                            obj.qq = pack25.qq;
                            if (Socket.send(PackDo.BuildPack(obj, 90)))
                                close();
                            break;
                        case 91:
                            GetMemberInfo pack26 = JSON.parseObject(task.data, GetMemberInfo.class);
                            MemberInfoPack obj1 = BotStart.getMemberInfo(pack26.qq, pack26.id, pack26.fid);
                            if (obj1 == null)
                                break;
                            if (Socket.send(PackDo.BuildPack(obj1, 91)))
                                close();
                            break;
                        case 92:
                            GetMemberInfo pack27 = JSON.parseObject(task.data, GetMemberInfo.class);
                            FriendsPack obj2 = BotStart.getFriend(pack27.qq, pack27.id);
                            if (obj2 == null)
                                break;
                            if (Socket.send(PackDo.BuildPack(obj2, 92)))
                                close();
                            break;
                        case 93:
                            MusicSharePack pack28 = JSON.parseObject(task.data, MusicSharePack.class);
                            BotStart.SendMusicShare(pack28.qq, pack28.id, pack28.type, pack28.title, pack28.summary, pack28.jumpUrl, pack28.pictureUrl, pack28.musicUrl);
                            break;
                        case 94:
                            pack28 = JSON.parseObject(task.data, MusicSharePack.class);
                            BotStart.SendMusicShareGroup(pack28.qq, pack28.id, pack28.type, pack28.title, pack28.summary, pack28.jumpUrl, pack28.pictureUrl, pack28.musicUrl);
                            break;
                        case 95:
                            pack28 = JSON.parseObject(task.data, MusicSharePack.class);
                            BotStart.SendMusicShareMember(pack28.qq, pack28.id, pack28.fid, pack28.type, pack28.title, pack28.summary, pack28.jumpUrl, pack28.pictureUrl, pack28.musicUrl);
                            break;
                        case 127:
                            close();
                            break;
                    }
                }
                Thread.sleep(10);
            } catch (Exception e) {
                if (!isRun)
                    break;
                Start.logger.error("数据处理发生异常", e);
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
                if (StartPack.RunQQ != 0 && !BotStart.getBots().contains(StartPack.RunQQ)) {
                    Start.logger.warn("插件连接失败，没有运行的QQ：" + StartPack.RunQQ);
                    Socket.close();
                    return;
                }
                runQQ = StartPack.RunQQ;
                PluginUtils.addPlugin(name, this);
                String data = JSON.toJSONString(BotStart.getBots());
                Socket.send(data.getBytes(Start.SendCharset));
            } else {
                Start.logger.warn("插件连接初始化失败");
                Socket.close();
                return;
            }
        } catch (Exception e) {
            Start.logger.error("插件连接初始化失败", e);
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
                Start.logger.error("连接发生异常", e);
                close();
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
            Start.logger.error("插件断开失败", e);
        }
    }
}
