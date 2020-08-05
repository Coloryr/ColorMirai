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
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
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
                long eventid = EventCall.AddEvent(new EventBase(event.getEventId(), (byte) 4, event));
                BotInvitedJoinGroupRequestEventPack pack =
                        new BotInvitedJoinGroupRequestEventPack(name, id, fid, eventid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(4, data));

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

            //29 [机器人]在发送群消息前广播（事件）
            @EventHandler
            public ListeningStatus GroupMessagePreSendEvent(GroupMessagePreSendEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getTarget().getId();
                Message message = event.getMessage();
                GroupMessagePreSendEventPack pack = new GroupMessagePreSendEventPack(id, message);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(29, data));
                return ListeningStatus.LISTENING;
            }

            //30 [机器人]群 "全员禁言" 功能状态改变（事件）
            @EventHandler
            public ListeningStatus GroupMuteAllEvent(GroupMuteAllEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                GroupMuteAllEventPack pack = new GroupMuteAllEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(30, data));
                return ListeningStatus.LISTENING;
            }

            //31 [机器人]群名改变（事件）
            @EventHandler
            public ListeningStatus GroupNameChangeEvent(GroupNameChangeEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                String old = event.getOrigin();
                String new_ = event.getNew();
                GroupNameChangeEventPack pack = new GroupNameChangeEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(31, data));
                return ListeningStatus.LISTENING;
            }

            //32 [机器人]图片上传成功（事件）
            @EventHandler
            public ListeningStatus ImageUploadEventA(ImageUploadEvent.Succeed event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getTarget().getId();
                String name = event.getImage().getImageId();
                ImageUploadEventAPack pack = new ImageUploadEventAPack(id, name);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(32, data));
                return ListeningStatus.LISTENING;
            }

            //33 [机器人]图片上传失败（事件）
            @EventHandler
            public ListeningStatus ImageUploadEventB(ImageUploadEvent.Failed event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getTarget().getId();
                String name = event.getSource().toString();
                String error = event.getMessage();
                int index = event.getErrno();
                ImageUploadEventBPack pack = new ImageUploadEventBPack(id, name, error, index);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(33, data));
                return ListeningStatus.LISTENING;
            }

            //34 [机器人]成员群名片改动（事件）
            @EventHandler
            public ListeningStatus MemberCardChangeEvent(MemberCardChangeEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String old = event.getOrigin();
                String new_ = event.getNew();
                MemberCardChangeEventPack pack = new MemberCardChangeEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(34, data));
                return ListeningStatus.LISTENING;
            }

            //35 [机器人]成成员被邀请加入群（事件）
            @EventHandler
            public ListeningStatus MemberJoinEventA(MemberJoinEvent.Invite event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                MemberJoinEventAPack pack = new MemberJoinEventAPack(id, fid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(35, data));
                return ListeningStatus.LISTENING;
            }

            //36 [机器人]成员主动加入群（事件）
            @EventHandler
            public ListeningStatus MemberJoinEventB(MemberJoinEvent.Active event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                MemberJoinEventAPack pack = new MemberJoinEventAPack(id, fid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(36, data));
                return ListeningStatus.LISTENING;
            }

            //37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
            @EventHandler
            public ListeningStatus MemberJoinRequestEvent(MemberJoinRequestEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getFromId();
                String message = event.getMessage();
                long eventid = EventCall.AddEvent(new EventBase(event.getEventId(), 37, event));
                MemberJoinRequestEventPack pack = new MemberJoinRequestEventPack(id, fid, message, eventid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(37, data));
                return ListeningStatus.LISTENING;
            }

            //38 [机器人]成员被踢出群（事件）
            @EventHandler
            public ListeningStatus MemberLeaveEventA(MemberLeaveEvent.Kick event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                long eid = 0;
                if (event.getOperator() != null) {
                    eid = event.getOperator().getId();
                }
                MemberLeaveEventAPack pack = new MemberLeaveEventAPack(id, fid, eid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(38, data));
                return ListeningStatus.LISTENING;
            }

            //39 [机器人]成员主动离开（事件）
            @EventHandler
            public ListeningStatus MemberLeaveEventB(MemberLeaveEvent.Quit event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                MemberLeaveEventBPack pack = new MemberLeaveEventBPack(id, fid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(39, data));
                return ListeningStatus.LISTENING;
            }

            //40 [机器人]群成员被禁言（事件）
            @EventHandler
            public ListeningStatus MemberMuteEvent(MemberMuteEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                long eid = 0;
                if (event.getOperator() != null) {
                    eid = event.getOperator().getId();
                }
                MemberMuteEventPack pack = new MemberMuteEventPack(id, fid, eid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(40, data));
                return ListeningStatus.LISTENING;
            }

            //41 [机器人]成员权限改变（事件）
            @EventHandler
            public ListeningStatus MemberPermissionChangeEvent(MemberPermissionChangeEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String old = event.getOrigin().name();
                String new_ = event.getNew().name();
                MemberPermissionChangeEventPack pack = new MemberPermissionChangeEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(41, data));
                return ListeningStatus.LISTENING;
            }

            //42 [机器人]成员群头衔改动（事件）
            @EventHandler
            public ListeningStatus MemberSpecialTitleChangeEvent(MemberSpecialTitleChangeEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String old = event.getOrigin();
                String new_ = event.getNew();
                MemberSpecialTitleChangeEventPack pack = new MemberSpecialTitleChangeEventPack(id, fid, old, new_);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(42, data));
                return ListeningStatus.LISTENING;
            }

            //43 [机器人]群成员被取消禁言（事件）
            @EventHandler
            public ListeningStatus MemberUnmuteEvent(MemberUnmuteEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                MemberUnmuteEventPack pack = new MemberUnmuteEventPack(id, fid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(43, data));
                return ListeningStatus.LISTENING;
            }

            //44 [机器人]好友消息撤回（事件）
            @EventHandler
            public ListeningStatus MessageRecallEventA(MessageRecallEvent.FriendRecall event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getAuthorId();
                int mid = event.getMessageId();
                int time = event.getMessageTime();
                MessageRecallEventAPack pack = new MessageRecallEventAPack(id, mid, time);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(44, data));
                return ListeningStatus.LISTENING;
            }

            //45 [机器人]群消息撤回事件（事件）
            @EventHandler
            public ListeningStatus MessageRecallEventB(MessageRecallEvent.GroupRecall event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getAuthorId();
                int mid = event.getMessageId();
                int time = event.getMessageTime();
                MessageRecallEventBPack pack = new MessageRecallEventBPack(id, fid, mid, time);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(45, data));
                return ListeningStatus.LISTENING;
            }

            //46 [机器人]一个账号请求添加机器人为好友（事件）
            @EventHandler
            public ListeningStatus NewFriendRequestEvent(NewFriendRequestEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getFromGroupId();
                long fid = event.getFromId();
                String name = event.getFromNick();
                String message = event.getMessage();
                long eventid = EventCall.AddEvent(new EventBase(event.getEventId(), 46, event));
                NewFriendRequestEventPack pack = new NewFriendRequestEventPack(id, fid, name, message, eventid);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(46, data));
                return ListeningStatus.LISTENING;
            }

            //47 [机器人]在群临时会话消息发送后广播（事件）
            @EventHandler
            public ListeningStatus TempMessagePostSendEvent(TempMessagePostSendEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getTarget().getId();
                boolean res = event.getReceipt() != null;
                MessageChain message = event.getMessage();
                String error = "";
                if (event.getException() != null) {
                    error = event.getException().getMessage();
                }
                TempMessagePostSendEventPack pack = new TempMessagePostSendEventPack(id, fid, res, message, error);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(47, data));
                return ListeningStatus.LISTENING;
            }

            //48 [机器人在发送群临时会话消息前广播（事件）
            @EventHandler
            public ListeningStatus TempMessagePreSendEvent(TempMessagePreSendEvent event) {
                if (!SocketServer.havePlugin())
                    return ListeningStatus.LISTENING;
                long id = event.getGroup().getId();
                long fid = event.getTarget().getId();
                Message message = event.getMessage();
                TempMessagePreSendEventPack pack = new TempMessagePreSendEventPack(id, fid, message);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task(48, data));
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
