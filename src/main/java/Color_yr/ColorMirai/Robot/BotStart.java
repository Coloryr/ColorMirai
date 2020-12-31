package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.Config.QQsObj;
import Color_yr.ColorMirai.EventDo.EventBase;
import Color_yr.ColorMirai.EventDo.EventCall;
import Color_yr.ColorMirai.Pack.ReturnPlugin.FriendsPack;
import Color_yr.ColorMirai.Pack.ReturnPlugin.GroupsPack;
import Color_yr.ColorMirai.Pack.ReturnPlugin.MemberInfoPack;
import Color_yr.ColorMirai.Pack.ToPlugin.*;
import Color_yr.ColorMirai.Plugin.Objs.SendPackObj;
import Color_yr.ColorMirai.Plugin.PluginUtils;
import Color_yr.ColorMirai.Plugin.ThePlugin;
import Color_yr.ColorMirai.Start;
import com.alibaba.fastjson.JSON;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.action.Nudge;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.network.WrongPasswordException;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.*;

public class BotStart {

    private static final List<SendPackObj> Tasks = new CopyOnWriteArrayList<>();
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Map<Integer, MessageSaveObj> MessageLsit = new ConcurrentHashMap<>();
    private static final Map<Long, Bot> bots = new HashMap<>();
    private static final List<Integer> reList = new CopyOnWriteArrayList<>();

    private static Thread EventDo;
    private static boolean isRun;

    public static boolean Start() {
        for (QQsObj item : Start.Config.QQs) {
            Bot bot = BotFactory.INSTANCE.newBot(item.QQ, item.Password, new BotConfiguration() {
                {
                    fileBasedDeviceInfo(Start.RunDir + "info.json");
                    switch (Start.Config.LoginType) {
                        case 0:
                            setProtocol(MiraiProtocol.ANDROID_PHONE);
                            break;
                        case 1:
                            setProtocol(MiraiProtocol.ANDROID_WATCH);
                            break;
                        case 2:
                            setProtocol(MiraiProtocol.ANDROID_PAD);
                            break;
                    }
                    redirectNetworkLogToDirectory(new File(Start.RunDir + "/BotNetWork"));
                    redirectBotLogToDirectory(new File(Start.RunDir + "/BotLog"));
                }
            });
            try {
                bot.login();
                bots.put(item.QQ, bot);
                Start.logger.info("QQ:" + item.QQ + "已登录");
            } catch (WrongPasswordException e) {
                Start.logger.error("机器人密码错误");
            } catch (Exception e) {
                Start.logger.error("机器人错误", e);
                return false;
            }
        }
        if (bots.size() == 0) {
            Start.logger.error("没有QQ号登录");
            return false;
        }
        SimpleListenerHost host = new SimpleListenerHost() {
            //1 [机器人]图片上传前. 可以阻止上传（事件）
            @EventHandler
            public void BeforeImageUploadEvent(BeforeImageUploadEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                String name = event.getSource().toString();
                long id = event.getTarget().getId();
                long qq = event.getBot().getId();
                BeforeImageUploadPack pack = new BeforeImageUploadPack(qq, name, id);
                Tasks.add(new SendPackObj(1, JSON.toJSONString(pack), 0, 0, qq));

            }

            //2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）
            @EventHandler
            public void BotAvatarChangedEvent(BotAvatarChangedEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                String name = event.getBot().getNick();
                long qq = event.getBot().getId();
                BotAvatarChangedPack pack = new BotAvatarChangedPack(qq, name);
                Tasks.add(new SendPackObj(2, JSON.toJSONString(pack), 0, 0, qq));

            }

            //3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
            @EventHandler
            public void BotGroupPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long qq = event.getBot().getId();
                String name = event.getNew().name();
                BotGroupPermissionChangePack pack = new BotGroupPermissionChangePack(qq, name, id);
                Tasks.add(new SendPackObj(3, JSON.toJSONString(pack), 0, id, qq));

            }

            //4 [机器人]被邀请加入一个群（事件）
            @EventHandler
            public void BotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroupId();
                String name = "";
                if (event.getInvitor() != null) {
                    name = event.getInvitor().getNick();
                }
                long qq = event.getBot().getId();
                long fid = event.getInvitorId();
                long eventid = EventCall.AddEvent(new EventBase(qq, event.getEventId(), (byte) 4, event));
                BotInvitedJoinGroupRequestEventPack pack = new BotInvitedJoinGroupRequestEventPack(qq, name, id, fid, eventid);
                Tasks.add(new SendPackObj(4, JSON.toJSONString(pack), fid, id, qq));


            }

            //5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
            @EventHandler
            public void BotJoinGroupEventA(BotJoinGroupEvent.Active event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long qq = event.getBot().getId();
                BotJoinGroupEventAPack pack = new BotJoinGroupEventAPack(qq, id);
                Tasks.add(new SendPackObj(5, JSON.toJSONString(pack), 0, id, qq));

            }

            //6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
            @EventHandler
            public void BotJoinGroupEventB(BotJoinGroupEvent.Invite event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getInvitor().getId();
                long qq = event.getBot().getId();
                String name = event.getInvitor().getNick();
                BotJoinGroupEventBPack pack = new BotJoinGroupEventBPack(qq, name, id, fid);
                Tasks.add(new SendPackObj(6, JSON.toJSONString(pack), fid, id, qq));

            }

            //7 [机器人]主动退出一个群（事件）
            @EventHandler
            public void BotLeaveEventA(BotLeaveEvent.Active event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long qq = event.getBot().getId();
                BotLeaveEventAPack pack = new BotLeaveEventAPack(qq, id);
                Tasks.add(new SendPackObj(7, JSON.toJSONString(pack), 0, id, qq));

            }

