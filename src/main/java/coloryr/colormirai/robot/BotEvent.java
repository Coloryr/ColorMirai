package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.socket.PluginUtils;
import coloryr.colormirai.plugin.socket.obj.SendPackObj;
import coloryr.colormirai.plugin.socket.pack.to.*;
import coloryr.colormirai.robot.event.EventBase;
import coloryr.colormirai.robot.event.EventCall;
import com.alibaba.fastjson.JSON;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageSource;
import org.jetbrains.annotations.NotNull;

public class BotEvent extends SimpleListenerHost {
    //1 [机器人]图片上传前. 可以阻止上传（事件）
    @EventHandler
    public void onBeforeImageUploadEvent(BeforeImageUploadEvent event) {
        if (PluginUtils.havePlugin())
            return;
        String name = event.getSource().toString();
        long id = event.getTarget().getId();
        long qq = event.getBot().getId();
        BeforeImageUploadPack pack = new BeforeImageUploadPack(qq, name, id);
        BotStart.addTask(new SendPackObj(1, JSON.toJSONString(pack), 0, 0, qq));
    }

    //2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）
    @EventHandler
    public void onBotAvatarChangedEvent(BotAvatarChangedEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long qq = event.getBot().getId();
        BotAvatarChangedPack pack = new BotAvatarChangedPack(qq);
        BotStart.addTask(new SendPackObj(2, JSON.toJSONString(pack), 0, 0, qq));
    }

