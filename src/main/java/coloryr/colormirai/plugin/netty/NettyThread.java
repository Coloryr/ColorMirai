package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.IPluginSocket;
import coloryr.colormirai.plugin.PluginUtils;
import coloryr.colormirai.plugin.ThePlugin;
import coloryr.colormirai.plugin.obj.PluginPack;
import coloryr.colormirai.plugin.pack.from.StartPack;
import coloryr.colormirai.robot.BotStart;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class NettyThread implements IPluginSocket {
    private ThePlugin plugin;
    private final ChannelHandlerContext context;
    private final Thread socketThread;

    private final Queue<ByteBuf> list = new ConcurrentLinkedDeque<>();

    public NettyThread(ChannelHandlerContext context) {
        this.context = context;
        socketThread = new Thread(this::start, "NettyThread");
    }

    private RePackObj read() {
        ByteBuf buff = list.poll();
        if (buff == null)
            return null;
        return new RePackObj(buff.readByte(), buff);
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
            StartPack StartPack = PackDecode.startPack(task.data);
            if (StartPack.Name != null && StartPack.Reg != null) {
                socketThread.setName("Plugin[" + StartPack.Name + "]NettyThread");
                plugin.setName(StartPack.Name);
                plugin.setEvents(StartPack.Reg);
                if (StartPack.Groups != null) {
                    plugin.addGroup(StartPack.Groups);
                }
                if (StartPack.QQs != null) {
                    plugin.addQQs(StartPack.QQs);
                }
                if (StartPack.RunQQ != 0 && !BotStart.getBotsKey().contains(StartPack.RunQQ)) {
                    ColorMiraiMain.logger.warn("插件连接失败，没有运行的QQ：" + StartPack.RunQQ);
                    context.close();
                    return;
                }
                plugin.setRunQQ(StartPack.RunQQ);
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
                            plugin.addPack(new PluginPack(PackDecode.groupSetMemberCard(task.data), task.index));
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
                            plugin.addPack(new PluginPack(PackDecode.getMemberInfo(task.data), task.index));
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
                        //60 心跳包
                        case 60: {
                            break;
                        }
                        default: {
                            ColorMiraiMain.logger.error("不知道的包" + task.index);
                            break;
                        }
                    }
                    Thread.sleep(10);
                }
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
        return false;
    }

    @Override
    public void setPlugin(ThePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void close() {
        context.close();
    }
}