            //8 [机器人]被管理员或群主踢出群（事件）
            @EventHandler
            public void BotLeaveEventB(BotLeaveEvent.Kick event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                String name = event.getOperator().getNick();
                long fid = event.getOperator().getId();
                long qq = event.getBot().getId();
                BotLeaveEventBPack pack = new BotLeaveEventBPack(qq, name, id, fid);
                Tasks.add(new SendPackObj(7, JSON.toJSONString(pack), fid, id, qq));

            }

            //9 [机器人]被禁言（事件）
            @EventHandler
            public void BotMuteEvent(BotMuteEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                int time = event.getDurationSeconds();
                long qq = event.getBot().getId();
                String name = event.getOperator().getNick();
                long fid = event.getOperator().getId();
                BotMuteEventPack pack = new BotMuteEventPack(qq, name, id, fid, time);
                Tasks.add(new SendPackObj(9, JSON.toJSONString(pack), fid, id, qq));

            }

            //10 [机器人]主动离线（事件）
            @EventHandler
            public void BotOfflineEventA(BotOfflineEvent.Active event) {
                if (PluginUtils.havePlugin())
                    return;
                String message = "";
                if (event.getCause() != null) {
                    message = event.getCause().getLocalizedMessage();
                }
                long qq = event.getBot().getId();
                BotOfflineEventAPack pack = new BotOfflineEventAPack(qq, message);
                Tasks.add(new SendPackObj(10, JSON.toJSONString(pack), 0, 0, qq));

            }

            //11 [机器人]被挤下线（事件）
            @EventHandler
            public void BotOfflineEventB(BotOfflineEvent.Force event) {
                if (PluginUtils.havePlugin())
                    return;
                String title = event.getTitle();
                String message = event.getMessage();
                long qq = event.getBot().getId();
                BotOfflineEventBPack pack = new BotOfflineEventBPack(qq, message, title);
                Tasks.add(new SendPackObj(11, JSON.toJSONString(pack), 0, 0, qq));

            }

            //12 [机器人]被服务器断开（事件）
            @EventHandler
            public void BotOfflineEventC(BotOfflineEvent.MsfOffline event) {
                if (PluginUtils.havePlugin())
                    return;
                String message = "";
                if (event.getCause() != null) {
                    message = event.getCause().getLocalizedMessage();
                }
                long qq = event.getBot().getId();
                BotOfflineEventAPack pack = new BotOfflineEventAPack(qq, message);
                Tasks.add(new SendPackObj(12, JSON.toJSONString(pack), 0, 0, qq));

            }

            //13 [机器人]因网络问题而掉线（事件）
            @EventHandler
            public void BotOfflineEventD(BotOfflineEvent.Dropped event) {
                if (PluginUtils.havePlugin())
                    return;
                String message = "";
                if (event.getCause() != null) {
                    message = event.getCause().getLocalizedMessage();
                }
                long qq = event.getBot().getId();
                BotOfflineEventAPack pack = new BotOfflineEventAPack(qq, message);
                Tasks.add(new SendPackObj(13, JSON.toJSONString(pack), 0, 0, qq));

            }

            //14 [机器人]服务器主动要求更换另一个服务器（事件）
            @EventHandler
            public void BotOfflineEventE(BotOfflineEvent.RequireReconnect event) {
                if (PluginUtils.havePlugin())
                    return;
                long qq = event.getBot().getId();
                BotOfflineEventCPack pack = new BotOfflineEventCPack(qq);
                Tasks.add(new SendPackObj(14, JSON.toJSONString(pack), 0, 0, qq));

            }

            //15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）
            @EventHandler
            public void BotOnlineEvent(BotOnlineEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long qq = event.getBot().getId();
                BotOnlineEventPack pack = new BotOnlineEventPack(qq);
                Tasks.add(new SendPackObj(15, JSON.toJSONString(pack), 0, 0, qq));

            }

            //16 [机器人]主动或被动重新登录（事件）
            @EventHandler
            public void BotReloginEvent(BotReloginEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                String message = "";
                if (event.getCause() != null) {
                    message = event.getCause().getMessage();
                }
                long qq = event.getBot().getId();
                BotReloginEventPack pack = new BotReloginEventPack(qq, message);
                Tasks.add(new SendPackObj(16, JSON.toJSONString(pack), 0, 0, qq));

            }

            //17 [机器人]被取消禁言（事件）
            @EventHandler
            public void BotUnmuteEvent(BotUnmuteEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getOperator().getId();
                long qq = event.getBot().getId();
                BotUnmuteEventPack pack = new BotUnmuteEventPack(qq, id, fid);
                Tasks.add(new SendPackObj(17, JSON.toJSONString(pack), fid, id, qq));

            }

            //18 [机器人]成功添加了一个新好友（事件）
            @EventHandler
            public void FriendAddEvent(FriendAddEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getFriend().getId();
                String name = event.getFriend().getNick();
                long qq = event.getBot().getId();
                FriendAddEventPack pack = new FriendAddEventPack(qq, name, id);
                Tasks.add(new SendPackObj(18, JSON.toJSONString(pack), id, 0, qq));

            }

