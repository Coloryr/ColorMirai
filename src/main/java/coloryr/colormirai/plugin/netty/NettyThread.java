package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.IPluginSocket;
import coloryr.colormirai.plugin.PluginUtils;
import coloryr.colormirai.plugin.ThePlugin;
import coloryr.colormirai.plugin.obj.PluginPack;
import coloryr.colormirai.plugin.pack.from.StartPack;
import coloryr.colormirai.plugin.pack.re.*;
import coloryr.colormirai.plugin.pack.to.*;
import coloryr.colormirai.robot.BotStart;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class NettyThread implements IPluginSocket {
    public static final int NettyVersion = 102;
    private ThePlugin plugin;
    private final ChannelHandlerContext context;
    private final Thread socketThread;

    private final Queue<ByteBuf> list = new ConcurrentLinkedDeque<>();

    public NettyThread(ChannelHandlerContext context) {
        this.context = context;
        socketThread = new Thread(this::start, "NettyThread");
    }

    public ThePlugin getPlugin() {
        return plugin;
    }

    private RePackObj read() {
        ByteBuf buff = list.poll();
        if (buff == null)
            return null;
        return new RePackObj(buff.readInt(), buff);
    }

    public void add(ByteBuf byteBuf) {
        list.add(byteBuf);
    }

    private void send(ByteBuf data) {
        try {
            context.writeAndFlush(data).sync();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件数据发送失败", e);
        }
    }

    private void start() {
        RePackObj task;
        try {
            while ((task = read()) == null) {
                Thread.sleep(100);
            }
            if (task.index != 0) {
                ColorMiraiMain.logger.warn("插件连接初始化失败，首个数据包不是初始化包");
                context.close();
                return;
            }
            int[] res = new int[1];
            StartPack StartPack = PackDecode.startPack(task.data, res);
            if (res[0] != NettyVersion) {
                ColorMiraiMain.logger.warn("插件连接器版本不正确，有可能会出现异常");
            }
            if (StartPack.name != null && StartPack.reg != null) {
                socketThread.setName("Plugin[" + StartPack.name + "]NettyThread");
                plugin.setName(StartPack.name);
                plugin.setEvents(StartPack.reg);
                if (StartPack.groups != null) {
                    plugin.addGroup(StartPack.groups);
                }
                if (StartPack.qqList != null) {
                    plugin.addQQs(StartPack.qqList);
                }
                if (StartPack.qq != 0 && !BotStart.getBotsKey().contains(StartPack.qq)) {
                    ColorMiraiMain.logger.warn("插件连接失败，没有运行的QQ：" + StartPack.qq);
                    context.close();
                    return;
                }
                plugin.setRunQQ(StartPack.qq);
                PluginUtils.addPlugin(plugin.getName(), plugin);
                send(PackEncode.startPack(BotStart.getBotsKey()));
            } else {
                ColorMiraiMain.logger.warn("插件连接初始化失败");
                context.close();
                return;
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件连接初始化失败", e);
            return;
        }
        plugin.setRun(true);
        plugin.startRead();
        while (plugin.isRun()) {
            try {
                task = read();
                if (task != null) {
                    if (ColorMiraiMain.config.debug) {
                        ColorMiraiMain.logger.info("收到数据包：[" + task.index + "]" + task.data + "");
                    }
                    switch (task.index) {
                        //27 [插件]下载文件
                        case 27: {
                            plugin.addPack(new PluginPack(PackDecode.downloadFilePack(task.data), task.index));
                            break;
                        }
                        //52 [插件]发送群消息
                        case 52: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupMessagePack(task.data), task.index));
                            break;
                        }
                        //53 [插件]发送私聊消息
                        case 53: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupPrivateMessagePack(task.data), task.index));
                            break;
                        }
                        //54 [插件]发送好友消息
                        case 54: {
                            plugin.addPack(new PluginPack(PackDecode.sendFriendMessagePack(task.data), task.index));
                            break;
                        }
                        //55 [插件]获取群列表
                        //56 [插件]获取好友列表
                        case 55:
                        case 56: {
                            plugin.addPack(new PluginPack(PackDecode.getPack(task.data), task.index));
                            break;
                        }
                        //57 [插件]获取群成员
                        case 57: {
                            plugin.addPack(new PluginPack(PackDecode.groupGetMemberInfoPack(task.data), task.index));
                            break;
                        }
                        //58 [插件]获取群设置
                        case 58: {
                            plugin.addPack(new PluginPack(PackDecode.groupGetSettingPack(task.data), task.index));
                            break;
                        }
                        //59 [插件]回应事件
                        case 59: {
                            plugin.addPack(new PluginPack(PackDecode.eventCallPack(task.data), task.index));
                            break;
                        }
                        //61 [插件]发送图片到群
                        case 61: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupImagePack(task.data), task.index));
                            break;
                        }
                        //62 [插件]发送图片到私聊
                        case 62: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupPrivateImagePack(task.data), task.index));
                            break;
                        }
                        //63 [插件]发送图片到朋友
                        case 63: {
                            plugin.addPack(new PluginPack(PackDecode.sendFriendImagePack(task.data), task.index));
                            break;
                        }
                        //64 [插件]删除群员
                        case 64: {
                            plugin.addPack(new PluginPack(PackDecode.groupKickMemberPack(task.data), task.index));
                            break;
                        }
                        //65 [插件]禁言群员
                        case 65: {
                            plugin.addPack(new PluginPack(PackDecode.groupMuteMemberPack(task.data), task.index));
                            break;
                        }
                        //66 [插件]解除禁言
                        case 66: {
                            plugin.addPack(new PluginPack(PackDecode.groupUnmuteMemberPack(task.data), task.index));
                            break;
                        }
                        //67 [插件]开启全员禁言
                        case 67: {
                            plugin.addPack(new PluginPack(PackDecode.groupMuteAllPack(task.data), task.index));
                            break;
                        }
                        //68 [插件]关闭全员禁言
                        case 68: {
                            plugin.addPack(new PluginPack(PackDecode.groupUnmuteAllPack(task.data), task.index));
                            break;
                        }
                        //69 [插件]设置群名片
                        case 69: {
                            plugin.addPack(new PluginPack(PackDecode.groupSetMemberCardPack(task.data), task.index));
                            break;
                        }
                        //70 [插件]设置群名
                        case 70: {
                            plugin.addPack(new PluginPack(PackDecode.groupSetNamePack(task.data), task.index));
                            break;
                        }
                        //71 [插件]撤回消息
                        case 71: {
                            plugin.addPack(new PluginPack(PackDecode.reCallMessagePack(task.data), task.index));
                            break;
                        }
                        //74 [插件]发送语音到群
                        case 74: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupSoundPack(task.data), task.index));
                            break;
                        }
                        //75 [插件]从本地文件加载图片发送到群
                        case 75: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupImageFilePack(task.data), task.index));
                            break;
                        }
                        //76 [插件]从本地文件加载图片发送到群私聊
                        case 76: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupPrivateImageFilePack(task.data), task.index));
                            break;
                        }
                        //77 [插件]从本地文件加载图片发送到朋友
                        case 77: {
                            plugin.addPack(new PluginPack(PackDecode.sendFriendImageFilePack(task.data), task.index));
                            break;
                        }
                        //78 [插件]从本地文件加载语音发送到群
                        case 78: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupSoundFilePack(task.data), task.index));
                            break;
                        }
                        //83 [插件]发送私聊戳一戳
                        case 83: {
                            plugin.addPack(new PluginPack(PackDecode.sendFriendNudgePack(task.data), task.index));
                            break;
                        }
                        //84 [插件]发送群戳一戳
                        case 84: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupMemberNudgePack(task.data), task.index));
                            break;
                        }
                        //90 [插件]获取图片Url
                        case 90: {
                            plugin.addPack(new PluginPack(PackDecode.getImageUrlPack(task.data), task.index));
                            break;
                        }
                        //91 [插件]获取群成员信息
                        case 91: {
                            plugin.addPack(new PluginPack(PackDecode.getMemberInfoPack(task.data), task.index));
                            break;
                        }
                        //92 [插件]获取朋友信息
                        case 92: {
                            plugin.addPack(new PluginPack(PackDecode.getFriendInfoPack(task.data), task.index));
                            break;
                        }
                        //93 [插件]发送音乐分享
                        case 93: {
                            plugin.addPack(new PluginPack(PackDecode.sendMusicSharePack(task.data), task.index));
                            break;
                        }
                        //94 [插件]设置群精华消息
                        case 94: {
                            plugin.addPack(new PluginPack(PackDecode.groupSetEssenceMessagePack(task.data), task.index));
                            break;
                        }
                        //95 [插件]消息队列
                        case 95: {
                            plugin.addPack(new PluginPack(PackDecode.messageBuffPack(task.data), task.index));
                            break;
                        }
                        //96 [插件]发送朋友骰子
                        case 96: {
                            plugin.addPack(new PluginPack(PackDecode.sendFriendDicePack(task.data), task.index));
                        }
                        //97 [插件]发送群骰子
                        case 97: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupDicePack(task.data), task.index));
                        }
                        //98 [插件]发送群私聊骰子
                        case 98: {
                            plugin.addPack(new PluginPack(PackDecode.sendGroupPrivateDicePack(task.data), task.index));
                        }
                        //99 [插件]上传群文件
                        case 99: {
                            plugin.addPack(new PluginPack(PackDecode.groupAddFilePack(task.data), task.index));
                            break;
                        }
                        //100 [插件]删除群文件
                        case 100: {
                            plugin.addPack(new PluginPack(PackDecode.groupDeleteFilePack(task.data), task.index));
                            break;
                        }
                        //101 [插件]获取群文件
                        case 101: {
                            plugin.addPack(new PluginPack(PackDecode.groupGetFilesPack(task.data), task.index));
                            break;
                        }
                        //102 [插件]移动群文件
                        case 102: {
                            plugin.addPack(new PluginPack(PackDecode.groupMoveFilePack(task.data), task.index));
                            break;
                        }
                        //103 [插件]重命名群文件
                        case 103: {
                            plugin.addPack(new PluginPack(PackDecode.groupRenameFilePack(task.data), task.index));
                            break;
                        }
                        //104 [插件]创新群文件文件夹
                        case 104: {
                            plugin.addPack(new PluginPack(PackDecode.groupAddDirPack(task.data), task.index));
                            break;
                        }
                        //105 [插件]删除群文件文件夹
                        case 105: {
                            plugin.addPack(new PluginPack(PackDecode.groupDeleteDirPack(task.data), task.index));
                            break;
                        }
                        //106 [插件]重命名群文件文件夹
                        case 106: {
                            plugin.addPack(new PluginPack(PackDecode.groupRenameDirPack(task.data), task.index));
                            break;
                        }
                        //107 [插件]下载群文件到指定位置
                        case 107: {
                            plugin.addPack(new PluginPack(PackDecode.groupDownloadFilePack(task.data), task.index));
                            break;
                        }
                        //108 [插件]设置取消管理员
                        case 108: {
                            plugin.addPack(new PluginPack(PackDecode.groupSetAdminPack(task.data), task.index));
                            break;
                        }
                        //109 [插件]获取群公告
                        case 109: {
                            plugin.addPack(new PluginPack(PackDecode.groupGetAnnouncementsPack(task.data), task.index));
                            break;
                        }
                        //110 [插件]设置群公告
                        case 110: {
                            plugin.addPack(new PluginPack(PackDecode.groupAddAnnouncementPack(task.data), task.index));
                            break;
                        }
                        //111 [插件]删除群公告
                        case 111: {
                            plugin.addPack(new PluginPack(PackDecode.groupDeleteAnnouncementPack(task.data), task.index));
                            break;
                        }
                        //112 [插件]发送好友语言文件
                        case 112: {
                            plugin.addPack(new PluginPack(PackDecode.sendFriendSoundFilePack(task.data), task.index));
                            break;
                        }
                        //114 [插件]设置允许群员邀请好友入群的状态
                        case 114: {
                            plugin.addPack(new PluginPack(PackDecode.groupSetAllowMemberInvitePack(task.data), task.index));
                            break;
                        }
                        //115 [插件]设置允许匿名聊天
                        case 115: {
                            plugin.addPack(new PluginPack(PackDecode.groupSetAnonymousChatEnabledPack(task.data), task.index));
                            break;
                        }
                        //117 [插件]发送陌生人消息
                        case 117: {
                            plugin.addPack(new PluginPack(PackDecode.sendStrangerMessagePack(task.data), task.index));
                            break;
                        }
                        //118 [插件]从本地文件加载图片发送到陌生人
                        case 118: {
                            plugin.addPack(new PluginPack(PackDecode.sendStrangerImageFilePack(task.data), task.index));
                            break;
                        }
                        //119 [插件]发送陌生人骰子
                        case 119: {
                            plugin.addPack(new PluginPack(PackDecode.sendStrangerDicePack(task.data), task.index));
                            break;
                        }
                        //120 [插件]发送陌生人戳一戳
                        case 120: {
                            plugin.addPack(new PluginPack(PackDecode.sendStrangerNudgePack(task.data), task.index));
                            break;
                        }
                        //121 [插件]从本地文件加载语音发送到陌生人
                        case 121: {
                            plugin.addPack(new PluginPack(PackDecode.sendStrangerSoundFilePack(task.data), task.index));
                            break;
                        }
                        //126 [插件]发送好友语音
                        case 126: {
                            plugin.addPack(new PluginPack(PackDecode.sendFriendSoundPack(task.data), task.index));
                            break;
                        }
                        //127 [插件]断开连接
                        case 127: {
                            plugin.close();
                            break;
                        }
                        //128 [插件]获取好友分组信息
                        case 128: {
                            plugin.addPack(new PluginPack(PackDecode.getFriendGroupPack(task.data), task.index));
                            break;
                        }
                        //129 [插件]获取所有好友分组信息
                        case 129: {
                            plugin.addPack(new PluginPack(PackDecode.getPack(task.data), task.index));
                            break;
                        }
                        //130 [插件]创建好友分组
                        case 130: {
                            plugin.addPack(new PluginPack(PackDecode.friendGroupCreatePack(task.data), task.index));
                            break;
                        }
                        //131 [插件]修改好友分组名
                        case 131: {
                            plugin.addPack(new PluginPack(PackDecode.friendGroupRenamePack(task.data), task.index));
                            break;
                        }
                        //132 [插件]移动好友到分组
                        case 132: {
                            plugin.addPack(new PluginPack(PackDecode.friendGroupMovePack(task.data), task.index));
                            break;
                        }
                        //133 [插件]删除好友分组
                        case 133: {
                            plugin.addPack(new PluginPack(PackDecode.friendGroupDeletePack(task.data), task.index));
                            break;
                        }
                        //134 [插件]修改群成员头衔
                        case 134: {
                            plugin.addPack(new PluginPack(PackDecode.groupEditMemberSpecialTitlePack(task.data), task.index));
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
                if (!plugin.isRun())
                    break;
                ColorMiraiMain.logger.error("连接发生异常", e);
                close();
            }
        }
    }

    @Override
    public boolean send(Object data, int index) {
        try {
            ByteBuf buff = null;
            switch (index) {
                case 0:
                    buff = PackEncode.reMessagePack((ReMessagePack) data);
                    break;
                case 1:
                    buff = PackEncode.beforeImageUploadPack((BeforeImageUploadPack) data);
                    break;
                case 2:
                    buff = PackEncode.botAvatarChangedPack((BotAvatarChangedPack) data);
                    break;
                case 3:
                    buff = PackEncode.botGroupPermissionChangePack((BotGroupPermissionChangePack) data);
                    break;
                case 4:
                    buff = PackEncode.botInvitedJoinGroupRequestEventPack((BotInvitedJoinGroupRequestEventPack) data);
                    break;
                case 5:
                    buff = PackEncode.botJoinGroupEventAPack((BotJoinGroupEventAPack) data);
                    break;
                case 6:
                    buff = PackEncode.botJoinGroupEventBPack((BotJoinGroupEventBPack) data);
                    break;
                case 7:
                    buff = PackEncode.botLeaveEventAPack((BotLeaveEventAPack) data);
                    break;
                case 8:
                    buff = PackEncode.botLeaveEventBPack((BotLeaveEventBPack) data);
                    break;
                case 9:
                    buff = PackEncode.botMuteEventPack((BotMuteEventPack) data);
                    break;
                case 10:
                    buff = PackEncode.botOfflineEventAPack((BotOfflineEventAPack) data, 10);
                    break;
                case 11:
                    buff = PackEncode.botOfflineEventBPack((BotOfflineEventBPack) data);
                    break;
                case 12:
                    buff = PackEncode.botOfflineEventAPack((BotOfflineEventAPack) data, 12);
                    break;
                case 13:
                    buff = PackEncode.botOfflineEventAPack((BotOfflineEventAPack) data, 13);
                    break;
                case 14:
                    buff = PackEncode.botOfflineEventCPack((BotOfflineEventCPack) data);
                    break;
                case 15:
                    buff = PackEncode.botOnlineEventPack((BotOnlineEventPack) data);
                    break;
                case 16:
                    buff = PackEncode.botReloginEventPack((BotReloginEventPack) data);
                    break;
                case 17:
                    buff = PackEncode.botUnmuteEventPack((BotUnmuteEventPack) data);
                    break;
                case 18:
                    buff = PackEncode.friendAddEventPack((FriendAddEventPack) data);
                    break;
                case 19:
                    buff = PackEncode.friendAvatarChangedEventPack((FriendAvatarChangedEventPack) data);
                    break;
                case 20:
                    buff = PackEncode.friendDeleteEventPack((FriendDeleteEventPack) data);
                    break;
                case 21:
                    buff = PackEncode.friendMessagePostSendEventPack((FriendMessagePostSendEventPack) data);
                    break;
                case 22:
                    buff = PackEncode.friendMessagePreSendEventPack((FriendMessagePreSendEventPack) data);
                    break;
                case 23:
                    buff = PackEncode.friendRemarkChangeEventPack((FriendRemarkChangeEventPack) data);
                    break;
                case 24:
                    buff = PackEncode.groupAllowAnonymousChatEventPack((GroupAllowAnonymousChatEventPack) data);
                    break;
                case 25:
                    buff = PackEncode.groupAllowConfessTalkEventPack((GroupAllowConfessTalkEventPack) data);
                    break;
                case 26:
                    buff = PackEncode.groupAllowMemberInviteEventPack((GroupAllowMemberInviteEventPack) data);
                    break;
                //好友文件还未支持
//                case 27:
//                    buff = PackEncode.downloadFilePack((ReDownloadFilePack) data);
//                    break;
                case 28:
                    buff = PackEncode.groupMessagePostSendEventPack((GroupMessagePostSendEventPack) data);
                    break;
                case 29:
                    buff = PackEncode.groupMessagePreSendEventPack((GroupMessagePreSendEventPack) data);
                    break;
                case 30:
                    buff = PackEncode.groupMuteAllEventPack((GroupMuteAllEventPack) data);
                    break;
                case 31:
                    buff = PackEncode.groupNameChangeEventPack((GroupNameChangeEventPack) data);
                    break;
                case 32:
                    buff = PackEncode.imageUploadEventAPack((ImageUploadEventAPack) data);
                    break;
                case 33:
                    buff = PackEncode.imageUploadEventBPack((ImageUploadEventBPack) data);
                    break;
                case 34:
                    buff = PackEncode.memberCardChangeEventPack((MemberCardChangeEventPack) data);
                    break;
                case 35:
                    buff = PackEncode.inviteMemberJoinEventPack((InviteMemberJoinEventPack) data);
                    break;
                case 36:
                    buff = PackEncode.memberJoinEventPack((MemberJoinEventPack) data);
                    break;
                case 37:
                    buff = PackEncode.memberJoinRequestEventPack((MemberJoinRequestEventPack) data);
                    break;
                case 38:
                    buff = PackEncode.memberLeaveEventAPack((MemberLeaveEventAPack) data);
                    break;
                case 39:
                    buff = PackEncode.memberLeaveEventBPack((MemberLeaveEventBPack) data);
                    break;
                case 40:
                    buff = PackEncode.memberMuteEventPack((MemberMuteEventPack) data);
                    break;
                case 41:
                    buff = PackEncode.memberPermissionChangeEventPack((MemberPermissionChangeEventPack) data);
                    break;
                case 42:
                    buff = PackEncode.memberSpecialTitleChangeEventPack((MemberSpecialTitleChangeEventPack) data);
                    break;
                case 43:
                    buff = PackEncode.memberUnmuteEventPack((MemberUnmuteEventPack) data);
                    break;
                case 44:
                    buff = PackEncode.messageRecallEventAPack((MessageRecallEventAPack) data);
                    break;
                case 45:
                    buff = PackEncode.messageRecallEventBPack((MessageRecallEventBPack) data);
                    break;
                case 46:
                    buff = PackEncode.newFriendRequestEventPack((NewFriendRequestEventPack) data);
                    break;
                case 47:
                    buff = PackEncode.tempMessagePostSendEventPack((TempMessagePostSendEventPack) data);
                    break;
                case 48:
                    buff = PackEncode.tempMessagePreSendEventPack((TempMessagePreSendEventPack) data);
                    break;
                case 49:
                    buff = PackEncode.groupMessageEventPack((GroupMessageEventPack) data);
                    break;
                case 50:
                    buff = PackEncode.tempMessageEventPack((TempMessageEventPack) data);
                    break;
                case 51:
                    buff = PackEncode.friendMessageEventPack((FriendMessageEventPack) data);
                    break;
                case 55:
                    buff = PackEncode.listGroupPack((ReListGroupPack) data);
                    break;
                case 56:
                    buff = PackEncode.listFriendPack((ReListFriendPack) data);
                    break;
                case 57:
                    buff = PackEncode.listMemberPack((ReListMemberPack) data);
                    break;
                case 58:
                    buff = PackEncode.groupSettingPack((ReGroupSettingPack) data);
                    break;
                case 60:
                    buff = PackEncode.testPack();
                    break;
                case 72:
                    buff = PackEncode.friendInputStatusChangedEventPack((FriendInputStatusChangedEventPack) data);
                    break;
                case 73:
                    buff = PackEncode.friendNickChangedEventPack((FriendNickChangedEventPack) data);
                    break;
                case 79:
                    buff = PackEncode.memberJoinRetrieveEventPack((MemberJoinRetrieveEventPack) data);
                    break;
                case 80:
                    buff = PackEncode.botJoinGroupEventRetrieveEventPack((BotJoinGroupEventRetrieveEventPack) data);
                    break;
                case 81:
                    buff = PackEncode.nudgedEventPack((NudgedEventPack) data, 81);
                    break;
                case 82:
                    buff = PackEncode.nudgedEventPack((NudgedEventPack) data, 82);
                    break;
                case 85:
                    buff = PackEncode.groupTalkativeChangePack((GroupTalkativeChangePack) data);
                    break;
                case 86:
                    buff = PackEncode.otherClientOnlineEventPack((OtherClientOnlineEventPack) data);
                    break;
                case 87:
                    buff = PackEncode.otherClientOfflineEventPack((OtherClientOfflineEventPack) data);
                    break;
                case 88:
                    buff = PackEncode.otherClientMessageEventPack((OtherClientMessageEventPack) data);
                    break;
                case 89:
                    buff = PackEncode.groupMessageSyncEventPack((GroupMessageSyncEventPack) data);
                    break;
                case 90:
                    buff = PackEncode.getImageUrlPack((ReGetImageUrlPack) data);
                    break;
                case 91:
                    buff = PackEncode.memberInfoPack((ReMemberInfoPack) data);
                    break;
                case 92:
                    buff = PackEncode.friendInfoPack((ReFriendInfoPack) data);
                    break;
                case 101:
                    buff = PackEncode.groupFilesPack((ReGroupFilesPack) data);
                    break;
                case 109:
                    buff = PackEncode.groupAnnouncementsPack((ReGroupAnnouncementsPack) data);
                    break;
                case 113:
                    buff = PackEncode.groupDisbandPack((GroupDisbandPack) data);
                    break;
                case 116:
                    buff = PackEncode.strangerMessageEventPack((StrangerMessageEventPack) data);
                    break;
                case 122:
                    buff = PackEncode.strangerMessagePreSendEventPack((StrangerMessagePreSendEventPack) data);
                    break;
                case 123:
                    buff = PackEncode.strangerMessagePostSendEventPack((StrangerMessagePostSendEventPack) data);
                    break;
                case 124:
                    buff = PackEncode.strangerRelationChangePack((StrangerRelationChangePack) data, 124);
                    break;
                case 125:
                    buff = PackEncode.strangerRelationChangePack((StrangerRelationChangePack) data, 125);
                    break;
                case 128:
                    buff = PackEncode.reFriendGroupPack((ReFriendGroupPack) data);
                    break;
                case 129:
                    buff = PackEncode.reListFriendGroupPack((ReListFriendGroupPack) data);
                    break;
            }
            if (buff != null) {
                context.writeAndFlush(buff).sync();
            }
            return false;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("数据发送发生错误", e);
        }
        return true;
    }

    @Override
    public void setPlugin(ThePlugin plugin) {
        this.plugin = plugin;
        socketThread.start();
    }

    @Override
    public void close() {
        context.close();
    }
}