    //3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
    @EventHandler
    public void onBotGroupPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long qq = event.getBot().getId();
        MemberPermission now = event.getNew();
        MemberPermission old = event.getOrigin();
        BotGroupPermissionChangePack pack = new BotGroupPermissionChangePack(qq, now, old, id);
        BotStart.addTask(new SendPackObj(3, JSON.toJSONString(pack), 0, id, qq));
    }

    //4 [机器人]被邀请加入一个群（事件）
    @EventHandler
    public void onBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroupId();
        long qq = event.getBot().getId();
        long fid = event.getInvitorId();
        long eventid = EventCall.addEvent(new EventBase(qq, event.getEventId(), 4, event));
        BotInvitedJoinGroupRequestEventPack pack = new BotInvitedJoinGroupRequestEventPack(qq, id, fid, eventid);
        BotStart.addTask(new SendPackObj(4, JSON.toJSONString(pack), fid, id, qq));
    }

    //5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
    @EventHandler
    public void onBotJoinGroupEventA(BotJoinGroupEvent.Active event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long qq = event.getBot().getId();
        BotJoinGroupEventAPack pack = new BotJoinGroupEventAPack(qq, id);
        BotStart.addTask(new SendPackObj(5, JSON.toJSONString(pack), 0, id, qq));

    }

    //6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
    @EventHandler
    public void onBotJoinGroupEventB(BotJoinGroupEvent.Invite event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getInvitor().getId();
        long qq = event.getBot().getId();
        BotJoinGroupEventBPack pack = new BotJoinGroupEventBPack(qq, id, fid);
        BotStart.addTask(new SendPackObj(6, JSON.toJSONString(pack), fid, id, qq));
    }

    //7 [机器人]主动退出一个群（事件）
    @EventHandler
    public void onBotLeaveEventA(BotLeaveEvent.Active event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long qq = event.getBot().getId();
        BotLeaveEventAPack pack = new BotLeaveEventAPack(qq, id);
        BotStart.addTask(new SendPackObj(7, JSON.toJSONString(pack), 0, id, qq));
    }

    //8 [机器人]被管理员或群主踢出群（事件）
    @EventHandler
    public void onBotLeaveEventB(BotLeaveEvent.Kick event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getOperator().getId();
        long qq = event.getBot().getId();
        BotLeaveEventBPack pack = new BotLeaveEventBPack(qq, id, fid);
        BotStart.addTask(new SendPackObj(7, JSON.toJSONString(pack), fid, id, qq));
    }

    //9 [机器人]被禁言（事件）
    @EventHandler
    public void onBotMuteEvent(BotMuteEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        int time = event.getDurationSeconds();
        long qq = event.getBot().getId();
        long fid = event.getOperator().getId();
        BotMuteEventPack pack = new BotMuteEventPack(qq, id, fid, time);
        BotStart.addTask(new SendPackObj(9, JSON.toJSONString(pack), fid, id, qq));
    }

    //10 [机器人]主动离线（事件）
    @EventHandler
    public void onBotOfflineEventA(BotOfflineEvent.Active event) {
        if (PluginUtils.havePlugin())
            return;
        String message = "";
        if (event.getCause() != null) {
            message = event.getCause().getLocalizedMessage();
        }
        long qq = event.getBot().getId();
        BotOfflineEventAPack pack = new BotOfflineEventAPack(qq, message);
        BotStart.addTask(new SendPackObj(10, JSON.toJSONString(pack), 0, 0, qq));
    }

    //11 [机器人]被挤下线（事件）
    @EventHandler
    public void onBotOfflineEventB(BotOfflineEvent.Force event) {
        if (PluginUtils.havePlugin())
            return;
        String title = event.getTitle();
        String message = event.getMessage();
        long qq = event.getBot().getId();
        BotOfflineEventBPack pack = new BotOfflineEventBPack(qq, message, title);
        BotStart.addTask(new SendPackObj(11, JSON.toJSONString(pack), 0, 0, qq));
    }

    //12 [机器人]被服务器断开（事件）
    @EventHandler
    public void onBotOfflineEventC(BotOfflineEvent.MsfOffline event) {
        if (PluginUtils.havePlugin())
            return;
        String message = "";
        if (event.getCause() != null) {
            message = event.getCause().getLocalizedMessage();
        }
        long qq = event.getBot().getId();
        BotOfflineEventAPack pack = new BotOfflineEventAPack(qq, message);
        BotStart.addTask(new SendPackObj(12, JSON.toJSONString(pack), 0, 0, qq));
    }

    //13 [机器人]因网络问题而掉线（事件）
    @EventHandler
    public void onBotOfflineEventD(BotOfflineEvent.Dropped event) {
        if (PluginUtils.havePlugin())
            return;
        String message = "";
        if (event.getCause() != null) {
            message = event.getCause().getLocalizedMessage();
        }
        long qq = event.getBot().getId();
        BotOfflineEventAPack pack = new BotOfflineEventAPack(qq, message);
        BotStart.addTask(new SendPackObj(13, JSON.toJSONString(pack), 0, 0, qq));
    }

    //14 [机器人]服务器主动要求更换另一个服务器（事件）
    @EventHandler
    public void onBotOfflineEventE(BotOfflineEvent.RequireReconnect event) {
        if (PluginUtils.havePlugin())
            return;
        long qq = event.getBot().getId();
        BotOfflineEventCPack pack = new BotOfflineEventCPack(qq);
        BotStart.addTask(new SendPackObj(14, JSON.toJSONString(pack), 0, 0, qq));
    }

    //15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）
    @EventHandler
    public void onBotOnlineEvent(BotOnlineEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long qq = event.getBot().getId();
        BotOnlineEventPack pack = new BotOnlineEventPack(qq);
        BotStart.addTask(new SendPackObj(15, JSON.toJSONString(pack), 0, 0, qq));
    }

    //16 [机器人]主动或被动重新登录（事件）
    @EventHandler
    public void onBotReloginEvent(BotReloginEvent event) {
        if (PluginUtils.havePlugin())
            return;
        String message = "";
        if (event.getCause() != null) {
            message = event.getCause().getMessage();
        }
        long qq = event.getBot().getId();
        BotReloginEventPack pack = new BotReloginEventPack(qq, message);
        BotStart.addTask(new SendPackObj(16, JSON.toJSONString(pack), 0, 0, qq));
    }

    //17 [机器人]被取消禁言（事件）
    @EventHandler
    public void onBotUnmuteEvent(BotUnmuteEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getOperator().getId();
        long qq = event.getBot().getId();
        BotUnmuteEventPack pack = new BotUnmuteEventPack(qq, id, fid);
        BotStart.addTask(new SendPackObj(17, JSON.toJSONString(pack), fid, id, qq));
    }

    //18 [机器人]成功添加了一个新好友（事件）
    @EventHandler
    public void onFriendAddEvent(FriendAddEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getFriend().getId();
        long qq = event.getBot().getId();
        String nick = event.getFriend().getNick();
        FriendAddEventPack pack = new FriendAddEventPack(qq, id, nick);
        BotStart.addTask(new SendPackObj(18, JSON.toJSONString(pack), id, 0, qq));
    }

    //19 [机器人]好友头像修改（事件）
    @EventHandler
    public void onFriendAvatarChangedEvent(FriendAvatarChangedEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getFriend().getId();
        long qq = event.getBot().getId();
        String url = event.getFriend().getAvatarUrl();
        FriendAvatarChangedEventPack pack = new FriendAvatarChangedEventPack(qq, id, url);
        BotStart.addTask(new SendPackObj(19, JSON.toJSONString(pack), id, 0, qq));
    }

    //20 [机器人]好友已被删除（事件）
    @EventHandler
    public void onFriendDeleteEvent(FriendDeleteEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getFriend().getId();
        long qq = event.getBot().getId();
        FriendDeleteEventPack pack = new FriendDeleteEventPack(qq, id);
        BotStart.addTask(new SendPackObj(20, JSON.toJSONString(pack), id, 0, qq));
    }

    //21 [机器人]在好友消息发送后广播（事件）
    @EventHandler
    public void onFriendMessagePostSendEvent(FriendMessagePostSendEvent event) {
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
        FriendMessagePostSendEventPack pack = new FriendMessagePostSendEventPack(qq, message, id, res, error);
        BotStart.addTask(new SendPackObj(21, JSON.toJSONString(pack), id, 0, qq));
    }

    //22 [机器人]在发送好友消息前广播（事件）
    @EventHandler
    public void onFriendMessagePreSendEvent(FriendMessagePreSendEvent event) {
        if (PluginUtils.havePlugin())
            return;
        Message message = event.getMessage();
        long id = event.getTarget().getId();
        long qq = event.getBot().getId();
        FriendMessagePreSendEventPack pack = new FriendMessagePreSendEventPack(qq, message, id);
        BotStart.addTask(new SendPackObj(22, JSON.toJSONString(pack), id, 0, qq));
    }

    //23 [机器人]好友昵称改变（事件）
    @EventHandler
    public void onFriendRemarkChangeEvent(FriendRemarkChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getFriend().getId();
        String old = event.getOldRemark();
        String now = event.getNewRemark();
        long qq = event.getBot().getId();
        FriendRemarkChangeEventPack pack = new FriendRemarkChangeEventPack(qq, id, old, now);
        BotStart.addTask(new SendPackObj(23, JSON.toJSONString(pack), id, 0, qq));
    }

    //24 [机器人]群 "匿名聊天" 功能状态改变（事件）
    @EventHandler
    public void onGroupAllowAnonymousChatEvent(GroupAllowAnonymousChatEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = 0;
        if (event.getOperator() != null) {
            fid = event.getOperator().getId();
        }
        boolean old = event.getOrigin();
        boolean now = event.getNew();
        long qq = event.getBot().getId();
        GroupAllowAnonymousChatEventPack pack = new GroupAllowAnonymousChatEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(24, JSON.toJSONString(pack), 0, id, qq));
    }

    //25 [机器人]群 "坦白说" 功能状态改变（事件）
    @EventHandler
    public void onGroupAllowConfessTalkEvent(GroupAllowConfessTalkEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        boolean old = event.getOrigin();
        boolean now = event.getNew();
        long qq = event.getBot().getId();
        GroupAllowConfessTalkEventPack pack = new GroupAllowConfessTalkEventPack(qq, id, old, now);
        BotStart.addTask(new SendPackObj(25, JSON.toJSONString(pack), 0, id, qq));
    }

    //26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）
    @EventHandler
    public void onGroupAllowMemberInviteEvent(GroupAllowMemberInviteEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = 0;
        if (event.getOperator() != null) {
            fid = event.getOperator().getId();
        }
        boolean old = event.getOrigin();
        boolean now = event.getNew();
        long qq = event.getBot().getId();
        GroupAllowMemberInviteEventPack pack = new GroupAllowMemberInviteEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(26, JSON.toJSONString(pack), fid, id, qq));
    }

    //27 [机器人]入群公告改变（事件）
    @EventHandler
    public void onGroupEntranceAnnouncementChangeEvent(GroupEntranceAnnouncementChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = 0;
        if (event.getOperator() != null) {
            fid = event.getOperator().getId();
        }
        String old = event.getOrigin();
        String now = event.getNew();
        long qq = event.getBot().getId();
        GroupEntranceAnnouncementChangeEventPack pack = new GroupEntranceAnnouncementChangeEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(27, JSON.toJSONString(pack), fid, id, qq));
    }

    //28 [机器人]在群消息发送后广播（事件）
    @EventHandler
    public void onGroupMessagePostSendEvent(GroupMessagePostSendEvent event) {
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
        BotStart.addTask(new SendPackObj(28, JSON.toJSONString(pack), 0, id, qq));
    }

    //29 [机器人]在发送群消息前广播（事件）
    @EventHandler
    public void onGroupMessagePreSendEvent(GroupMessagePreSendEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getTarget().getId();
        Message message = event.getMessage();
        long qq = event.getBot().getId();
        GroupMessagePreSendEventPack pack = new GroupMessagePreSendEventPack(qq, id, message);
        BotStart.addTask(new SendPackObj(29, JSON.toJSONString(pack), 0, id, qq));
    }

    //30 [机器人]群 "全员禁言" 功能状态改变（事件）
    @EventHandler
    public void onGroupMuteAllEvent(GroupMuteAllEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = 0;
        if (event.getOperator() != null) {
            fid = event.getOperator().getId();
        }
        boolean old = event.getOrigin();
        boolean now = event.getNew();
        long qq = event.getBot().getId();
        GroupMuteAllEventPack pack = new GroupMuteAllEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(30, JSON.toJSONString(pack), fid, id, qq));
    }

    //31 [机器人]群名改变（事件）
    @EventHandler
    public void onGroupNameChangeEvent(GroupNameChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = 0;
        if (event.getOperator() != null) {
            fid = event.getOperator().getId();
        }
        String old = event.getOrigin();
        String now = event.getNew();
        long qq = event.getBot().getId();
        GroupNameChangeEventPack pack = new GroupNameChangeEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(31, JSON.toJSONString(pack), fid, id, qq));
    }

    //32 [机器人]图片上传成功（事件）
    @EventHandler
    public void onImageUploadEventA(ImageUploadEvent.Succeed event) {
        if (PluginUtils.havePlugin())
            return;
        long id = 0, fid = 0;
        Contact contact = event.getTarget();
        if (event.getTarget() instanceof Group) {
            id = event.getTarget().getId();
        } else if (event.getTarget() instanceof Member) {
            Member member = (Member) event.getTarget();
            id = member.getGroup().getId();
            fid = member.getId();
        } else if (event.getTarget() instanceof TempUser) {
            TempUser tempUser = (TempUser) event.getTarget();
            id = tempUser.getId();
        } else if (event.getTarget() instanceof Friend) {
            id = event.getTarget().getId();
        }
        String uuid = event.getImage().getImageId();
        long qq = event.getBot().getId();
        ImageUploadEventAPack pack = new ImageUploadEventAPack(qq, id, fid, uuid);
        BotStart.addTask(new SendPackObj(32, JSON.toJSONString(pack), 0, id, qq));
    }

    //33 [机器人]图片上传失败（事件）
    @EventHandler
    public void onImageUploadEventB(ImageUploadEvent.Failed event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getTarget().getId();
        String name = event.getSource().toString();
        String error = event.getMessage();
        int index = event.getErrno();
        long qq = event.getBot().getId();
        ImageUploadEventBPack pack = new ImageUploadEventBPack(qq, id, name, error, index);
        BotStart.addTask(new SendPackObj(33, JSON.toJSONString(pack), 0, id, qq));
    }

    //34 [机器人]成员群名片改动（事件）
    @EventHandler
    public void onMemberCardChangeEvent(MemberCardChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        String old = event.getOrigin();
        String now = event.getNew();
        long qq = event.getBot().getId();
        MemberCardChangeEventPack pack = new MemberCardChangeEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(34, JSON.toJSONString(pack), fid, id, qq));
    }

    //35 [机器人]成成员被邀请加入群（事件）
    @EventHandler
    public void onMemberJoinEventA(MemberJoinEvent.Invite event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        String name = event.getMember().getNameCard();
        long ifid = event.getInvitor().getId();
        String iname = event.getInvitor().getNameCard();
        long qq = event.getBot().getId();
        InviteMemberJoinEventPack pack = new InviteMemberJoinEventPack(qq, id, fid, name, ifid, iname);
        BotStart.addTask(new SendPackObj(35, JSON.toJSONString(pack), fid, id, qq));
    }

    //36 [机器人]成员主动加入群（事件）
    @EventHandler
    public void onMemberJoinEventB(MemberJoinEvent.Active event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        String name = event.getMember().getNameCard();
        long qq = event.getBot().getId();
        MemberJoinEventPack pack = new MemberJoinEventPack(qq, id, fid, name);
        BotStart.addTask(new SendPackObj(36, JSON.toJSONString(pack), fid, id, qq));
    }

    //37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
    @EventHandler
    public void onMemberJoinRequestEvent(MemberJoinRequestEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = 0;
        if (event.getGroup() != null) {
            id = event.getGroup().getId();
        }
        long fid = event.getFromId();
        String message = event.getMessage();
        long qq = event.getBot().getId();
        NormalMember temp = event.getInvitor();
        long qid;
        if (temp == null) {
            qid = 0;
        } else {
            qid = temp.getId();
        }
        long eventid = EventCall.addEvent(new EventBase(qq, event.getEventId(), 37, event));
        MemberJoinRequestEventPack pack = new MemberJoinRequestEventPack(qq, id, fid, message, eventid, qid);
        BotStart.addTask(new SendPackObj(37, JSON.toJSONString(pack), fid, id, qq));
    }

    //38 [机器人]成员被踢出群（事件）
    @EventHandler
    public void onMemberLeaveEventA(MemberLeaveEvent.Kick event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        long eid = 0;
        if (event.getOperator() != null) {
            eid = event.getOperator().getId();
        }
        long qq = event.getBot().getId();
        MemberLeaveEventAPack pack = new MemberLeaveEventAPack(qq, id, fid, eid);
        BotStart.addTask(new SendPackObj(38, JSON.toJSONString(pack), fid, id, qq));
    }

    //39 [机器人]成员主动离开（事件）
    @EventHandler
    public void onMemberLeaveEventB(MemberLeaveEvent.Quit event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        long qq = event.getBot().getId();
        MemberLeaveEventBPack pack = new MemberLeaveEventBPack(qq, id, fid);
        BotStart.addTask(new SendPackObj(39, JSON.toJSONString(pack), fid, id, qq));
    }

    //40 [机器人]群成员被禁言（事件）
    @EventHandler
    public void onMemberMuteEvent(MemberMuteEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        long eid = 0;
        int time = event.getDurationSeconds();
        if (event.getOperator() != null) {
            eid = event.getOperator().getId();
        }
        long qq = event.getBot().getId();
        MemberMuteEventPack pack = new MemberMuteEventPack(qq, id, fid, eid, time);
        BotStart.addTask(new SendPackObj(40, JSON.toJSONString(pack), fid, id, qq));
    }

    //41 [机器人]成员权限改变（事件）
    @EventHandler
    public void onMemberPermissionChangeEvent(MemberPermissionChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        MemberPermission old = event.getOrigin();
        MemberPermission now = event.getNew();
        long qq = event.getBot().getId();
        MemberPermissionChangeEventPack pack = new MemberPermissionChangeEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(41, JSON.toJSONString(pack), fid, id, qq));
    }

    //42 [机器人]成员群头衔改动（事件）
    @EventHandler
    public void onMemberSpecialTitleChangeEvent(MemberSpecialTitleChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        String old = event.getOrigin();
        String now = event.getNew();
        long qq = event.getBot().getId();
        MemberSpecialTitleChangeEventPack pack = new MemberSpecialTitleChangeEventPack(qq, id, fid, old, now);
        BotStart.addTask(new SendPackObj(42, JSON.toJSONString(pack), fid, id, qq));
    }

    //43 [机器人]群成员被取消禁言（事件）
    @EventHandler
    public void onMemberUnmuteEvent(MemberUnmuteEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        long eid = 0;
        if (event.getOperator() != null) {
            eid = event.getOperator().getId();
        }
        long qq = event.getBot().getId();
        MemberUnmuteEventPack pack = new MemberUnmuteEventPack(qq, id, fid, eid);
        BotStart.addTask(new SendPackObj(43, JSON.toJSONString(pack), fid, id, qq));
    }

    //44 [机器人]好友消息撤回（事件）
    @EventHandler
    public void onMessageRecallEventA(MessageRecallEvent.FriendRecall event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getAuthorId();
        int[] mid = event.getMessageIds();
        long qq = event.getBot().getId();
        int time = event.getMessageTime();
        MessageRecallEventAPack pack = new MessageRecallEventAPack(qq, id, mid, time);
        BotStart.addTask(new SendPackObj(44, JSON.toJSONString(pack), id, 0, qq));
    }

    //45 [机器人]群消息撤回事件（事件）
    @EventHandler
    public void onMessageRecallEventB(MessageRecallEvent.GroupRecall event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getAuthorId();
        int[] mid = event.getMessageIds();
        int time = event.getMessageTime();
        long oid = 0;
        if (event.getOperator() != null) {
            oid = event.getOperator().getId();
        }
        long qq = event.getBot().getId();
        MessageRecallEventBPack pack = new MessageRecallEventBPack(qq, id, fid, mid, time, oid);
        BotStart.addTask(new SendPackObj(45, JSON.toJSONString(pack), fid, id, qq));
    }

    //46 [机器人]一个账号请求添加机器人为好友（事件）
    @EventHandler
    public void onNewFriendRequestEvent(NewFriendRequestEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getFromGroupId();
        long fid = event.getFromId();
        String message = event.getMessage();
        long qq = event.getBot().getId();
        long eventid = EventCall.addEvent(new EventBase(qq, event.getEventId(), 46, event));
        NewFriendRequestEventPack pack = new NewFriendRequestEventPack(qq, id, fid, message, eventid);
        BotStart.addTask(new SendPackObj(46, JSON.toJSONString(pack), fid, id, qq));
    }

    //47 [机器人]在群临时会话消息发送后广播（事件）
    @EventHandler
    public void onTempMessagePostSendEvent(GroupTempMessagePostSendEvent event) {
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
        BotStart.addTask(new SendPackObj(47, JSON.toJSONString(pack), fid, id, qq));
    }

    //48 [机器人]在发送群临时会话消息前广播（事件）
    @EventHandler
    public void onTempMessagePreSendEvent(GroupTempMessagePreSendEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getTarget().getId();
        Message message = event.getMessage();
        long qq = event.getBot().getId();
        TempMessagePreSendEventPack pack = new TempMessagePreSendEventPack(qq, id, fid, message);
        BotStart.addTask(new SendPackObj(48, JSON.toJSONString(pack), fid, id, qq));
    }

    //49 [机器人]收到群消息（事件）
    @EventHandler
    public void onGroupMessageEvent(GroupMessageEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getSubject().getId();
        long fid = event.getSender().getId();
        if (ColorMiraiMain.config.escapeSelf && BotStart.getBots().containsKey(fid))
            return;
        MessageChain message = event.getMessage();
        MessageSaveObj call = new MessageSaveObj();
        call.sourceQQ = event.getBot().getId();
        call.source = event.getSource();
        call.time = -1;
        call.id = call.source.getIds()[0];
        long qq = event.getBot().getId();
        String name = event.getSender().getNameCard();
        BotStart.addMessage(qq, call.id, call);
        GroupMessageEventPack pack = new GroupMessageEventPack(qq, id, fid, message, event.getPermission(), name);
        BotStart.addTask(new SendPackObj(49, JSON.toJSONString(pack), fid, id, qq));
    }

    //50 [机器人]收到群临时会话消息（事件）
    @EventHandler
    public void onTempMessageEvent(GroupTempMessageEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getSender().getId();
        MessageChain message = event.getMessage();
        MessageSaveObj call = new MessageSaveObj();
        call.sourceQQ = event.getBot().getId();
        call.source = event.getSource();
        call.time = -1;
        call.id = call.source.getIds()[0];
        int time = event.getTime();
        long qq = event.getBot().getId();
        String name = event.getSender().getNameCard();
        BotStart.addMessage(qq, call.id, call);
        TempMessageEventPack pack = new TempMessageEventPack(qq, id, fid, message, time, event.getSender().getPermission(), name);
        BotStart.addTask(new SendPackObj(50, JSON.toJSONString(pack), fid, id, qq));
    }

    //51 [机器人]收到朋友消息（事件）
    @EventHandler
    public void onFriendMessageEvent(FriendMessageEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getSender().getId();
        MessageChain message = event.getMessage();
        MessageSaveObj call = new MessageSaveObj();
        call.sourceQQ = event.getBot().getId();
        call.source = event.getSource();
        call.time = -1;
        call.id = call.source.getIds()[0];
        int time = event.getTime();
        long qq = event.getBot().getId();
        String name = event.getSenderName();
        BotStart.addMessage(qq, call.id, call);
        FriendMessageEventPack pack = new FriendMessageEventPack(qq, id, message, time, name);
        BotStart.addTask(new SendPackObj(51, JSON.toJSONString(pack), id, 0, qq));
    }

    //72 [机器人]友输入状态改变（事件）
    @EventHandler
    public void onFriendInputStatusChangedEvent(FriendInputStatusChangedEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getFriend().getId();
        boolean input = event.getInputting();
        long qq = event.getBot().getId();
        FriendInputStatusChangedEventPack pack = new FriendInputStatusChangedEventPack(qq, id, input);
        BotStart.addTask(new SendPackObj(72, JSON.toJSONString(pack), id, 0, qq));
    }

    //73 [机器人]好友昵称改变（事件）
    @EventHandler
    public void onFriendNickChangedEvent(FriendNickChangedEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getFriend().getId();
        String old = event.getFrom();
        String now = event.getTo();
        long qq = event.getBot().getId();
        FriendNickChangedEventPack pack = new FriendNickChangedEventPack(qq, id, old, now);
        BotStart.addTask(new SendPackObj(73, JSON.toJSONString(pack), id, 0, qq));
    }

    //79 [机器人]成员群恢复（事件）
    @EventHandler
    public void onMemberJoinRetrieveEvent(MemberJoinEvent.Retrieve event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long fid = event.getMember().getId();
        long qq = event.getBot().getId();
        MemberJoinRetrieveEventPack pack = new MemberJoinRetrieveEventPack(qq, id, fid);
        BotStart.addTask(new SendPackObj(79, JSON.toJSONString(pack), fid, id, qq));
    }

    //80 [机器人]机器人群恢复（事件）
    @EventHandler
    public void onBotJoinGroupEventRetrieveEvent(BotJoinGroupEvent.Retrieve event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        long qq = event.getBot().getId();
        BotJoinGroupEventRetrieveEventPack pack = new BotJoinGroupEventRetrieveEventPack(qq, id);
        BotStart.addTask(new SendPackObj(80, JSON.toJSONString(pack), 0, id, qq));
    }

    //81 [机器人]群成员戳一戳（事件）
    //82 [机器人]机器人被戳一戳（事件）
    @EventHandler
    public void onMemberNudgedEvent(NudgeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long aid = event.getFrom().getId();
        long fid = event.getTarget().getId();
        long qq = event.getBot().getId();
        String action = event.getAction();
        String suffix = event.getSuffix();
        if (event.getSubject() instanceof Group) {
            Group group = (Group) event.getSubject();
            long id = group.getId();
            NudgedEventPack pack = new NudgedEventPack(qq, id, fid, aid, action, suffix);
            BotStart.addTask(new SendPackObj(81, JSON.toJSONString(pack), fid, id, qq));
        } else if (event.getSubject() instanceof Stranger) {
            Stranger group = (Stranger) event.getSubject();
            long id = group.getId();
            NudgedEventPack pack = new NudgedEventPack(qq, id, fid, aid, action, suffix);
            BotStart.addTask(new SendPackObj(82, JSON.toJSONString(pack), fid, id, qq));
        } else if (event.getSubject() instanceof Friend) {
            Friend group = (Friend) event.getSubject();
            long id = group.getId();
            NudgedEventPack pack = new NudgedEventPack(qq, id, fid, aid, action, suffix);
            BotStart.addTask(new SendPackObj(82, JSON.toJSONString(pack), fid, id, qq));
        } else if (event.getSubject() instanceof Member) {
            Member group = (Member) event.getSubject();
            long id = group.getId();
            NudgedEventPack pack = new NudgedEventPack(qq, id, fid, aid, action, suffix);
            BotStart.addTask(new SendPackObj(82, JSON.toJSONString(pack), fid, id, qq));
        }
    }

    //85 [机器人]龙王改变时（事件）
    @EventHandler
    public void onGroupTalkativeChangeEvent(GroupTalkativeChangeEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long qq = event.getBot().getId();
        long id = event.getGroup().getId();
        long now = event.getNow().getId();
        long old = event.getPrevious().getId();
        GroupTalkativeChangePack pack = new GroupTalkativeChangePack(qq, id, now, old);
        BotStart.addTask(new SendPackObj(98, JSON.toJSONString(pack), 0, id, qq));
    }

    //86 [机器人]其他客户端上线（事件）
    @EventHandler
    public void onOtherClientOnlineEvent(OtherClientOnlineEvent event) {
        if (PluginUtils.havePlugin())
            return;
        int appId = event.getClient().getInfo().getAppId();
        String kind = "";
        if (event.getKind() != null) {
            kind = event.getKind().name();
        }
        String platform;
        Platform temp = event.getClient().getInfo().getPlatform();
        if (temp == null) {
            platform = "";
        } else {
            platform = temp.toString();
        }
        String deviceName = event.getClient().getInfo().getDeviceName();
        String deviceKind = event.getClient().getInfo().getDeviceKind();
        long qq = event.getBot().getId();
        OtherClientOnlineEventPack pack = new OtherClientOnlineEventPack(qq, appId, kind, platform, deviceName, deviceKind);
        BotStart.addTask(new SendPackObj(86, JSON.toJSONString(pack), qq, 0, qq));
    }

    //87 [机器人]其他客户端离线（事件）
    @EventHandler
    public void onOtherClientOfflineEvent(OtherClientOfflineEvent event) {
        if (PluginUtils.havePlugin())
            return;
        int appId = event.getClient().getInfo().getAppId();
        Platform temp = event.getClient().getInfo().getPlatform();
        String platform;
        if (temp == null) {
            platform = "";
        } else {
            platform = temp.toString();
        }
        String deviceName = event.getClient().getInfo().getDeviceName();
        String deviceKind = event.getClient().getInfo().getDeviceKind();
        long qq = event.getBot().getId();
        OtherClientOfflineEventPack pack = new OtherClientOfflineEventPack(qq, appId, platform, deviceName, deviceKind);
        BotStart.addTask(new SendPackObj(87, JSON.toJSONString(pack), qq, 0, qq));
    }

    //88 [机器人]其他客户端发送消息给 Bot（事件）
    @EventHandler
    public void onOtherClientMessageEvent(OtherClientMessageEvent event) {
        if (PluginUtils.havePlugin())
            return;
        int appId = event.getClient().getInfo().getAppId();
        Platform temp = event.getClient().getInfo().getPlatform();
        String platform;
        if (temp == null) {
            platform = "";
        } else {
            platform = temp.toString();
        }
        String deviceName = event.getClient().getInfo().getDeviceName();
        String deviceKind = event.getClient().getInfo().getDeviceKind();
        MessageChain message = event.getMessage();
        long qq = event.getBot().getId();
        OtherClientMessageEventPack pack = new OtherClientMessageEventPack(qq, appId, platform, deviceName, deviceKind, message);
        BotStart.addTask(new SendPackObj(88, JSON.toJSONString(pack), qq, 0, qq));
    }

    //89 [机器人]其他客户端发送群消息（事件）
    @EventHandler
    public void onGroupMessageSyncEvent(GroupMessageSyncEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getGroup().getId();
        int time = event.getTime();
        MessageChain message = event.getMessage();
        long qq = event.getBot().getId();
        GroupMessageSyncEventPack pack = new GroupMessageSyncEventPack(qq, id, time, message);
        BotStart.addTask(new SendPackObj(89, JSON.toJSONString(pack), qq, 0, qq));
    }

    //113 [机器人]群解散消息（事件）
    @EventHandler
    public void onGroupDisbandEvent(BotLeaveEvent.Disband event) {
        if (PluginUtils.havePlugin())
            return;
        long qq = event.getBot().getId();
        long id = event.getGroup().getId();
        GroupDisbandPack pack = new GroupDisbandPack(qq, id);
        BotStart.addTask(new SendPackObj(98, JSON.toJSONString(pack), 0, id, qq));
    }

    //116 [机器人]收到陌生人消息（事件）
    @EventHandler
    public void onStrangerMessageEvent(StrangerMessageEvent event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getSender().getId();
        MessageChain message = event.getMessage();
        MessageSaveObj call = new MessageSaveObj();
        call.sourceQQ = event.getBot().getId();
        call.source = event.getSource();
        call.time = -1;
        call.id = call.source.getIds()[0];
        int time = event.getTime();
        long qq = event.getBot().getId();
        String name = event.getSenderName();
        BotStart.addMessage(qq, call.id, call);
        StrangerMessageEventPack pack = new StrangerMessageEventPack(qq, id, message, time, name);
        BotStart.addTask(new SendPackObj(116, JSON.toJSONString(pack), id, 0, qq));
    }

    //122 [机器人]在发送陌生人消息前广播（事件）
    @EventHandler
    public void onStrangerMessagePreSendEvent(StrangerMessagePreSendEvent event) {
        if (PluginUtils.havePlugin())
            return;
        Message message = event.getMessage();
        long id = event.getTarget().getId();
        long qq = event.getBot().getId();
        StrangerMessagePreSendEventPack pack = new StrangerMessagePreSendEventPack(qq, message, id);
        BotStart.addTask(new SendPackObj(122, JSON.toJSONString(pack), id, 0, qq));
    }

    //123 [机器人]在陌生人消息发送后广播（事件）
    @EventHandler
    public void onStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event) {
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
        StrangerMessagePostSendEventPack pack = new StrangerMessagePostSendEventPack(qq, message, id, res, error);
        BotStart.addTask(new SendPackObj(123, JSON.toJSONString(pack), id, 0, qq));
    }

    //124 [机器人]陌生人关系改变（事件）
    @EventHandler
    public void onStrangerRelationChangeEvent(StrangerRelationChangeEvent.Deleted event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getStranger().getId();
        long qq = event.getBot().getId();
        StrangerRelationChangePack pack = new StrangerRelationChangePack(qq, id, 0);
        BotStart.addTask(new SendPackObj(124, JSON.toJSONString(pack), id, 0, qq));
    }

    @EventHandler
    public void onStrangerRelationChangeEvent(StrangerRelationChangeEvent.Friended event) {
        if (PluginUtils.havePlugin())
            return;
        long id = event.getStranger().getId();
        long qq = event.getBot().getId();
        StrangerRelationChangePack pack = new StrangerRelationChangePack(qq, id, 1);
        BotStart.addTask(new SendPackObj(124, JSON.toJSONString(pack), id, 0, qq));
    }

    //处理在处理事件中发生的未捕获异常
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        ColorMiraiMain.logger.error("在事件处理中发生异常" + "\n" + context, exception);
    }
}