            //19 [机器人]好友头像被修改（事件）
            @EventHandler
            public void FriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getFriend().getId();
                String name = event.getFriend().getNick();
                long qq = event.getBot().getId();
                String url = event.getFriend().getAvatarUrl();
                FriendAvatarChangedEventPack pack = new FriendAvatarChangedEventPack(qq, name, id, url);
                Tasks.add(new SendPackObj(19, JSON.toJSONString(pack), id, 0, qq));

            }

            //20 [机器人]好友已被删除（事件）
            @EventHandler
            public void FriendDeleteEvent(FriendDeleteEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getFriend().getId();
                long qq = event.getBot().getId();
                String name = event.getFriend().getNick();
                FriendDeleteEventPack pack = new FriendDeleteEventPack(qq, name, id);
                Tasks.add(new SendPackObj(20, JSON.toJSONString(pack), id, 0, qq));

            }

            //21 [机器人]在好友消息发送后广播（事件）
            @EventHandler
            public void FriendMessagePostSendEvent(FriendMessagePostSendEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getTarget().getId();
                String name = event.getTarget().getNick();
                boolean res = event.getReceipt() != null;
                MessageSource message = null;
                if (res) {
                    message = event.getReceipt().getSource();
                }
                String error = "";
                if (event.getException() != null) {
                    error = event.getException().getMessage();
                }
                long qq = event.getBot().getId();
                FriendMessagePostSendEventPack pack = new FriendMessagePostSendEventPack(qq, message, id, name, res, error);
                Tasks.add(new SendPackObj(21, JSON.toJSONString(pack), id, 0, qq));

            }

            //22 [机器人]在发送好友消息前广播（事件）
            @EventHandler
            public void FriendMessagePreSendEvent(FriendMessagePreSendEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                Message message = event.getMessage();
                long id = event.getTarget().getId();
                long qq = event.getBot().getId();
                String name = event.getTarget().getNick();
                FriendMessagePreSendEventPack pack = new FriendMessagePreSendEventPack(qq, message, id, name);
                Tasks.add(new SendPackObj(22, JSON.toJSONString(pack), id, 0, qq));

            }

            //23 [机器人]好友昵称改变（事件）
            @EventHandler
            public void FriendRemarkChangeEvent(FriendRemarkChangeEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getFriend().getId();
                String old = event.getOldRemark();
                String name = event.getNewRemark();
                long qq = event.getBot().getId();
                FriendRemarkChangeEventPack pack = new FriendRemarkChangeEventPack(qq, id, old, name);
                Tasks.add(new SendPackObj(23, JSON.toJSONString(pack), id, 0, qq));

            }

            //24 [机器人]群 "匿名聊天" 功能状态改变（事件）
            @EventHandler
            public void GroupAllowAnonymousChatEvent(GroupAllowAnonymousChatEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                long qq = event.getBot().getId();
                GroupAllowAnonymousChatEventPack pack = new GroupAllowAnonymousChatEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(24, JSON.toJSONString(pack), 0, id, qq));

            }

            //25 [机器人]群 "坦白说" 功能状态改变（事件）
            @EventHandler
            public void GroupAllowConfessTalkEvent(GroupAllowConfessTalkEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                boolean bot = event.isByBot();
                long qq = event.getBot().getId();
                GroupAllowConfessTalkEventPack pack = new GroupAllowConfessTalkEventPack(qq, id, old, new_, bot);
                Tasks.add(new SendPackObj(25, JSON.toJSONString(pack), 0, id, qq));

            }

            //26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）
            @EventHandler
            public void GroupAllowMemberInviteEvent(GroupAllowMemberInviteEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                long qq = event.getBot().getId();
                GroupAllowMemberInviteEventPack pack = new GroupAllowMemberInviteEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(26, JSON.toJSONString(pack), fid, id, qq));

            }

            //27 [机器人]入群公告改变（事件）
            @EventHandler
            public void GroupEntranceAnnouncementChangeEvent(GroupEntranceAnnouncementChangeEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                String old = event.getOrigin();
                String new_ = event.getNew();
                long qq = event.getBot().getId();
                GroupEntranceAnnouncementChangeEventPack pack = new GroupEntranceAnnouncementChangeEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(27, JSON.toJSONString(pack), fid, id, qq));

            }

            //28 [机器人]在群消息发送后广播（事件）
            @EventHandler
            public void GroupMessagePostSendEvent(GroupMessagePostSendEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getTarget().getId();
                boolean res = event.getReceipt() != null;
                MessageSource message = null;
                if (res) {
                    message = event.getReceipt().getSource();
                }
                String error = "";
                if (event.getException() != null) {
                    error = event.getException().getMessage();
                }
                long qq = event.getBot().getId();
                GroupMessagePostSendEventPack pack = new GroupMessagePostSendEventPack(qq, id, res, message, error);
                Tasks.add(new SendPackObj(28, JSON.toJSONString(pack), 0, id, qq));

            }

            //29 [机器人]在发送群消息前广播（事件）
            @EventHandler
            public void GroupMessagePreSendEvent(GroupMessagePreSendEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getTarget().getId();
                Message message = event.getMessage();
                long qq = event.getBot().getId();
                GroupMessagePreSendEventPack pack = new GroupMessagePreSendEventPack(qq, id, message);
                Tasks.add(new SendPackObj(29, JSON.toJSONString(pack), 0, id, qq));

            }

            //30 [机器人]群 "全员禁言" 功能状态改变（事件）
            @EventHandler
            public void GroupMuteAllEvent(GroupMuteAllEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                boolean old = event.getOrigin();
                boolean new_ = event.getNew();
                long qq = event.getBot().getId();
                GroupMuteAllEventPack pack = new GroupMuteAllEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(30, JSON.toJSONString(pack), fid, id, qq));

            }

            //31 [机器人]群名改变（事件）
            @EventHandler
            public void GroupNameChangeEvent(GroupNameChangeEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = 0;
                if (event.getOperator() != null) {
                    fid = event.getOperator().getId();
                }
                String old = event.getOrigin();
                String new_ = event.getNew();
                long qq = event.getBot().getId();
                GroupNameChangeEventPack pack = new GroupNameChangeEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(31, JSON.toJSONString(pack), fid, id, qq));

            }

            //32 [机器人]图片上传成功（事件）
            @EventHandler
            public void ImageUploadEventA(ImageUploadEvent.Succeed event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getTarget().getId();
                String name = event.getImage().getImageId();
                long qq = event.getBot().getId();
                ImageUploadEventAPack pack = new ImageUploadEventAPack(qq, id, name);
                Tasks.add(new SendPackObj(32, JSON.toJSONString(pack), 0, id, qq));

            }

            //33 [机器人]图片上传失败（事件）
            @EventHandler
            public void ImageUploadEventB(ImageUploadEvent.Failed event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getTarget().getId();
                String name = event.getSource().toString();
                String error = event.getMessage();
                int index = event.getErrno();
                long qq = event.getBot().getId();
                ImageUploadEventBPack pack = new ImageUploadEventBPack(qq, id, name, error, index);
                Tasks.add(new SendPackObj(33, JSON.toJSONString(pack), 0, id, qq));

            }

            //34 [机器人]成员群名片改动（事件）
            @EventHandler
            public void MemberCardChangeEvent(MemberCardChangeEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String old = event.getOrigin();
                String new_ = event.getNew();
                long qq = event.getBot().getId();
                MemberCardChangeEventPack pack = new MemberCardChangeEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(34, JSON.toJSONString(pack), fid, id, qq));

            }

            //35 [机器人]成成员被邀请加入群（事件）
            @EventHandler
            public void MemberJoinEventA(MemberJoinEvent.Invite event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String name = event.getMember().getNameCard();
                long qq = event.getBot().getId();
                MemberJoinEventAPack pack = new MemberJoinEventAPack(qq, id, fid, name);
                Tasks.add(new SendPackObj(35, JSON.toJSONString(pack), fid, id, qq));

            }

            //36 [机器人]成员主动加入群（事件）
            @EventHandler
            public void MemberJoinEventB(MemberJoinEvent.Active event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String name = event.getMember().getNameCard();
                long qq = event.getBot().getId();
                MemberJoinEventAPack pack = new MemberJoinEventAPack(qq, id, fid, name);
                Tasks.add(new SendPackObj(36, JSON.toJSONString(pack), fid, id, qq));

            }

            //37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
            @EventHandler
            public void MemberJoinRequestEvent(MemberJoinRequestEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = 0;
                if (event.getGroup() != null) {
                    id = event.getGroup().getId();
                }
                long fid = event.getFromId();
                String message = event.getMessage();
                long qq = event.getBot().getId();
                long eventid = EventCall.AddEvent(new EventBase(qq, event.getEventId(), 37, event));
                MemberJoinRequestEventPack pack = new MemberJoinRequestEventPack(qq, id, fid, message, eventid);
                Tasks.add(new SendPackObj(37, JSON.toJSONString(pack), fid, id, qq));

            }

            //38 [机器人]成员被踢出群（事件）
            @EventHandler
            public void MemberLeaveEventA(MemberLeaveEvent.Kick event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String fname = event.getMember().getNameCard();
                String ename = "";
                long eid = 0;
                if (event.getOperator() != null) {
                    eid = event.getOperator().getId();
                    ename = event.getOperator().getNameCard();
                }
                long qq = event.getBot().getId();
                MemberLeaveEventAPack pack = new MemberLeaveEventAPack(qq, id, fid, eid, fname, ename);
                Tasks.add(new SendPackObj(38, JSON.toJSONString(pack), fid, id, qq));

            }

            //39 [机器人]成员主动离开（事件）
            @EventHandler
            public void MemberLeaveEventB(MemberLeaveEvent.Quit event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String name = event.getMember().getNameCard();
                long qq = event.getBot().getId();
                MemberLeaveEventBPack pack = new MemberLeaveEventBPack(qq, id, fid, name);
                Tasks.add(new SendPackObj(39, JSON.toJSONString(pack), fid, id, qq));

            }

            //40 [机器人]群成员被禁言（事件）
            @EventHandler
            public void MemberMuteEvent(MemberMuteEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                long eid = 0;
                String fname = event.getMember().getNameCard();
                String ename = "";
                int time = event.getDurationSeconds();
                if (event.getOperator() != null) {
                    eid = event.getOperator().getId();
                    ename = event.getOperator().getNameCard();
                }
                long qq = event.getBot().getId();
                MemberMuteEventPack pack = new MemberMuteEventPack(qq, id, fid, eid, fname, ename, time);
                Tasks.add(new SendPackObj(40, JSON.toJSONString(pack), fid, id, qq));

            }

            //41 [机器人]成员权限改变（事件）
            @EventHandler
            public void MemberPermissionChangeEvent(MemberPermissionChangeEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String old = event.getOrigin().name();
                String new_ = event.getNew().name();
                long qq = event.getBot().getId();
                MemberPermissionChangeEventPack pack = new MemberPermissionChangeEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(41, JSON.toJSONString(pack), fid, id, qq));

            }

            //42 [机器人]成员群头衔改动（事件）
            @EventHandler
            public void MemberSpecialTitleChangeEvent(MemberSpecialTitleChangeEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String old = event.getOrigin();
                String new_ = event.getNew();
                long qq = event.getBot().getId();
                MemberSpecialTitleChangeEventPack pack = new MemberSpecialTitleChangeEventPack(qq, id, fid, old, new_);
                Tasks.add(new SendPackObj(42, JSON.toJSONString(pack), fid, id, qq));
            }

            //43 [机器人]群成员被取消禁言（事件）
            @EventHandler
            public void MemberUnmuteEvent(MemberUnmuteEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                long eid = 0;
                String fname = event.getMember().getNameCard();
                String ename = "";
                if (event.getOperator() != null) {
                    eid = event.getOperator().getId();
                    ename = event.getOperator().getNameCard();
                }
                long qq = event.getBot().getId();
                MemberUnmuteEventPack pack = new MemberUnmuteEventPack(qq, id, fid, eid, fname, ename);
                Tasks.add(new SendPackObj(43, JSON.toJSONString(pack), fid, id, qq));
            }

            //44 [机器人]好友消息撤回（事件）
            @EventHandler
            public void MessageRecallEventA(MessageRecallEvent.FriendRecall event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getAuthorId();
                int[] mid = event.getMessageIds();
                long qq = event.getBot().getId();
                int time = event.getMessageTime();
                MessageRecallEventAPack pack = new MessageRecallEventAPack(qq, id, mid, time);
                Tasks.add(new SendPackObj(44, JSON.toJSONString(pack), id, 0, qq));
            }

            //45 [机器人]群消息撤回事件（事件）
            @EventHandler
            public void MessageRecallEventB(MessageRecallEvent.GroupRecall event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getAuthorId();
                int[] mid = event.getMessageIds();
                int time = event.getMessageTime();
                long oid = 0;
                String oanme = "";
                if (event.getOperator() != null) {
                    oid = event.getOperator().getId();
                    oanme = event.getOperator().getNameCard();
                }
                long qq = event.getBot().getId();
                MessageRecallEventBPack pack = new MessageRecallEventBPack(qq, id, fid, mid, time, oid, oanme);
                Tasks.add(new SendPackObj(45, JSON.toJSONString(pack), fid, id, qq));
            }

            //46 [机器人]一个账号请求添加机器人为好友（事件）
            @EventHandler
            public void NewFriendRequestEvent(NewFriendRequestEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getFromGroupId();
                long fid = event.getFromId();
                String name = event.getFromNick();
                String message = event.getMessage();
                long qq = event.getBot().getId();
                long eventid = EventCall.AddEvent(new EventBase(qq, event.getEventId(), 46, event));
                NewFriendRequestEventPack pack = new NewFriendRequestEventPack(qq, id, fid, name, message, eventid);
                Tasks.add(new SendPackObj(46, JSON.toJSONString(pack), fid, id, qq));
            }

            //47 [机器人]在群临时会话消息发送后广播（事件）
            @EventHandler
            public void TempMessagePostSendEvent(TempMessagePostSendEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getTarget().getId();
                boolean res = event.getReceipt() != null;
                MessageSource message = null;
                if (res) {
                    message = event.getReceipt().getSource();
                }
                String error = "";
                if (event.getException() != null) {
                    error = event.getException().getMessage();
                }
                long qq = event.getBot().getId();
                TempMessagePostSendEventPack pack = new TempMessagePostSendEventPack(qq, id, fid, res, message, error);
                Tasks.add(new SendPackObj(47, JSON.toJSONString(pack), fid, id, qq));
            }

            //48 [机器人]在发送群临时会话消息前广播（事件）
            @EventHandler
            public void TempMessagePreSendEvent(TempMessagePreSendEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getTarget().getId();
                String fname = event.getTarget().getNameCard();
                Message message = event.getMessage();
                long qq = event.getBot().getId();
                TempMessagePreSendEventPack pack = new TempMessagePreSendEventPack(qq, id, fid, message, fname);
                Tasks.add(new SendPackObj(48, JSON.toJSONString(pack), fid, id, qq));
            }

            //49 [机器人]收到群消息（事件）
            @EventHandler
            public void GroupMessageEvent(GroupMessageEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getSubject().getId();
                long fid = event.getSender().getId();
                if (Start.Config.escapeSelf && bots.containsKey(fid))
                    return;
                String name = event.getSender().getNameCard();
                MessageChain message = event.getMessage();
                MessageSaveObj call = new MessageSaveObj();
                call.sourceQQ = event.getBot().getId();
                call.source = event.getSource();
                call.time = -1;
                call.id = call.source.getIds()[0];
                MessageLsit.put(call.id, call);
                long qq = event.getBot().getId();
                GroupMessageEventPack pack = new GroupMessageEventPack(qq, id, fid, name, message);
                Tasks.add(new SendPackObj(49, JSON.toJSONString(pack), fid, id, qq));
            }

            //50 [机器人]收到群临时会话消息（事件）
            @EventHandler
            public void TempMessageEvent(TempMessageEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getSender().getId();
                String name = event.getSenderName();
                MessageChain message = event.getMessage();
                MessageSaveObj call = new MessageSaveObj();
                call.sourceQQ = event.getBot().getId();
                call.source = event.getSource();
                call.time = -1;
                call.id = call.source.getIds()[0];
                MessageLsit.put(call.id, call);
                int time = event.getTime();
                long qq = event.getBot().getId();
                TempMessageEventPack pack = new TempMessageEventPack(qq, id, fid, name, message, time);
                Tasks.add(new SendPackObj(50, JSON.toJSONString(pack), fid, id, qq));
            }

            //51 [机器人]收到朋友消息（事件）
            @EventHandler
            public void FriendMessageEvent(FriendMessageEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getSender().getId();
                String name = event.getSenderName();
                MessageChain message = event.getMessage();
                MessageSaveObj call = new MessageSaveObj();
                call.sourceQQ = event.getBot().getId();
                call.source = event.getSource();
                call.time = -1;
                call.id = call.source.getIds()[0];
                MessageLsit.put(call.id, call);
                int time = event.getTime();
                long qq = event.getBot().getId();
                FriendMessageEventPack pack = new FriendMessageEventPack(qq, id, name, message, time);
                Tasks.add(new SendPackObj(51, JSON.toJSONString(pack), id, 0, qq));
            }

            //72 [机器人]友输入状态改变（事件）
            @EventHandler
            public void FriendInputStatusChangedEvent(FriendInputStatusChangedEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getFriend().getId();
                String name = event.getFriend().getNick();
                boolean input = event.getInputting();
                long qq = event.getBot().getId();
                FriendInputStatusChangedEventPack pack = new FriendInputStatusChangedEventPack(qq, id, name, input);
                Tasks.add(new SendPackObj(72, JSON.toJSONString(pack), id, 0, qq));
            }

            //79 [插件]成员群恢复（事件）
            @EventHandler
            public void MemberJoinRetrieveEvent(MemberJoinEvent.Retrieve event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                long qq = event.getBot().getId();
                String name = event.getMember().getNameCard();
                MemberJoinRetrieveEventPack pack = new MemberJoinRetrieveEventPack(qq, id, fid, name);
                Tasks.add(new SendPackObj(79, JSON.toJSONString(pack), fid, id, qq));
            }

            //80 [插件]机器人群恢复（事件）
            @EventHandler
            public void BotJoinGroupEventRetrieveEvent(BotJoinGroupEvent.Retrieve event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long qq = event.getBot().getId();
                BotJoinGroupEventRetrieveEventPack pack = new BotJoinGroupEventRetrieveEventPack(qq, id);
                Tasks.add(new SendPackObj(80, JSON.toJSONString(pack), 0, id, qq));
            }

            //81 [插件]群成员戳一戳（事件）
            @EventHandler
            public void MemberNudgedEvent(MemberNudgedEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                long fid = event.getMember().getId();
                String name = event.getMember().getNameCard();
                String action = event.getAction();
                String suffix = event.getSuffix();
                long qq = event.getBot().getId();
                MemberNudgedEventPack pack = new MemberNudgedEventPack(qq, id, fid, name, action, suffix);
                Tasks.add(new SendPackObj(81, JSON.toJSONString(pack), fid, id, qq));
            }

            //82 [插件]机器人戳一戳（事件）
            @EventHandler
            public void BotNudgedEvent(BotNudgedEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getFrom().getId();
                String action = event.getAction();
                String suffix = event.getSuffix();
                long qq = event.getBot().getId();
                BotNudgedEventPack pack = new BotNudgedEventPack(qq, id, action, suffix);
                Tasks.add(new SendPackObj(82, JSON.toJSONString(pack), id, 0, qq));
            }

            //86 [机器人]其他客户端上线（事件）
            @EventHandler
            public void OtherClientOnlineEvent(OtherClientOnlineEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                int appId = event.getClient().getInfo().getAppId();
                String kind = "";
                if (event.getKind() != null) {
                    kind = event.getKind().name();
                }
                String platform = event.getClient().getInfo().getPlatform().toString();
                String deviceName = event.getClient().getInfo().getDeviceName();
                String deviceKind = event.getClient().getInfo().getDeviceKind();
                long qq = event.getBot().getId();
                OtherClientOnlineEventPack pack = new OtherClientOnlineEventPack(qq, appId, kind, platform, deviceName, deviceKind);
                Tasks.add(new SendPackObj(86, JSON.toJSONString(pack), qq, 0, qq));
            }

            //87 [机器人]其他客户端离线（事件）
            @EventHandler
            public void OtherClientOfflineEvent(OtherClientOfflineEvent event) {
                if (PluginUtils.havePlugin())
                    return;
           int appId = event.getClient().getInfo().getAppId();
                String platform = event.getClient().getInfo().getPlatform().toString();
                String deviceName = event.getClient().getInfo().getDeviceName();
                String deviceKind = event.getClient().getInfo().getDeviceKind();
                long qq = event.getBot().getId();
                OtherClientOfflineEventPack pack = new OtherClientOfflineEventPack(qq, appId, platform, deviceName, deviceKind);
                Tasks.add(new SendPackObj(87, JSON.toJSONString(pack), qq, 0, qq));
            }

            //88 [机器人]其他客户端发送消息给 Bot（事件）
            @EventHandler
            public void OtherClientMessageEvent(OtherClientMessageEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                int appId = event.getClient().getInfo().getAppId();
                String platform = event.getClient().getInfo().getPlatform().toString();
                String deviceName = event.getClient().getInfo().getDeviceName();
                String deviceKind = event.getClient().getInfo().getDeviceKind();
                String senderName = event.getSenderName();
                MessageChain message = event.getMessage();
                long qq = event.getBot().getId();
                OtherClientMessageEventPack pack = new OtherClientMessageEventPack(qq, appId, platform, deviceName, deviceKind, senderName, message);
                Tasks.add(new SendPackObj(88, JSON.toJSONString(pack), qq, 0, qq));
            }

            //89 [机器人]他客户端发送群消息（事件）
            @EventHandler
            public void GroupMessageSyncEvent(GroupMessageSyncEvent event) {
                if (PluginUtils.havePlugin())
                    return;
                long id = event.getGroup().getId();
                int time = event.getTime();
                String senderName = event.getSenderName();
                MessageChain message = event.getMessage();
                long qq = event.getBot().getId();
                GroupMessageSyncEventPack pack = new GroupMessageSyncEventPack(qq, id, time, senderName, message);
                Tasks.add(new SendPackObj(89, JSON.toJSONString(pack), qq, 0, qq));
            }

            //处理在处理事件中发生的未捕获异常
            @Override
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                Start.logger.error("在事件处理中发生异常" + "\n" + context, exception);
            }
        };
        for (Bot item : bots.values()) {
            item.getEventChannel().registerListenerHost(host);
            break;
        }

        isRun = true;
        EventDo = new Thread(() -> {
            while (isRun) {
                try {
                    if (!Tasks.isEmpty()) {
                        SendPackObj task = Tasks.remove(0);
                        task.data += " ";
                        byte[] temp = task.data.getBytes(Start.SendCharset);
                        temp[temp.length - 1] = task.index;
                        for (ThePlugin item : PluginUtils.getAll()) {
                            item.callEvent(task, temp);
                        }
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    Start.logger.error("插件处理事件出现问题", e);
                }
            }
        });
        EventDo.start();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            if (!reList.isEmpty()) {
                for (int item : reList) {
                    if (MessageLsit.containsKey(item)) {
                        MessageSaveObj item1 = MessageLsit.remove(item);
                        if (item1.time > 0 || item1.time == -1)
                            try {
                                Mirai.getInstance().recallMessage(bots.get(item1.sourceQQ), item1.source);
                            } catch (Exception e) {
                                Start.logger.error("消息撤回失败", e);
                            }
                    }
                }
                reList.clear();
            }
            for (Map.Entry<Integer, MessageSaveObj> item : MessageLsit.entrySet()) {
                MessageSaveObj call = item.getValue();
                if (call.time > 0)
                    call.time--;
                if (call.time != 0) {
                    MessageLsit.put(item.getKey(), call);
                }
            }
            if (MessageLsit.size() >= Start.Config.MaxList) {
                MessageLsit.clear();
            }
        }, 0, 1, TimeUnit.SECONDS);
        Start.logger.info("机器人已启动");
        return true;
    }

    public static void addTask(SendPackObj task) {
        Tasks.add(task);
    }

    public static void stop() {
        try {
            isRun = false;
            if (bots.size() != 0)
                for (Bot item : bots.values()) {
                    item.close(new Throwable());
                }
            bots.clear();
            if (EventDo != null)
                EventDo.join();
        } catch (Exception e) {
            Start.logger.error("关闭机器人时出现错误", e);
        }
    }

    public static void sendGroupMessage(long qq, long group, List<String> message) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Group group1 = bots.get(qq).getGroup(group);
            if(group1 == null) {
                Start.logger.warn("机器人" + qq + "不存在群:" + group);
                return;
            }
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                if (item.startsWith("at:")) {
                    Member member = group1.get(Long.parseLong(item.replace("at:", "")));
                    if (member == null)
                        continue;
                    messageChain = messageChain.plus(new At(member.getId()));
                } else if (item.startsWith("quote:")) {
                    int id = Integer.parseInt(item.replace("quote:", ""));
                    MessageSaveObj call = MessageLsit.get(id);
                    if (call == null)
                        continue;
                    if (call.source == null)
                        continue;
                    QuoteReply quote = new QuoteReply(call.source);
                    messageChain = messageChain.plus(quote);
                } else {
                    messageChain = messageChain.plus(item);
                }
            }
            MessageSource source = group1.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.sourceQQ = qq;
                call.source = source;
                call.time = 120;
                call.id = source.getIds()[0];
                MessageLsit.put(call.id, call);
            }
        } catch (Exception e) {
            Start.logger.error("发送群消息失败", e);
        }
    }

    public static void sendGroupPrivateMessage(long qq, long group, long fid, List<String> message) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Group group1 = bots.get(qq).getGroup(group);
            if (group1 == null) {
                Start.logger.warn("机器人" + qq + "不存在群:" + group);
                return;
            }
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            NormalMember member = group1.get(fid);
            if (member == null) {
                Start.logger.warn("群：" + group + "不存在群员:" + fid);
                return;
            }
            MessageSource source = member.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.source = source;
                call.time = 120;
                call.id = temp[0];
                MessageLsit.put(call.id, call);
            }
        } catch (Exception e) {
            Start.logger.error("发送群私聊消息失败", e);
        }
    }

    public static void sendFriendMessage(long qq, long fid, List<String> message) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            Friend friend = bot.getFriend(fid);
            if(friend == null) {
                Start.logger.warn("机器人" + qq + "不存在朋友:" + fid);
                return;
            }
            MessageSource source = friend.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.source = source;
                call.time = 120;
                call.id = temp[0];
                MessageLsit.put(call.id, call);
            }
        } catch (Exception e) {
            Start.logger.error("发送朋友消息失败", e);
        }
    }

    public static List<GroupsPack> getGroups(long qq) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = bots.get(qq);
            List<GroupsPack> list = new ArrayList<>();
            for (Group item : bot.getGroups()) {
                GroupsPack info = new GroupsPack();
                info.qq = qq;
                info.id = item.getId();
                info.name = item.getName();
                info.img = item.getAvatarUrl();
                info.oid = item.getOwner().getId();
                info.oname = item.getOwner().getNameCard();
                info.per = item.getBotPermission().name();
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            Start.logger.error("获取群数据失败", e);
            return null;
        }
    }

    public static List<FriendsPack> getFriends(long qq) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = bots.get(qq);
            List<FriendsPack> list = new ArrayList<>();
            for (Friend item : bot.getFriends()) {
                FriendsPack info = new FriendsPack();
                info.id = item.getId();
                info.name = item.getNick();
                info.img = item.getAvatarUrl();
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            Start.logger.error("获取朋友数据失败", e);
            return null;
        }
    }

    public static List<MemberInfoPack> getMembers(long qq, long id) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = bots.get(qq);
            if (bot.getGroups().contains(id)) {
                List<MemberInfoPack> list = new ArrayList<>();
                Group group1  = bot.getGroup(id);
                if(group1 == null) {
                    Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                for (Member item : group1.getMembers()) {
                    MemberInfoPack info = new MemberInfoPack();
                    info.id = item.getId();
                    info.name = item.getNameCard();
                    info.img = item.getAvatarUrl();
                    info.nick = item.getNick();
                    info.per = item.getPermission().name();
                    list.add(info);
                }
                return list;
            } else {
                Start.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            Start.logger.error("获取群成员数据失败", e);
            return null;
        }
    }

    public static GroupSettings getGroupInfo(long qq, long id) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = bots.get(qq);
            if (bot.getGroups().contains(id)) {
                Group item = bot.getGroup(id);
                if (item == null) {
                    Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                return item.getSettings();
            } else {
                Start.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            Start.logger.error("获取群成员数据失败", e);
            return null;
        }
    }

    public static void sendGroupImage(long qq, long id, String img) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }

            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.error("没有群：" + id);
                return;
            }
            ExternalResource image = ExternalResource.create(new ByteArrayInputStream(decoder.decode(img)));
            group.sendMessage(group.uploadImage(image));
        } catch (Exception e) {
            Start.logger.error("发送群图片失败", e);
        }
    }

    public static void sendGroupImageFile(long qq, long id, String file) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            FileInputStream stream = new FileInputStream(file);
            ExternalResource image = ExternalResource.create(stream);
            group.sendMessage(group.uploadImage(image));
            stream.close();
        } catch (Exception e) {
            Start.logger.error("发送群图片失败", e);
        }
    }

    public static void sendGroupPrivataImage(long qq, long id, long fid, String img) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            Member member = group.get(fid);
            if (member == null) {
                Start.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            ExternalResource image = ExternalResource.create(new ByteArrayInputStream(decoder.decode(img)));
            member.sendMessage(member.uploadImage(image));
        } catch (Exception e) {
            Start.logger.error("发送私聊图片失败", e);
        }
    }

    public static void sendGroupPrivateImageFile(long qq, long id, long fid, String file) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            Member member = group.get(fid);
            if (member == null) {
                Start.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            FileInputStream stream = new FileInputStream(file);
            ExternalResource image = ExternalResource.create(stream);
            member.sendMessage(member.uploadImage(image));
            stream.close();
        } catch (Exception e) {
            Start.logger.error("发送私聊图片失败", e);
        }
    }

    public static void sendFriendImage(long qq, long id, String img) {
        if (!bots.containsKey(qq)) {
            Start.logger.warn("不存在QQ号:" + qq);
            return;
        }
        Bot bot = bots.get(qq);
        try {
            Friend friend = bot.getFriend(id);
            if(friend == null) {
                Start.logger.warn("机器人:" + qq + "不存在朋友:" + id);
                return;
            }
            ExternalResource image = ExternalResource.create(new ByteArrayInputStream(decoder.decode(img)));
            friend.sendMessage(friend.uploadImage(image));
        } catch (Exception e) {
            Start.logger.error("发送朋友失败", e);
        }
    }

    public static void sendFriendImageFile(long qq, long id, String file) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Friend friend = bot.getFriend(id);
            if(friend == null) {
                Start.logger.warn("机器人:" + qq + "不存在朋友:" + id);
                return;
            }
            FileInputStream stream = new FileInputStream(file);
            ExternalResource image = ExternalResource.create(stream);
            friend.sendMessage(friend.uploadImage(image));
            stream.close();
        } catch (Exception e) {
            Start.logger.error("发送朋友失败", e);
        }
    }

    public static void DeleteGroupMember(long qq, long id, long fid) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                Start.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.kick("");
        } catch (Exception e) {
            Start.logger.error("踢出成员失败", e);
        }
    }

    public static void MuteGroupMember(long qq, long id, long fid, int time) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                Start.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.mute(time);
        } catch (Exception e) {
            Start.logger.error("禁言成员失败", e);
        }
    }

    public static void UnmuteGroupMember(long qq, long id, long fid) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                Start.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.unmute();
        } catch (Exception e) {
            Start.logger.error("解禁成员失败", e);
        }
    }

    public static void GroupMuteAll(long qq, long id) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            group.getSettings().setMuteAll(true);
        } catch (Exception e) {
            Start.logger.error("全群禁言失败", e);
        }
    }

    public static void GroupUnmuteAll(long qq, long id) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            group.getSettings().setMuteAll(false);
        } catch (Exception e) {
            Start.logger.error("全群解禁失败", e);
        }
    }

    public static void SetGroupMemberCard(long qq, long id, long fid, String card) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                Start.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.setNameCard(card);
        } catch (Exception e) {
            Start.logger.error("修改群员名片失败", e);
        }
    }

    public static void SetGroupName(long qq, long id, String name) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            group.setName(name);
        } catch (Exception e) {
            Start.logger.error("设置群名失败", e);
        }
    }

    public static void ReCall(Integer id) {
        try {
            reList.add(id);
        } catch (Exception e) {
            Start.logger.error("消息撤回失败", e);
        }
    }

    public static void SendGroupSound(long qq, long id, String sound) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            ExternalResource voice = ExternalResource.create(new ByteArrayInputStream(decoder.decode(sound)));
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            group.sendMessage(group.uploadVoice(voice));
        } catch (Exception e) {
            Start.logger.error("发送群语音失败", e);
        }
    }

    public static void SendGroupSoundFile(long qq, long id, String file) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            FileInputStream stream = new FileInputStream(file);
            ExternalResource voice = ExternalResource.create(stream);
            group.sendMessage(group.uploadVoice(voice));
            stream.close();
        } catch (Exception e) {
            Start.logger.error("发送群语音失败", e);
        }
    }

    public static List<Long> getBots() {
        return new ArrayList<>(bots.keySet());
    }

    public static void SendNudge(long qq, long id) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Friend friend = bot.getFriend(id);
            if(friend == null) {
                Start.logger.warn("机器人:" + qq + "不存在朋友:" + id);
                return;
            }
            Nudge.Companion.sendNudge(friend, friend.nudge());
        } catch (Exception e) {
            Start.logger.error("发送好友戳一戳失败", e);
        }
    }

    public static void SendNudge(long qq, long id, long fid) {
        try {
            if (!bots.containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = bots.get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            Member member = group.get(fid);
            if (member == null) {
                Start.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            Nudge.Companion.sendNudge(member, member.nudge());
        } catch (Exception e) {
            Start.logger.error("发送群成员戳一戳失败", e);
        }
    }
}
