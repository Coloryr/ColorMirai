package Color_yr.ColorMirai.Socket;

import Color_yr.ColorMirai.BotStart;
import Color_yr.ColorMirai.EventDo.EventCall;
import Color_yr.ColorMirai.Pack.FromPlugin.*;
import Color_yr.ColorMirai.Pack.PackDo;
import Color_yr.ColorMirai.Start;
import com.alibaba.fastjson.JSON;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Plugins {
    private final Socket Socket;
    private final List<RePackTask> Tasks = new CopyOnWriteArrayList<>();
    private final Thread read;
    private final Thread doRead;
    private String name;
    private List<Integer> Events = null;
    private boolean isRun;

    public String getName() {
        return name;
    }

    public String getReg() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var item : Events) {
            stringBuilder.append(item).append(",");
        }
        String data = stringBuilder.toString();
        return data.substring(0, data.length() - 1);
    }

    public Plugins(Socket Socket) {
        this.Socket = Socket;
        read = new Thread(this::start);
        doRead = new Thread(this::startRead);
        read.start();
    }

    private void startRead() {
        while (isRun) {
            try {
                if (!Tasks.isEmpty()) {
                    var task = Tasks.remove(0);
                    switch (task.getIndex()) {
                        case 52:
                            var pack = JSON.parseObject(task.getData(), SendGroupMessagePack.class);
                            BotStart.sendGroupMessage(pack.id, pack.message);
                            break;
                        case 53:
                            var pack1 = JSON.parseObject(task.getData(), SendGroupPrivateMessagePack.class);
                            BotStart.sendGroupPrivateMessage(pack1.id, pack1.fid, pack1.message);
                            break;
                        case 54:
                            var pack2 = JSON.parseObject(task.getData(), SendFriendMessagePack.class);
                            BotStart.sendFriendMessage(pack2.id, pack2.message);
                            break;
                        case 55:
                            if (SocketServer.sendPack(PackDo.BuildPack(BotStart.getGroups(), 55), Socket))
                                close();
                            break;
                        case 56:
                            if (SocketServer.sendPack(PackDo.BuildPack(BotStart.getFriends(), 56), Socket))
                                close();
                            break;
                        case 57:
                            var pack3 = JSON.parseObject(task.getData(), GetGroupMemberInfoPack.class);
                            if (SocketServer.sendPack(PackDo.BuildPack(
                                    BotStart.getMembers(pack3.id), 57), Socket))
                                close();
                            break;
                        case 58:
                            var pack4 = JSON.parseObject(task.getData(), GetGroupSettingPack.class);
                            if (SocketServer.sendPack(PackDo.BuildPack(
                                    BotStart.getGroupInfo(pack4.id), 58), Socket))
                                close();
                            break;
                        case 59:
                            var pack5 = JSON.parseObject(task.getData(), EventCallPack.class);
                            EventCall.DoEvent(pack5.eventid, pack5.dofun, pack5.arg);
                            break;
                        case 61:
                            var pack6 = JSON.parseObject(task.getData(), SendGroupImagePack.class);
                            BotStart.sendGroupImage(pack6.id, pack6.img);
                            break;
                        case 62:
                            var pack7 = JSON.parseObject(task.getData(), SendGroupPrivateImagePack.class);
                            BotStart.sendGroupPrivataImage(pack7.id, pack7.fid, pack7.img);
                            break;
                        case 63:
                            var pack8 = JSON.parseObject(task.getData(), SendFriendImagePack.class);
                            BotStart.sendFriendImage(pack8.id, pack8.img);
                            break;
                        case 64:
                            var pack9 = JSON.parseObject(task.getData(), DeleteGroupMember.class);
                            BotStart.DeleteGroupMember(pack9.id, pack9.fid);
                            break;
                        case 65:
                            var pack10 = JSON.parseObject(task.getData(), MuteGroupMember.class);
                            BotStart.MuteGroupMember(pack10.id, pack10.fid, pack10.time);
                            break;
                        case 66:
                            var pack11 = JSON.parseObject(task.getData(), UnmuteGroupMember.class);
                            BotStart.UnmuteGroupMember(pack11.id, pack11.fid);
                            break;
                        case 67:
                            var pack12 = JSON.parseObject(task.getData(), GroupMuteAll.class);
                            BotStart.GroupMuteAll(pack12.id);
                            break;
                        case 68:
                            var pack13 = JSON.parseObject(task.getData(), GroupUnmuteAll.class);
                            BotStart.GroupUnmuteAll(pack13.id);
                            break;
                        case 69:
                            var pack14 = JSON.parseObject(task.getData(), SetGroupMemberCard.class);
                            BotStart.SetGroupMemberCard(pack14.id, pack14.fid, pack14.card);
                            break;
                        case 70:
                            var pack15 = JSON.parseObject(task.getData(), SetGroupName.class);
                            BotStart.SetGroupName(pack15.id, pack15.name);
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
        try {
            while (Socket.getInputStream().available() == 0) {
                Thread.sleep(10);
            }
            var buf = new byte[Socket.getInputStream().available()];
            var len = Socket.getInputStream().read(buf);
            if (len > 0) {
                String temp = new String(buf, StandardCharsets.UTF_8);
                StartPack pack = JSON.parseObject(temp, StartPack.class);
                if (pack.Name != null && pack.Reg != null) {
                    name = pack.Name;
                    Events = pack.Reg;
                    SocketServer.addPlugin(name, this);
                } else {
                    Start.logger.warn("插件连接初始化失败");
                    Socket.close();
                    return;
                }
            } else {
                Start.logger.warn("插件连接初始化失败");
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
                if (Socket.getInputStream().available() > 0) {
                    var inputStream = Socket.getInputStream();
                    var bytes = new byte[1024];
                    int len;
                    byte index = 0;
                    var sb = new StringBuilder();
                    while ((len = inputStream.read(bytes)) != -1) {
                        if (len == 1024)
                            sb.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
                        else {
                            index = bytes[len - 1];
                            bytes[len - 1] = 0;
                            sb.append(new String(bytes, 0, len - 1, StandardCharsets.UTF_8));
                            break;
                        }
                    }
                    Tasks.add(new RePackTask(index, sb.toString()));
                } else if (Socket.getInputStream().available() < 0) {
                    Start.logger.warn("插件连接断开");
                    close();
                    return;
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

    public void pack() {
        byte[] data = new byte[3];
        data[0] = '{';
        data[1] = '}';
        data[2] = 60;
        if (SocketServer.sendPack(data, Socket))
            close();
    }

    public void callEvent(int index, byte[] data) {
        if (Events.contains(index)) {
            if (SocketServer.sendPack(data, Socket))
                close();
        }
    }

    public void close() {
        try {
            isRun = false;
            Socket.close();
            SocketServer.removePlugin(name);
        } catch (Exception e) {
            Start.logger.error("插件断开失败", e);
        }
    }
}
