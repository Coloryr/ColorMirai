package Color_yr.ColorMirai.Socket;

import Color_yr.ColorMirai.BotStart;
import Color_yr.ColorMirai.EventDo.EventCall;
import Color_yr.ColorMirai.Pack.FromPlugin.*;
import Color_yr.ColorMirai.Pack.PackDo;
import Color_yr.ColorMirai.Start;
import com.alibaba.fastjson.JSON;

import java.io.InputStream;
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
                    RePackTask task = Tasks.remove(0);
                    if (!task.getData().isEmpty()) {
                        switch (task.getIndex()) {
                            case 52:
                                SendGroupMessagePack pack = JSON.parseObject(task.getData(), SendGroupMessagePack.class);
                                BotStart.sendGroupMessage(pack.getId(), pack.getMessage());
                                break;
                            case 53:
                                SendGroupPrivateMessagePack pack1 = JSON.parseObject(
                                        task.getData(), SendGroupPrivateMessagePack.class);
                                BotStart.sendGroupPrivateMessage(pack1.getId(), pack1.getFid(), pack1.getMessage());
                                break;
                            case 54:
                                SendFriendMessagePack pack2 = JSON.parseObject(task.getData(), SendFriendMessagePack.class);
                                BotStart.sendFriendMessage(pack2.getId(), pack2.getMessage());
                                break;
                            case 55:
                                if (!SocketServer.sendPack(PackDo.BuildPack(BotStart.getGroups(), 55), Socket))
                                    close();
                                break;
                            case 56:
                                if (!SocketServer.sendPack(PackDo.BuildPack(BotStart.getFriends(), 56), Socket))
                                    close();
                                break;
                            case 57:
                                GetGroupMemberInfoPack pack3 = JSON.parseObject(task.getData(), GetGroupMemberInfoPack.class);
                                if (!SocketServer.sendPack(PackDo.BuildPack(
                                        BotStart.getMembers(pack3.getId()), 57), Socket))
                                    close();
                                break;
                            case 58:
                                GetGroupSettingPack pack4 = JSON.parseObject(task.getData(), GetGroupSettingPack.class);
                                if (!SocketServer.sendPack(PackDo.BuildPack(
                                        BotStart.getGroupInfo(pack4.getId()), 58), Socket))
                                    close();
                                break;
                            case 59:
                                EventCallPack pack5 = JSON.parseObject(task.getData(), EventCallPack.class);
                                EventCall.DoEvent(pack5.getEventid(), pack5.getDofun(), pack5.getArg());
                                break;
                            case 61:
                                SendImageGroupPack pack6 = JSON.parseObject(task.getData(), SendImageGroupPack.class);
                                BotStart.sendGroupImage(pack6.getId(), pack6.getImg());
                                break;
                            case 62:
                                SendImageGroupPrivatePack pack7 = JSON.parseObject(task.getData(), SendImageGroupPrivatePack.class);
                                BotStart.sendGroupPrivataImage(pack7.getId(), pack7.getFid(), pack7.getImg());
                                break;
                            case 63:
                                SendFriendImagePack pack8 = JSON.parseObject(task.getData(), SendFriendImagePack.class);
                                BotStart.sendFriendImage(pack8.getId(), pack8.getImg());
                                break;
                        }
                    }
                }
                Thread.sleep(50);
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
            byte[] buf = new byte[Socket.getInputStream().available()];
            int len = Socket.getInputStream().read(buf);
            if (len > 0) {
                String temp = new String(buf, StandardCharsets.UTF_8);
                StartPack pack = JSON.parseObject(temp, StartPack.class);
                if (pack.getName() != null && pack.getReg() != null) {
                    name = pack.getName();
                    Events = pack.getReg();
                    SocketServer.addPlugin(name, this);
                } else {
                    Start.logger.warn("插件连接初始化失败");
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
                    InputStream inputStream = Socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int len;
                    byte index = 0;
                    StringBuilder sb = new StringBuilder();
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
                Thread.sleep(50);
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
        if (!SocketServer.sendPack(data, Socket))
            close();
    }

    public void callEvent(int index, byte[] data) {
        if (Events.contains(index)) {
            if (!SocketServer.sendPack(data, Socket))
                close();
        }
    }

    public void close() {
        try {
            isRun = false;
            Socket.close();
            if (read != null && read.isAlive())
                read.join();
            if (doRead != null && doRead.isAlive())
                doRead.join();
            SocketServer.removePlugin(name);
        } catch (Exception e) {
            Start.logger.error("插件断开失败", e);
        }
    }
}
