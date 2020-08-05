package Color_yr.ColorMirai;

import Color_yr.ColorMirai.EventDo.EventBase;
import Color_yr.ColorMirai.EventDo.EventCall;
import Color_yr.ColorMirai.Pack.*;
import Color_yr.ColorMirai.Socket.Plugins;
import Color_yr.ColorMirai.Socket.SocketServer;
import com.google.gson.Gson;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BotStart {
    private static final List<Task> Tasks = new CopyOnWriteArrayList<>();
    private static Bot bot;
    private static Thread EventDo;
    private static boolean isRun;
    private static Gson Gson;

    public static boolean Start() {
        Gson = new Gson();
        bot = BotFactoryJvm.newBot(Start.Config.getQQ(), Start.Config.getPassword(), new BotConfiguration() {
            {
                fileBasedDeviceInfo(Start.RunDir + "info.json");
                setProtocol(MiraiProtocol.ANDROID_PHONE);
            }
        });
        try {
            bot.login();
        } catch (Exception e) {
            Start.logger.error("机器人错误", e);
            return false;
        }

        Events.registerEvents(bot, new SimpleListenerHost() {
            //1 [机器人]图片上传前. 可以阻止上传（事件）
            @EventHandler
            public ListeningStatus BeforeImageUploadEvent(BeforeImageUploadEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                String name = event.getSource().toString();
                long id = event.getTarget().getId();
                BeforeImageUploadPack pack = new BeforeImageUploadPack(name, id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(1, data));
                return ListeningStatus.LISTENING;
            }

            //2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）
            @EventHandler
            public ListeningStatus BotAvatarChangedEvent(BotAvatarChangedEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                String name = event.getBot().getNick();
                BotAvatarChangedPack pack = new BotAvatarChangedPack(name);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(2, data));
                return ListeningStatus.LISTENING;
            }

            //3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
            @EventHandler
            public ListeningStatus BotGroupPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                String name = event.getNew().name();
                BotGroupPermissionChangePack pack = new BotGroupPermissionChangePack(name, id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(3, data));
                return ListeningStatus.LISTENING;
            }

            //4 [机器人]被邀请加入一个群（事件）
            @EventHandler
            public ListeningStatus BotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroupId();
                String name = event.getInvitor().getNick();
                long fid = event.getInvitorId();
                BotInvitedJoinGroupRequestEventPack pack =
                        new BotInvitedJoinGroupRequestEventPack(name, id, fid, event.getEventId());
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(4, data));
                EventCall.AddEvent(new EventBase(event.getEventId(), (byte) 4, event));
                return ListeningStatus.LISTENING;
            }

            //5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
            @EventHandler
            public ListeningStatus BotJoinGroupEventA(BotJoinGroupEvent.Active event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                BotJoinGroupEventAPack pack = new BotJoinGroupEventAPack(id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(5, data));
                return ListeningStatus.LISTENING;
            }

            //6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
            @EventHandler
            public ListeningStatus BotJoinGroupEventB(BotJoinGroupEvent.Invite event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getInvitor().getId();
                String name = event.getInvitor().getNick();
                BotJoinGroupEventBPack pack = new BotJoinGroupEventBPack(name, id, fid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(6, data));
                return ListeningStatus.LISTENING;
            }

            //7 [机器人]主动退出一个群（事件）
            @EventHandler
            public ListeningStatus BotLeaveEventA(BotLeaveEvent.Active event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                BotLeaveEventAPack pack = new BotLeaveEventAPack(id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(7, data));
                return ListeningStatus.LISTENING;
            }

            //8 [机器人]被管理员或群主踢出群（事件）
            @EventHandler
            public ListeningStatus BotLeaveEventB(BotLeaveEvent.Kick event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                String name = event.getOperator().getNick();
                long fid = event.getOperator().getId();
                BotLeaveEventBPack pack = new BotLeaveEventBPack(name, id, fid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(7, data));
                return ListeningStatus.LISTENING;
            }

            //9 [机器人]被禁言（事件）
            @EventHandler
            public ListeningStatus BotMuteEvent(BotMuteEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                int time = event.getDurationSeconds();
                String name = event.getOperator().getNick();
                long fid = event.getOperator().getId();
                BotMuteEventPack pack = new BotMuteEventPack(name, id, fid, time);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(9, data));
                return ListeningStatus.LISTENING;
            }

            //10 [机器人]主动离线（事件）
            @EventHandler
            public ListeningStatus BotOfflineEventA(BotOfflineEvent.Active event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                String message = event.getCause().getMessage();
                BotOfflineEventAPack pack = new BotOfflineEventAPack(message);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(10, data));
                return ListeningStatus.LISTENING;
            }

            //11 [机器人]被挤下线（事件）
            @EventHandler
            public ListeningStatus BotOfflineEventB(BotOfflineEvent.Force event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                String title = event.getTitle();
                String message = event.getMessage();
                BotOfflineEventBPack pack = new BotOfflineEventBPack(message, title);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(11, data));
                return ListeningStatus.LISTENING;
            }

            //12 [机器人]被服务器断开（事件）
            @EventHandler
            public ListeningStatus BotOfflineEventC(BotOfflineEvent.MsfOffline event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                String message = event.getCause().getMessage();
                BotOfflineEventAPack pack = new BotOfflineEventAPack(message);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(12, data));
                return ListeningStatus.LISTENING;
            }

            //13 [机器人]因网络问题而掉线（事件）
            @EventHandler
            public ListeningStatus BotOfflineEventD(BotOfflineEvent.Dropped event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                String message = event.getCause().getMessage();
                BotOfflineEventAPack pack = new BotOfflineEventAPack(message);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(13, data));
                return ListeningStatus.LISTENING;
            }

            //14 [机器人]服务器主动要求更换另一个服务器（事件）
            @EventHandler
            public ListeningStatus BotOfflineEventE(BotOfflineEvent.RequireReconnect event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getBot().getId();
                BotOfflineEventCPack pack = new BotOfflineEventCPack(id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(14, data));
                return ListeningStatus.LISTENING;
            }

            //15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）
            @EventHandler
            public ListeningStatus BotOnlineEvent(BotOnlineEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getBot().getId();
                BotOnlineEventPack pack = new BotOnlineEventPack(id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(15, data));
                return ListeningStatus.LISTENING;
            }

            //16 [机器人]主动或被动重新登录（事件）
            @EventHandler
            public ListeningStatus BotReloginEvent(BotReloginEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                String message = event.getCause().getMessage();
                BotReloginEventPack pack = new BotReloginEventPack(message);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(16, data));
                return ListeningStatus.LISTENING;
            }

            //17 [机器人]被取消禁言（事件）
            @EventHandler
            public ListeningStatus BotUnmuteEvent(BotUnmuteEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getOperator().getId();
                BotUnmuteEventPack pack = new BotUnmuteEventPack(id, fid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(17, data));
                return ListeningStatus.LISTENING;
            }

            //18 [机器人]成功添加了一个新好友（事件）
            @EventHandler
            public ListeningStatus FriendAddEvent(FriendAddEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getFriend().getId();
                String name = event.getFriend().getNick();
                FriendAddEventPack pack = new FriendAddEventPack(name, id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(18, data));
                return ListeningStatus.LISTENING;
            }

            //19 [机器人]头像被修改（事件）
            @EventHandler
            public ListeningStatus FriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getFriend().getId();
                String name = event.getFriend().getNick();
                String url = event.getFriend().getAvatarUrl();
                FriendAvatarChangedEventPack pack = new FriendAvatarChangedEventPack(name, id, url);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(19, data));
                return ListeningStatus.LISTENING;
            }

            //20 [机器人]好友已被删除（事件）
            @EventHandler
            public ListeningStatus FriendDeleteEvent(FriendDeleteEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getFriend().getId();
                String name = event.getFriend().getNick();
                FriendDeleteEventPack pack = new FriendDeleteEventPack(name, id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(20, data));
                return ListeningStatus.LISTENING;
            }

            //21 [机器人]在好友消息发送后广播（事件）
            @EventHandler
            public ListeningStatus FriendMessagePostSendEvent(FriendMessagePostSendEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                MessageChain message = event.getMessage();
                long id = event.getTarget().getId();
                String name = event.getTarget().getNick();
                boolean res = event.getReceipt() != null;
                String error = "";
                if (event.getException() != null) {
                    error = event.getException().getMessage();
                }
                FriendMessagePostSendEventPack pack = new FriendMessagePostSendEventPack(message, id, name, res, error);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(21, data));
                return ListeningStatus.LISTENING;
            }

            //22 [机器人]在发送好友消息前广播（事件）
            @EventHandler
            public ListeningStatus FriendMessagePreSendEvent(FriendMessagePreSendEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                Message message = event.getMessage();
                long id = event.getTarget().getId();
                String name = event.getTarget().getNick();
                FriendMessagePreSendEventPack pack = new FriendMessagePreSendEventPack(message, id, name);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(22, data));
                return ListeningStatus.LISTENING;
            }

            //23 [机器人]好友昵称改变（事件）
            @EventHandler
            public ListeningStatus FriendRemarkChangeEvent(FriendRemarkChangeEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getFriend().getId();
                String name = event.getNewName();
                FriendRemarkChangeEventPack pack = new FriendRemarkChangeEventPack(id, name);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(23, data));
                return ListeningStatus.LISTENING;
            }

            //24 [机器人]群 "匿名聊天" 功能状态改变（事件）
            @EventHandler
            public ListeningStatus GroupAllowAnonymousChatEvent(GroupAllowAnonymousChatEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                GroupAllowAnonymousChatEventPack pack = new GroupAllowAnonymousChatEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(24, data));
                return ListeningStatus.LISTENING;
            }

            //25 [机器人]群 "坦白说" 功能状态改变（事件）
            @EventHandler
            public ListeningStatus GroupAllowConfessTalkEvent(GroupAllowConfessTalkEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                boolean bot = event.isByBot();
                GroupAllowConfessTalkEventPack pack = new GroupAllowConfessTalkEventPack(id, old, new_, bot);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(25, data));
                return ListeningStatus.LISTENING;
            }

            //26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）
            @EventHandler
            public ListeningStatus GroupAllowMemberInviteEvent(GroupAllowMemberInviteEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                GroupAllowMemberInviteEventPack pack = new GroupAllowMemberInviteEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(26, data));
                return ListeningStatus.LISTENING;
            }

            //27 [机器人]入群公告改变（事件）
            @EventHandler
            public ListeningStatus GroupEntranceAnnouncementChangeEvent(GroupEntranceAnnouncementChangeEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                String old = event.getOrigin();
                String new_ = event.getNew();
                GroupEntranceAnnouncementChangeEventPack pack =
                        new GroupEntranceAnnouncementChangeEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(27, data));
                return ListeningStatus.LISTENING;
            }

            //28 [机器人]在群消息发送后广播（事件）
            @EventHandler
            public ListeningStatus GroupMessagePostSendEvent(GroupMessagePostSendEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getTarget().getId();
                boolean res = event.getReceipt() != null;
                MessageChain message = event.getMessage();
                String error = "";
                if (event.getException() != null) {
                    error = event.getException().getMessage();
                }
                GroupMessagePostSendEventPack pack = new GroupMessagePostSendEventPack(id, res, message, error);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(28, data));
                return ListeningStatus.LISTENING;
            }

            //处理在处理事件中发生的未捕获异常
            @Override
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                Start.logger.error("在事件处理中发生异常" + "\n" + context, exception);
            }
        });

        isRun = true;
        EventDo = new Thread(() -> {
            while (isRun) {
                try {
                    if (!Tasks.isEmpty()) {
                        Task Task = Tasks.remove(0);
                        eventCallToPlugin((byte) Task.index, Task.data);
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    Start.logger.error("插件处理事件出现问题", e);
                }
            }
        });
        EventDo.start();
        Start.logger.info("机器人已启动");
        return true;
    }

    public static void eventCallToPlugin(byte index, byte[] data) {
        byte[] temp = new byte[data.length + 1];
        temp[data.length] = index;
        for (Plugins item : SocketServer.PluginList.values()) {
            item.callEvent(index, temp);
        }
    }

    public static boolean sendGroupMessage(long group, String message) {
        try {
            bot.getGroup(group).sendMessage(message);
            return true;
        } catch (Exception e) {
            Start.logger.error("发送群消息失败", e);
            return false;
        }
    }

    static class Task {
        public int index;
        public byte[] data;

        public Task(int index, byte[] data) {
            this.index = index;
            this.data = data;
        }
    }
}
