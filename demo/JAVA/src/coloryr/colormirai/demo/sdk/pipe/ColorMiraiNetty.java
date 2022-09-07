package coloryr.colormirai.demo.sdk.pipe;

import coloryr.colormirai.demo.sdk.RobotBase;
import coloryr.colormirai.demo.sdk.api.IColorMiraiPipe;
import coloryr.colormirai.demo.sdk.enums.LogType;
import coloryr.colormirai.demo.sdk.enums.MemberPermission;
import coloryr.colormirai.demo.sdk.enums.Sex;
import coloryr.colormirai.demo.sdk.enums.StateType;
import coloryr.colormirai.demo.sdk.objs.GroupSettings;
import coloryr.colormirai.demo.sdk.objs.UserProfile;
import coloryr.colormirai.demo.sdk.pack.PackBase;
import coloryr.colormirai.demo.sdk.pack.from.*;
import coloryr.colormirai.demo.sdk.pack.re.*;
import coloryr.colormirai.demo.sdk.pack.to.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ColorMiraiNetty implements IColorMiraiPipe {
    static class PackTask {
        public PackBase pack;
        public byte index;
    }

    public RobotBase robot;
    private final Queue<PackTask> queue1;
    private final Queue<ByteBuf> queue2;
    private final Thread thread;
    private final NioEventLoopGroup group;
    private Channel client;
    private Bootstrap bootstrap;
    private final ColorMiraiNetty netty;

    public ColorMiraiNetty(RobotBase robot) {
        this.robot = robot;
        queue1 = new ConcurrentLinkedDeque<>();
        queue2 = new ConcurrentLinkedDeque<>();
        thread = new Thread(this::read);

        group = new NioEventLoopGroup();

        netty = this;
    }

    private void read() {
        ByteBuf item;
        PackTask item1;
        int time = 0;
        while (robot.isRun) {
            try {
                Thread.sleep(50);
                if (!robot.isConnect) {
                    reConnect();
                    robot.isFirst = false;
                    robot.times = 0;
                    robot.robotStateEvent.StateAction(StateType.CONNECT);
                } else if ((item = queue2.poll()) != null) {
                    byte index = item.readByte();
                    if (index == 60)
                        continue;
                    PackBase pack = null;
                    switch (index) {
                        case 1:
                            pack = PackDecode.beforeImageUploadPack(item);
                            break;
                        case 2:
                            pack = PackDecode.botAvatarChangedPack(item);
                            break;
                        case 3:
                            pack = PackDecode.botGroupPermissionChangePack(item);
                            break;
                        case 4:
                            pack = PackDecode.botInvitedJoinGroupRequestEventPack(item);
                            break;
                        case 5:
                            pack = PackDecode.botJoinGroupEventAPack(item);
                            break;
                        case 6:
                            pack = PackDecode.botJoinGroupEventBPack(item);
                            break;
                        case 7:
                            pack = PackDecode.botLeaveEventAPack(item);
                            break;
                        case 8:
                            pack = PackDecode.botLeaveEventBPack(item);
                            break;
                        case 9:
                            pack = PackDecode.botMuteEventPack(item);
                            break;
                        case 10:
                            pack = PackDecode.botOfflineEventAPack(item);
                            break;
                        case 11:
                            pack = PackDecode.botOfflineEventBPack(item);
                            break;
                        case 12:
                            pack = PackDecode.botOfflineEventAPack(item);
                            break;
                        case 13:
                            pack = PackDecode.botOfflineEventAPack(item);
                            break;
                        case 14:
                            pack = PackDecode.botOfflineEventCPack(item);
                            break;
                        case 15:
                            pack = PackDecode.botOnlineEventPack(item);
                            break;
                        case 16:
                            pack = PackDecode.botReloginEventPack(item);
                            break;
                        case 17:
                            pack = PackDecode.botUnmuteEventPack(item);
                            break;
                        case 18:
                            pack = PackDecode.friendAddEventPack(item);
                            break;
                        case 19:
                            pack = PackDecode.friendAvatarChangedEventPack(item);
                            break;
                        case 20:
                            pack = PackDecode.friendDeleteEventPack(item);
                            break;
                        case 21:
                            pack = PackDecode.friendMessagePostSendEventPack(item);
                            break;
                        case 22:
                            pack = PackDecode.friendMessagePreSendEventPack(item);
                            break;
                        case 23:
                            pack = PackDecode.friendRemarkChangeEventPack(item);
                            break;
                        case 24:
                            pack = PackDecode.groupAllowAnonymousChatEventPack(item);
                            break;
                        case 25:
                            pack = PackDecode.groupAllowConfessTalkEventPack(item);
                            break;
                        case 26:
                            pack = PackDecode.groupAllowMemberInviteEventPack(item);
                            break;
                        //case 27:
                        //    pack = PackDecode.groupEntranceAnnouncementChangeEventPack();
                        //    break;
                        case 28:
                            pack = PackDecode.groupMessagePostSendEventPack(item);
                            break;
                        case 29:
                            pack = PackDecode.groupMessagePreSendEventPack(item);
                            break;
                        case 30:
                            pack = PackDecode.groupMuteAllEventPack(item);
                            break;
                        case 31:
                            pack = PackDecode.groupNameChangeEventPack(item);
                            break;
                        case 32:
                            pack = PackDecode.imageUploadEventAPack(item);
                            break;
                        case 33:
                            pack = PackDecode.imageUploadEventBPack(item);
                            break;
                        case 34:
                            pack = PackDecode.memberCardChangeEventPack(item);
                            break;
                        case 35:
                            pack = PackDecode.inviteMemberJoinEventPack(item);
                            break;
                        case 36:
                            pack = PackDecode.memberJoinEventPack(item);
                            break;
                        case 37:
                            pack = PackDecode.memberJoinRequestEventPack(item);
                            break;
                        case 38:
                            pack = PackDecode.memberLeaveEventAPack(item);
                            break;
                        case 39:
                            pack = PackDecode.memberLeaveEventBPack(item);
                            break;
                        case 40:
                            pack = PackDecode.memberMuteEventPack(item);
                            break;
                        case 41:
                            pack = PackDecode.memberPermissionChangeEventPack(item);
                            break;
                        case 42:
                            pack = PackDecode.memberSpecialTitleChangeEventPack(item);
                            break;
                        case 43:
                            pack = PackDecode.memberUnmuteEventPack(item);
                            break;
                        case 44:
                            pack = PackDecode.messageRecallEventAPack(item);
                            break;
                        case 45:
                            pack = PackDecode.messageRecallEventBPack(item);
                            break;
                        case 46:
                            pack = PackDecode.newFriendRequestEventPack(item);
                            break;
                        case 47:
                            pack = PackDecode.tempMessagePostSendEventPack(item);
                            break;
                        case 48:
                            pack = PackDecode.tempMessagePreSendEventPack(item);
                            break;
                        case 49:
                            pack = PackDecode.groupMessageEventPack(item);
                            break;
                        case 50:
                            pack = PackDecode.tempMessageEventPack(item);
                            break;
                        case 51:
                            pack = PackDecode.friendMessageEventPack(item);
                            break;
                        case 55:
                            pack = PackDecode.listGroupPack(item);
                            break;
                        case 56:
                            pack = PackDecode.listFriendPack(item);
                            break;
                        case 57:
                            pack = PackDecode.listMemberPack(item);
                            break;
                        case 58:
                            pack = PackDecode.groupSettingPack(item);
                            break;
                        case 72:
                            pack = PackDecode.friendInputStatusChangedEventPack(item);
                            break;
                        case 73:
                            pack = PackDecode.friendNickChangedEventPack(item);
                            break;
                        case 79:
                            pack = PackDecode.memberJoinRetrieveEventPack(item);
                            break;
                        case 80:
                            pack = PackDecode.botJoinGroupEventRetrieveEventPack(item);
                            break;
                        case 81:
                            pack = PackDecode.nudgedEventPack(item);
                            break;
                        case 82:
                            pack = PackDecode.nudgedEventPack(item);
                            break;
                        case 85:
                            pack = PackDecode.groupTalkativeChangePack(item);
                            break;
                        case 86:
                            pack = PackDecode.otherClientOnlineEventPack(item);
                            break;
                        case 87:
                            pack = PackDecode.otherClientOfflineEventPack(item);
                            break;
                        case 88:
                            pack = PackDecode.otherClientMessageEventPack(item);
                            break;
                        case 89:
                            pack = PackDecode.groupMessageSyncEventPack(item);
                            break;
                        case 90:
                            pack = PackDecode.getImageUrlPack(item);
                            break;
                        case 91:
                            pack = PackDecode.memberInfoPack(item);
                            break;
                        case 92:
                            pack = PackDecode.friendInfoPack(item);
                            break;
                        case 101:
                            pack = PackDecode.groupFilesPack(item);
                            break;
                        case 109:
                            pack = PackDecode.groupAnnouncementsPack(item);
                            break;
                        case 113:
                            pack = PackDecode.groupDisbandPack(item);
                            break;
                        case 116:
                            pack = PackDecode.strangerMessageEventPack(item);
                            break;
                        case 122:
                            pack = PackDecode.strangerMessagePreSendEventPack(item);
                            break;
                        case 123:
                            pack = PackDecode.strangerMessagePostSendEventPack(item);
                            break;
                        case 124:
                            pack = PackDecode.strangerRelationChangePack(item);
                            break;
                        case 125:
                            pack = PackDecode.strangerRelationChangePack(item);
                            break;
                    }

                    robot.addRead(pack, index);
                } else if (robot.config.check && time >= 20) {
                    time = 0;
                    addSend(null, (byte) 60);
                } else if ((item1 = queue1.poll()) != null) {
                    ByteBuf pack = null;
                    switch (item1.index) {
                        case 52:
                            pack = PackEncode.ToPack((SendGroupMessagePack) item1.pack);
                            break;
                        case 53:
                            pack = PackEncode.ToPack((SendGroupPrivateMessagePack) item1.pack);
                            break;
                        case 54:
                            pack = PackEncode.ToPack((SendFriendMessagePack) item1.pack);
                            break;
                        case 55:
                            pack = PackEncode.ToPack((GetPack) item1.pack, (byte) 55);
                            break;
                        case 56:
                            pack = PackEncode.ToPack((GetPack) item1.pack, (byte) 56);
                            break;
                        case 57:
                            pack = PackEncode.ToPack((GroupGetMemberInfoPack) item1.pack);
                            break;
                        case 58:
                            pack = PackEncode.ToPack((GroupGetSettingPack) item1.pack);
                            break;
                        case 59:
                            pack = PackEncode.ToPack((EventCallPack) item1.pack);
                            break;
                        case 60:
                            pack = PackEncode.TestPack();
                            break;
                        case 61:
                            pack = PackEncode.ToPack((SendGroupImagePack) item1.pack);
                            break;
                        case 62:
                            pack = PackEncode.ToPack((SendGroupPrivateImagePack) item1.pack);
                            break;
                        case 63:
                            pack = PackEncode.ToPack((SendFriendImagePack) item1.pack);
                            break;
                        case 64:
                            pack = PackEncode.ToPack((GroupKickMemberPack) item1.pack);
                            break;
                        case 65:
                            pack = PackEncode.ToPack((GroupMuteMemberPack) item1.pack);
                            break;
                        case 66:
                            pack = PackEncode.ToPack((GroupUnmuteMemberPack) item1.pack);
                            break;
                        case 67:
                            pack = PackEncode.ToPack((GroupMuteAllPack) item1.pack);
                            break;
                        case 68:
                            pack = PackEncode.ToPack((GroupUnmuteAllPack) item1.pack);
                            break;
                        case 69:
                            pack = PackEncode.ToPack((GroupSetMemberCardPack) item1.pack);
                            break;
                        case 70:
                            pack = PackEncode.ToPack((GroupSetNamePack) item1.pack);
                            break;
                        case 71:
                            pack = PackEncode.ToPack((ReCallMessagePack) item1.pack);
                            break;
                        case 74:
                            pack = PackEncode.ToPack((SendGroupSoundPack) item1.pack);
                            break;
                        case 75:
                            pack = PackEncode.ToPack((SendGroupImageFilePack) item1.pack);
                            break;
                        case 76:
                            pack = PackEncode.ToPack((SendGroupPrivateImageFilePack) item1.pack);
                            break;
                        case 77:
                            pack = PackEncode.ToPack((SendFriendImageFilePack) item1.pack);
                            break;
                        case 78:
                            pack = PackEncode.ToPack((SendGroupSoundFilePack) item1.pack);
                            break;
                        case 83:
                            pack = PackEncode.ToPack((SendFriendNudgePack) item1.pack);
                            break;
                        case 84:
                            pack = PackEncode.ToPack((SendGroupMemberNudgePack) item1.pack);
                            break;
                        case 90:
                            pack = PackEncode.ToPack((GetImageUrlPack) item1.pack);
                            break;
                        case 91:
                            pack = PackEncode.ToPack((GetMemberInfoPack) item1.pack);
                            break;
                        case 92:
                            pack = PackEncode.ToPack((GetFriendInfoPack) item1.pack);
                            break;
                        case 93:
                            pack = PackEncode.ToPack((SendMusicSharePack) item1.pack);
                            break;
                        case 94:
                            pack = PackEncode.ToPack((GroupSetEssenceMessagePack) item1.pack);
                            break;
                        case 95:
                            pack = PackEncode.ToPack((MessageBuffPack) item1.pack);
                            break;
                        case 96:
                            pack = PackEncode.ToPack((SendFriendDicePack) item1.pack);
                            break;
                        case 97:
                            pack = PackEncode.ToPack((SendGroupDicePack) item1.pack);
                            break;
                        case 98:
                            pack = PackEncode.ToPack((SendGroupPrivateDicePack) item1.pack);
                            break;
                        case 99:
                            pack = PackEncode.ToPack((GroupAddFilePack) item1.pack);
                            break;
                        case 100:
                            pack = PackEncode.ToPack((GroupDeleteFilePack) item1.pack);
                            break;
                        case 101:
                            pack = PackEncode.ToPack((GroupGetFilesPack) item1.pack);
                            break;
                        case 102:
                            pack = PackEncode.ToPack((GroupMoveFilePack) item1.pack);
                            break;
                        case 103:
                            pack = PackEncode.ToPack((GroupRenameFilePack) item1.pack);
                            break;
                        case 104:
                            pack = PackEncode.ToPack((GroupAddDirPack) item1.pack);
                            break;
                        case 105:
                            pack = PackEncode.ToPack((GroupDeleteDirPack) item1.pack);
                            break;
                        case 106:
                            pack = PackEncode.ToPack((GroupRenameDirPack) item1.pack);
                            break;
                        case 107:
                            pack = PackEncode.ToPack((GroupDownloadFilePack) item1.pack);
                            break;
                        case 108:
                            pack = PackEncode.ToPack((GroupSetAdminPack) item1.pack);
                            break;
                        case 109:
                            pack = PackEncode.ToPack((GroupGetAnnouncementsPack) item1.pack);
                            break;
                        case 110:
                            pack = PackEncode.ToPack((GroupAddAnnouncementPack) item1.pack);
                            break;
                        case 111:
                            pack = PackEncode.ToPack((GroupDeleteAnnouncementPack) item1.pack);
                            break;
                        case 112:
                            pack = PackEncode.ToPack((SendFriendSoundFilePack) item1.pack);
                            break;
                        case 114:
                            pack = PackEncode.ToPack((GroupSetAllowMemberInvitePack) item1.pack);
                            break;
                        case 115:
                            pack = PackEncode.ToPack((GroupSetAnonymousChatEnabledPack) item1.pack);
                            break;
                        case 117:
                            pack = PackEncode.ToPack((SendStrangerMessagePack) item1.pack);
                            break;
                        case 118:
                            pack = PackEncode.ToPack((SendStrangerImageFilePack) item1.pack);
                            break;
                        case 119:
                            pack = PackEncode.ToPack((SendStrangerDicePack) item1.pack);
                            break;
                        case 120:
                            pack = PackEncode.ToPack((SendStrangerNudgePack) item1.pack);
                            break;
                        case 121:
                            pack = PackEncode.ToPack((SendStrangerSoundFilePack) item1.pack);
                            break;
                        case 126:
                            pack = PackEncode.ToPack((SendFriendSoundPack) item1.pack);
                            break;
                    }
                    if (pack != null) {
                        client.writeAndFlush(pack).sync();
                    }
                }
                time++;
            } catch (Exception e) {
                robot.isConnect = false;
                robot.robotStateEvent.StateAction(StateType.DISCONNECT);
                e.printStackTrace();
                if (robot.isFirst) {
                    robot.isRun = false;
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人连接失败");
                } else {
                    robot.times++;
                    if (robot.times == 10) {
                        robot.isRun = false;
                        robot.robotLogEvent.LogAction(LogType.ERROR, "重连失败次数过多");
                    }
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人连接失败");
                    e.printStackTrace();
                    robot.isConnect = false;
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人" + robot.config.time + "毫秒后重连");
                    try {
                        Thread.sleep(robot.config.time);
                    } catch (Exception exception) {
                    }
                    robot.robotLogEvent.LogAction(LogType.ERROR, "机器人重连中");
                }
            }
        }
    }

    @Override
    public void addSend(PackBase pack_, byte index_) {
        queue1.add(new PackTask() {{
            pack = pack_;
            index = index_;
        }});
    }

    public void addreadPack(ByteBuf pack) {
        queue2.add(pack);
    }

    @Override
    public void reConnect() throws Exception {
        if (client != null) {
            client.close().await();
            client.deregister().sync();
        }

        queue2.clear();

        robot.robotStateEvent.StateAction(StateType.CONNECTING);

        bootstrap = new Bootstrap();
        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LengthFieldPrepender(4))
                                .addLast(new LengthFieldBasedFrameDecoder(1024 * 500, 0, 4, 0, 4))
                                .addLast(new ClientHandler(netty));
                    }
                });
        client = bootstrap.connect(robot.config.ip, robot.config.port).await().channel();

        ByteBuf pack = PackEncode.ToPack(robot.packStart);

        client.writeAndFlush(pack).sync();

        while ((pack = queue2.poll()) == null) {
            Thread.sleep(10);
        }

        robot.qqs = PackDecode.startPack(pack);

        queue1.clear();
        robot.robotLogEvent.LogAction(LogType.LOG, "机器人已连接");
        robot.isConnect = true;
    }

    @Override
    public void sendStop() {
        ByteBuf buff = Unpooled.buffer();
        buff.writeByte(127);
        try {
            client.writeAndFlush(buff).sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        robot.isConnect = false;
        robot.robotStateEvent.StateAction(StateType.DISCONNECT);
        try {
            client.close().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startRead() {
        thread.start();
    }

    static class ClientHandler extends ChannelInboundHandlerAdapter {
        private final ColorMiraiNetty netty;

        public ClientHandler(ColorMiraiNetty netty) {
            this.netty = netty;
        }

        @Override
        public void channelRead(ChannelHandlerContext context, Object message) {
            ByteBuf byteBuf = (ByteBuf) message;
            netty.addreadPack(byteBuf);
        }

        @Override
        public void channelInactive(ChannelHandlerContext context) {
            netty.robot.isConnect = false;
            netty.robot.robotStateEvent.StateAction(StateType.DISCONNECT);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext context) {
            context.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext context, Throwable cause) {
            netty.robot.isConnect = false;
            netty.robot.robotStateEvent.StateAction(StateType.DISCONNECT);
            context.close();
        }
    }

    static class PackDecode {
        public static String readString(ByteBuf buff) {
            int length = buff.readInt();
            byte[] temp = new byte[length];
            buff.readBytes(temp);
            return new String(temp, StandardCharsets.UTF_8);
        }

        public static List<Long> readLongList(ByteBuf buff) {
            int length = buff.readInt();
            List<Long> list = new ArrayList<>();
            for (int a = 0; a < length; a++) {
                list.add(buff.readLong());
            }
            return list;
        }

        public static int[] readIntList(ByteBuf buff) {
            int[] temp = new int[buff.readInt()];
            for (int a = 0; a < temp.length; a++) {
                temp[a] = buff.readInt();
            }
            return temp;
        }

        public static byte[] readBytes(ByteBuf buff) {
            int length = buff.readInt();
            byte[] temp = new byte[length];
            buff.readBytes(temp);
            return temp;
        }

        public static List<String> readStringList(ByteBuf buff) {
            int length = buff.readInt();
            List<String> list = new ArrayList<>();
            for (int a = 0; a < length; a++) {
                list.add(readString(buff));
            }
            return list;
        }

        public static ReMemberInfoPack readMemberInfoPack(ByteBuf buff) {
            ReMemberInfoPack pack = new ReMemberInfoPack();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.nick = readString(buff);
            pack.img = readString(buff);
            pack.per = MemberPermission.values()[buff.readInt()];
            pack.nameCard = readString(buff);
            pack.specialTitle = readString(buff);
            pack.avatarUrl = readString(buff);
            pack.muteTimeRemaining = buff.readInt();
            pack.joinTimestamp = buff.readInt();
            pack.lastSpeakTimestamp = buff.readInt();
            pack.uuid = readString(buff);
            return pack;
        }

        public static ReFriendInfoPack readFriendInfoPack(ByteBuf buff) {
            ReFriendInfoPack pack = new ReFriendInfoPack();
            pack.id = buff.readLong();
            pack.img = readString(buff);
            pack.remark = readString(buff);
            pack.userProfile = new UserProfile();
            pack.userProfile.nickname = readString(buff);
            pack.userProfile.email = readString(buff);
            pack.userProfile.age = buff.readInt();
            pack.userProfile.qLevel = buff.readInt();
            pack.userProfile.sex = Sex.values()[buff.readInt()];
            pack.userProfile.sign = readString(buff);
            pack.uuid = readString(buff);
            return pack;
        }

        public static GroupFileInfo readGroupFileInfo(ByteBuf buff) {
            GroupFileInfo pack = new GroupFileInfo();
            pack.name = readString(buff);
            pack.id = readString(buff);
            pack.absolutePath = readString(buff);
            pack.isFile = buff.readBoolean();
            pack.isFolder = buff.readBoolean();
            pack.size = buff.readLong();
            pack.uploaderId = buff.readLong();
            pack.uploadTime = buff.readLong();
            pack.lastModifyTime = buff.readLong();
            pack.expiryTime = buff.readLong();
            pack.sha1 = readString(buff);
            pack.md5 = readString(buff);
            return pack;
        }

        public static GroupAnnouncement readGroupAnnouncement(ByteBuf buff) {
            GroupAnnouncement pack = new GroupAnnouncement();
            pack.senderId = buff.readLong();
            pack.fid = readString(buff);
            pack.allConfirmed = buff.readBoolean();
            pack.confirmedMembersCount = buff.readInt();
            pack.publicationTime = buff.readLong();
            pack.content = readString(buff);
            pack.image = readString(buff);
            pack.sendToNewMember = buff.readBoolean();
            pack.isPinned = buff.readBoolean();
            pack.showEditCard = buff.readBoolean();
            pack.showPopup = buff.readBoolean();
            pack.requireConfirmation = buff.readBoolean();
            return pack;
        }

        public static GroupInfo readGroupInfo(ByteBuf buff) {
            GroupInfo pack = new GroupInfo();
            pack.id = buff.readLong();
            pack.name = readString(buff);
            pack.img = readString(buff);
            pack.oid = buff.readLong();
            pack.per = MemberPermission.values()[buff.readInt()];
            return pack;
        }

        public static List<Long> startPack(ByteBuf buff) {
            int a = buff.readInt();
            List<Long> list = new ArrayList<>();
            for (int i = 0; i < a; i++) {
                list.add(buff.readLong());
            }
            return list;
        }

        public static ReListGroupPack listGroupPack(ByteBuf buff) {
            ReListGroupPack pack = new ReListGroupPack();
            pack.qq = buff.readLong();
            pack.groups = new ArrayList<>();
            int a = buff.readInt();
            for (int i = 0; i < a; i++) {
                pack.groups.add(readGroupInfo(buff));
            }
            pack.uuid = readString(buff);
            return pack;
        }

        public static ReListFriendPack listFriendPack(ByteBuf buff) {
            ReListFriendPack pack = new ReListFriendPack();
            pack.qq = buff.readLong();
            pack.friends = new ArrayList<>();
            int a = buff.readInt();
            for (int i = 0; i < a; i++) {
                pack.friends.add(readFriendInfoPack(buff));
            }
            pack.uuid = readString(buff);
            return pack;
        }

        public static ReListMemberPack listMemberPack(ByteBuf buff) {
            ReListMemberPack pack = new ReListMemberPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.members = new ArrayList<>();
            int a = buff.readInt();
            for (int i = 0; i < a; i++) {
                pack.members.add(readMemberInfoPack(buff));
            }
            pack.uuid = readString(buff);
            return pack;
        }

        public static ReGroupSettingPack groupSettingPack(ByteBuf buff) {
            ReGroupSettingPack pack = new ReGroupSettingPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.setting = new GroupSettings();
            pack.setting.isMuteAll = buff.readBoolean();
            pack.setting.isAllowMemberInvite = buff.readBoolean();
            pack.setting.isAutoApproveEnabled = buff.readBoolean();
            pack.setting.isAnonymousChatEnabled = buff.readBoolean();
            pack.uuid = readString(buff);
            return pack;
        }

        public static GetImageUrlPack getImageUrlPack(ByteBuf buff) {
            GetImageUrlPack pack = new GetImageUrlPack();
            pack.qq = buff.readLong();
            pack.uuid = readString(buff);
            return pack;
        }

        public static ReMemberInfoPack memberInfoPack(ByteBuf buff) {
            long qq = buff.readLong();
            ReMemberInfoPack pack = readMemberInfoPack(buff);
            pack.qq = qq;
            return pack;
        }

        public static ReFriendInfoPack friendInfoPack(ByteBuf buff) {
            long qq = buff.readLong();
            ReFriendInfoPack pack = readFriendInfoPack(buff);
            pack.qq = qq;
            pack.uuid = readString(buff);
            return pack;
        }

        public static ReGroupFilesPack groupFilesPack(ByteBuf buff) {
            ReGroupFilesPack pack = new ReGroupFilesPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.files = new ArrayList<>();
            int a = buff.readInt();
            for (int i = 0; i < a; i++) {
                pack.files.add(readGroupFileInfo(buff));
            }
            pack.uuid = readString(buff);
            return pack;
        }

        public static ReGroupAnnouncementsPack groupAnnouncementsPack(ByteBuf buff) {
            ReGroupAnnouncementsPack pack = new ReGroupAnnouncementsPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.list = new ArrayList<>();
            int a = buff.readInt();
            for (int i = 0; i < a; i++) {
                pack.list.add(readGroupAnnouncement(buff));
            }
            pack.uuid = readString(buff);
            return pack;
        }

        public static BeforeImageUploadPack beforeImageUploadPack(ByteBuf buff) {
            BeforeImageUploadPack pack = new BeforeImageUploadPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.name = readString(buff);
            return pack;
        }

        public static BotAvatarChangedPack botAvatarChangedPack(ByteBuf buff) {
            BotAvatarChangedPack pack = new BotAvatarChangedPack();
            pack.qq = buff.readLong();
            return pack;
        }

        public static BotGroupPermissionChangePack botGroupPermissionChangePack(ByteBuf buff) {
            BotGroupPermissionChangePack pack = new BotGroupPermissionChangePack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = MemberPermission.values()[buff.readInt()];
            pack.now = MemberPermission.values()[buff.readInt()];
            return pack;
        }

        public static BotInvitedJoinGroupRequestEventPack botInvitedJoinGroupRequestEventPack(ByteBuf buff) {
            BotInvitedJoinGroupRequestEventPack pack = new BotInvitedJoinGroupRequestEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.eventid = buff.readLong();
            return pack;
        }

        public static BotJoinGroupEventAPack botJoinGroupEventAPack(ByteBuf buff) {
            BotJoinGroupEventAPack pack = new BotJoinGroupEventAPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            return pack;
        }

        public static BotJoinGroupEventBPack botJoinGroupEventBPack(ByteBuf buff) {
            BotJoinGroupEventBPack pack = new BotJoinGroupEventBPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            return pack;
        }

        public static BotLeaveEventAPack botLeaveEventAPack(ByteBuf buff) {
            BotLeaveEventAPack pack = new BotLeaveEventAPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            return pack;
        }

        public static BotLeaveEventBPack botLeaveEventBPack(ByteBuf buff) {
            BotLeaveEventBPack pack = new BotLeaveEventBPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            return pack;
        }

        public static BotMuteEventPack botMuteEventPack(ByteBuf buff) {
            BotMuteEventPack pack = new BotMuteEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.time = buff.readInt();
            return pack;
        }

        public static BotOfflineEventAPack botOfflineEventAPack(ByteBuf buff) {
            BotOfflineEventAPack pack = new BotOfflineEventAPack();
            pack.qq = buff.readLong();
            pack.message = readString(buff);
            return pack;
        }

        public static BotOfflineEventBPack botOfflineEventBPack(ByteBuf buff) {
            BotOfflineEventBPack pack = new BotOfflineEventBPack();
            pack.qq = buff.readLong();
            pack.message = readString(buff);
            pack.title = readString(buff);
            return pack;
        }

        public static BotOfflineEventCPack botOfflineEventCPack(ByteBuf buff) {
            BotOfflineEventCPack pack = new BotOfflineEventCPack();
            pack.qq = buff.readLong();
            return pack;
        }

        public static BotOnlineEventPack botOnlineEventPack(ByteBuf buff) {
            BotOnlineEventPack pack = new BotOnlineEventPack();
            pack.qq = buff.readLong();
            return pack;
        }

        public static BotReloginEventPack botReloginEventPack(ByteBuf buff) {
            BotReloginEventPack pack = new BotReloginEventPack();
            pack.qq = buff.readLong();
            pack.message = readString(buff);
            return pack;
        }

        public static BotUnmuteEventPack botUnmuteEventPack(ByteBuf buff) {
            BotUnmuteEventPack pack = new BotUnmuteEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            return pack;
        }

        public static FriendAddEventPack friendAddEventPack(ByteBuf buff) {
            FriendAddEventPack pack = new FriendAddEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.nick = readString(buff);
            return pack;
        }

        public static FriendAvatarChangedEventPack friendAvatarChangedEventPack(ByteBuf buff) {
            FriendAvatarChangedEventPack pack = new FriendAvatarChangedEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.url = readString(buff);
            return pack;
        }

        public static FriendDeleteEventPack friendDeleteEventPack(ByteBuf buff) {
            FriendDeleteEventPack pack = new FriendDeleteEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            return pack;
        }

        public static FriendMessagePostSendEventPack friendMessagePostSendEventPack(ByteBuf buff) {
            FriendMessagePostSendEventPack pack = new FriendMessagePostSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.res = buff.readBoolean();
            pack.message = readStringList(buff);
            pack.error = readString(buff);
            return pack;
        }

        public static FriendMessagePreSendEventPack friendMessagePreSendEventPack(ByteBuf buff) {
            FriendMessagePreSendEventPack pack = new FriendMessagePreSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.message = readStringList(buff);
            return pack;
        }

        public static FriendRemarkChangeEventPack friendRemarkChangeEventPack(ByteBuf buff) {
            FriendRemarkChangeEventPack pack = new FriendRemarkChangeEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = readString(buff);
            pack.now = readString(buff);
            return pack;
        }

        public static GroupAllowAnonymousChatEventPack groupAllowAnonymousChatEventPack(ByteBuf buff) {
            GroupAllowAnonymousChatEventPack pack = new GroupAllowAnonymousChatEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = buff.readBoolean();
            pack.now = buff.readBoolean();
            return pack;
        }

        public static GroupAllowConfessTalkEventPack groupAllowConfessTalkEventPack(ByteBuf buff) {
            GroupAllowConfessTalkEventPack pack = new GroupAllowConfessTalkEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = buff.readBoolean();
            pack.now = buff.readBoolean();
            return pack;
        }

        public static GroupAllowMemberInviteEventPack groupAllowMemberInviteEventPack(ByteBuf buff) {
            GroupAllowMemberInviteEventPack pack = new GroupAllowMemberInviteEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = buff.readBoolean();
            pack.now = buff.readBoolean();
            return pack;
        }

        //public static GroupEntranceAnnouncementChangeEventPack GroupEntranceAnnouncementChangeEventPack(ByteBuf buff)
        //{
        //    GroupEntranceAnnouncementChangeEventPack pack = new()
        //    {
        //        qq = buff.readLong(),
        //        id = buff.readLong(),
        //        fid = buff.readLong(),
        //        old = buff.readBoolean(),
        //        now = buff.readBoolean()
        //    };
        //    return pack;
        //}
        public static GroupMessagePostSendEventPack groupMessagePostSendEventPack(ByteBuf buff) {
            GroupMessagePostSendEventPack pack = new GroupMessagePostSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.res = buff.readBoolean();
            pack.message = readStringList(buff);
            pack.error = readString(buff);
            return pack;
        }

        public static GroupMessagePreSendEventPack groupMessagePreSendEventPack(ByteBuf buff) {
            GroupMessagePreSendEventPack pack = new GroupMessagePreSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.message = readStringList(buff);
            return pack;
        }

        public static GroupMuteAllEventPack groupMuteAllEventPack(ByteBuf buff) {
            GroupMuteAllEventPack pack = new GroupMuteAllEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.old = buff.readBoolean();
            pack.now = buff.readBoolean();
            return pack;
        }

        public static GroupNameChangeEventPack groupNameChangeEventPack(ByteBuf buff) {
            GroupNameChangeEventPack pack = new GroupNameChangeEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.old = readString(buff);
            pack.now = readString(buff);
            return pack;
        }

        public static ImageUploadEventAPack imageUploadEventAPack(ByteBuf buff) {
            ImageUploadEventAPack pack = new ImageUploadEventAPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.uuid = readString(buff);
            return pack;
        }

        public static ImageUploadEventBPack imageUploadEventBPack(ByteBuf buff) {
            ImageUploadEventBPack pack = new ImageUploadEventBPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.index = buff.readInt();
            pack.name = readString(buff);
            pack.error = readString(buff);
            return pack;
        }

        public static MemberCardChangeEventPack memberCardChangeEventPack(ByteBuf buff) {
            MemberCardChangeEventPack pack = new MemberCardChangeEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = readString(buff);
            pack.now = readString(buff);
            return pack;
        }

        public static InviteMemberJoinEventPack inviteMemberJoinEventPack(ByteBuf buff) {
            InviteMemberJoinEventPack pack = new InviteMemberJoinEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.name = readString(buff);
            pack.ifid = buff.readLong();
            pack.iname = readString(buff);
            return pack;
        }

        public static MemberJoinEventPack memberJoinEventPack(ByteBuf buff) {
            MemberJoinEventPack pack = new MemberJoinEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.name = readString(buff);
            return pack;
        }

        public static MemberJoinRequestEventPack memberJoinRequestEventPack(ByteBuf buff) {
            MemberJoinRequestEventPack pack = new MemberJoinRequestEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.qif = buff.readLong();
            pack.message = readString(buff);
            pack.eventid = buff.readLong();
            return pack;
        }

        public static MemberLeaveEventAPack memberLeaveEventAPack(ByteBuf buff) {
            MemberLeaveEventAPack pack = new MemberLeaveEventAPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.eid = buff.readLong();
            return pack;
        }

        public static MemberLeaveEventBPack memberLeaveEventBPack(ByteBuf buff) {
            MemberLeaveEventBPack pack = new MemberLeaveEventBPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            return pack;
        }

        public static MemberMuteEventPack memberMuteEventPack(ByteBuf buff) {
            MemberMuteEventPack pack = new MemberMuteEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.eid = buff.readLong();
            pack.time = buff.readInt();
            return pack;
        }

        public static MemberPermissionChangeEventPack memberPermissionChangeEventPack(ByteBuf buff) {
            MemberPermissionChangeEventPack pack = new MemberPermissionChangeEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.old = MemberPermission.values()[buff.readInt()];
            pack.now = MemberPermission.values()[buff.readInt()];
            return pack;
        }

        public static MemberSpecialTitleChangeEventPack memberSpecialTitleChangeEventPack(ByteBuf buff) {
            MemberSpecialTitleChangeEventPack pack = new MemberSpecialTitleChangeEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.old = readString(buff);
            pack.now = readString(buff);
            return pack;
        }

        public static MemberUnmuteEventPack memberUnmuteEventPack(ByteBuf buff) {
            MemberUnmuteEventPack pack = new MemberUnmuteEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.eid = buff.readLong();
            return pack;
        }

        public static MessageRecallEventAPack messageRecallEventAPack(ByteBuf buff) {
            MessageRecallEventAPack pack = new MessageRecallEventAPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.time = buff.readInt();
            int a = buff.readInt();
            pack.mid = new int[a];
            for (int i = 0; i < a; i++) {
                pack.mid[i] = buff.readInt();
            }
            return pack;
        }

        public static MessageRecallEventBPack messageRecallEventBPack(ByteBuf buff) {
            MessageRecallEventBPack pack = new MessageRecallEventBPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.time = buff.readInt();
            pack.fid = buff.readLong();
            pack.oid = buff.readLong();
            int a = buff.readInt();
            pack.mid = new int[a];
            for (int i = 0; i < a; i++) {
                pack.mid[i] = buff.readInt();
            }
            return pack;
        }

        public static NewFriendRequestEventPack newFriendRequestEventPack(ByteBuf buff) {
            NewFriendRequestEventPack pack = new NewFriendRequestEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.message = readString(buff);
            pack.eventid = buff.readLong();
            return pack;
        }

        public static TempMessagePostSendEventPack tempMessagePostSendEventPack(ByteBuf buff) {
            TempMessagePostSendEventPack pack = new TempMessagePostSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.res = buff.readBoolean();
            pack.message = readStringList(buff);
            pack.error = readString(buff);
            return pack;
        }

        public static TempMessagePreSendEventPack tempMessagePreSendEventPack(ByteBuf buff) {
            TempMessagePreSendEventPack pack = new TempMessagePreSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.message = readStringList(buff);
            return pack;
        }

        public static GroupMessageEventPack groupMessageEventPack(ByteBuf buff) {
            GroupMessageEventPack pack = new GroupMessageEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.name = readString(buff);
            pack.ids1 = readIntList(buff);
            pack.ids2 = readIntList(buff);
            pack.permission = MemberPermission.values()[buff.readInt()];
            pack.message = readStringList(buff);
            return pack;
        }

        public static TempMessageEventPack tempMessageEventPack(ByteBuf buff) {
            TempMessageEventPack pack = new TempMessageEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.name = readString(buff);
            pack.ids1 = readIntList(buff);
            pack.ids2 = readIntList(buff);
            pack.permission = MemberPermission.values()[buff.readInt()];
            pack.message = readStringList(buff);
            pack.time = buff.readInt();
            return pack;
        }

        public static FriendMessageEventPack friendMessageEventPack(ByteBuf buff) {
            FriendMessageEventPack pack = new FriendMessageEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.name = readString(buff);
            pack.ids1 = readIntList(buff);
            pack.ids2 = readIntList(buff);
            pack.message = readStringList(buff);
            pack.time = buff.readInt();
            return pack;
        }

        public static FriendInputStatusChangedEventPack friendInputStatusChangedEventPack(ByteBuf buff) {
            FriendInputStatusChangedEventPack pack = new FriendInputStatusChangedEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.input = buff.readBoolean();
            return pack;
        }

        public static FriendNickChangedEventPack friendNickChangedEventPack(ByteBuf buff) {
            FriendNickChangedEventPack pack = new FriendNickChangedEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = readString(buff);
            pack.now = readString(buff);
            return pack;
        }

        public static MemberJoinRetrieveEventPack memberJoinRetrieveEventPack(ByteBuf buff) {
            MemberJoinRetrieveEventPack pack = new MemberJoinRetrieveEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            return pack;
        }

        public static BotJoinGroupEventRetrieveEventPack botJoinGroupEventRetrieveEventPack(ByteBuf buff) {
            BotJoinGroupEventRetrieveEventPack pack = new BotJoinGroupEventRetrieveEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            return pack;
        }

        public static NudgedEventPack nudgedEventPack(ByteBuf buff) {
            NudgedEventPack pack = new NudgedEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.fid = buff.readLong();
            pack.aid = buff.readLong();
            pack.action = readString(buff);
            pack.suffix = readString(buff);
            return pack;
        }

        public static GroupTalkativeChangePack groupTalkativeChangePack(ByteBuf buff) {
            GroupTalkativeChangePack pack = new GroupTalkativeChangePack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.old = buff.readLong();
            pack.now = buff.readLong();
            return pack;
        }

        public static OtherClientOnlineEventPack otherClientOnlineEventPack(ByteBuf buff) {
            OtherClientOnlineEventPack pack = new OtherClientOnlineEventPack();
            pack.qq = buff.readLong();
            pack.appId = buff.readInt();
            pack.kind = readString(buff);
            pack.platform = readString(buff);
            pack.deviceName = readString(buff);
            pack.deviceKind = readString(buff);
            return pack;
        }

        public static OtherClientOfflineEventPack otherClientOfflineEventPack(ByteBuf buff) {
            OtherClientOfflineEventPack pack = new OtherClientOfflineEventPack();
            pack.qq = buff.readLong();
            pack.appId = buff.readInt();
            pack.platform = readString(buff);
            pack.deviceName = readString(buff);
            pack.deviceKind = readString(buff);
            return pack;
        }

        public static OtherClientMessageEventPack otherClientMessageEventPack(ByteBuf buff) {
            OtherClientMessageEventPack pack = new OtherClientMessageEventPack();
            pack.qq = buff.readLong();
            pack.appId = buff.readInt();
            pack.platform = readString(buff);
            pack.deviceName = readString(buff);
            pack.deviceKind = readString(buff);
            pack.message = readStringList(buff);
            return pack;
        }

        public static GroupMessageSyncEventPack groupMessageSyncEventPack(ByteBuf buff) {
            GroupMessageSyncEventPack pack = new GroupMessageSyncEventPack();
            pack.qq = buff.readLong();
            pack.time = buff.readInt();
            pack.message = readStringList(buff);
            return pack;
        }

        public static GroupDisbandPack groupDisbandPack(ByteBuf buff) {
            GroupDisbandPack pack = new GroupDisbandPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            return pack;
        }

        public static StrangerMessageEventPack strangerMessageEventPack(ByteBuf buff) {
            StrangerMessageEventPack pack = new StrangerMessageEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.name = readString(buff);
            pack.ids1 = readIntList(buff);
            pack.ids2 = readIntList(buff);
            pack.message = readStringList(buff);
            pack.time = buff.readInt();
            return pack;
        }

        public static StrangerMessagePreSendEventPack strangerMessagePreSendEventPack(ByteBuf buff) {
            StrangerMessagePreSendEventPack pack = new StrangerMessagePreSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.message = readStringList(buff);
            return pack;
        }

        public static StrangerMessagePostSendEventPack strangerMessagePostSendEventPack(ByteBuf buff) {
            StrangerMessagePostSendEventPack pack = new StrangerMessagePostSendEventPack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.res = buff.readBoolean();
            pack.message = readStringList(buff);
            pack.error = readString(buff);
            return pack;
        }

        public static StrangerRelationChangePack strangerRelationChangePack(ByteBuf buff) {
            StrangerRelationChangePack pack = new StrangerRelationChangePack();
            pack.qq = buff.readLong();
            pack.id = buff.readLong();
            pack.type = buff.readInt();
            return pack;
        }
    }

    static class PackEncode {
        public static void writeString(ByteBuf buf, String data) {
            if (data == null)
                return;
            byte[] temp = data.getBytes(StandardCharsets.UTF_8);
            buf.writeInt(temp.length);
            buf.writeBytes(temp);
        }

        private static void writeStringList(ByteBuf buf, List<String> message) {
            buf.writeInt(message.size());
            for (String item : message) {
                writeString(buf, item);
            }
        }

        public static void writeLongList(ByteBuf buff, List<Long> list) {
            buff.writeInt(list.size());
            for (long item : list) {
                buff.writeLong(item);
            }
        }

        public static void writeBytes1(ByteBuf buff, byte[] data) {
            buff.writeInt(data.length);
            buff.writeBytes(data, 0, data.length);
        }

        public static void writeIntList(ByteBuf buff, int[] data) {
            buff.writeInt(data.length);
            for (int item : data) {
                buff.writeInt(item);
            }
        }

        public static ByteBuf ToPack(StartPack pack) {
            if (pack.qqList == null)
                pack.qqList = new ArrayList<>();
            if (pack.groups == null)
                pack.groups = new ArrayList<>();
            if (pack.reg == null)
                pack.reg = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(0);
            writeString(buff, pack.name);
            buff.writeInt(pack.reg.size());
            for (int item : pack.reg) {
                buff.writeInt(item);
            }
            writeLongList(buff, pack.groups);
            writeLongList(buff, pack.qqList);
            buff.writeLong(pack.qq);
            return buff;
        }

        public static ByteBuf ToPack(SendGroupMessagePack pack) {
            if (pack.ids == null)
                pack.ids = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(52)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeStringList(buff, pack.message);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupPrivateMessagePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(53)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid);
            writeStringList(buff, pack.message);

            return buff;
        }

        public static ByteBuf ToPack(SendFriendMessagePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(54)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeStringList(buff, pack.message);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(GetPack pack, byte index) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(index).writeLong(pack.qq);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(GroupGetMemberInfoPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(57)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(GroupGetSettingPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(58)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(EventCallPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(59)
                    .writeLong(pack.qq)
                    .writeLong(pack.eventid)
                    .writeInt(pack.dofun);
            writeStringList(buff, pack.arg);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupImagePack pack) {
            if (pack.data == null)
                pack.data = new byte[0];
            if (pack.ids == null)
                pack.ids = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(61)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeBytes1(buff, pack.data);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupPrivateImagePack pack) {
            if (pack.data == null)
                pack.data = new byte[0];
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(62)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid);
            writeBytes1(buff, pack.data);

            return buff;
        }

        public static ByteBuf ToPack(SendFriendImagePack pack) {
            if (pack.data == null)
                pack.data = new byte[0];
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(63)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeBytes1(buff, pack.data);

            return buff;
        }

        public static ByteBuf ToPack(GroupKickMemberPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(64)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid)
                    .writeBoolean(pack.black);

            return buff;
        }

        public static ByteBuf ToPack(GroupMuteMemberPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(65)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid)
                    .writeInt(pack.time);

            return buff;
        }

        public static ByteBuf ToPack(GroupUnmuteMemberPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(66)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid);

            return buff;
        }

        public static ByteBuf ToPack(GroupMuteAllPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(67)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);

            return buff;
        }

        public static ByteBuf ToPack(GroupUnmuteAllPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(68)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);

            return buff;
        }

        public static ByteBuf ToPack(GroupSetMemberCardPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(69)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid);
            writeString(buff, pack.card);

            return buff;
        }

        public static ByteBuf ToPack(GroupSetNamePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(70)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.name);

            return buff;
        }

        public static ByteBuf ToPack(ReCallMessagePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(71)
                    .writeLong(pack.qq)
                    .writeInt(pack.id);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupSoundPack pack) {
            if (pack.data == null)
                pack.data = new byte[0];
            if (pack.ids == null)
                pack.ids = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(74)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeBytes1(buff, pack.data);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupImageFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(75)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.file);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupPrivateImageFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(76)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid);
            writeString(buff, pack.file);

            return buff;
        }

        public static ByteBuf ToPack(SendFriendImageFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(77)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupSoundFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(78)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.file);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendFriendNudgePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(83)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupMemberNudgePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(84)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid);

            return buff;
        }

        public static ByteBuf ToPack(GetImageUrlPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(90)
                    .writeLong(pack.qq);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(GetMemberInfoPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(91)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(GetFriendInfoPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(92)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(SendMusicSharePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(93)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid)
                    .writeInt(pack.type)
                    .writeInt(pack.type1);
            writeString(buff, pack.title);
            writeString(buff, pack.summary);
            writeString(buff, pack.jumpUrl);
            writeString(buff, pack.pictureUrl);
            writeString(buff, pack.musicUrl);

            return buff;
        }

        public static ByteBuf ToPack(GroupSetEssenceMessagePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(94)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeIntList(buff, pack.ids1);
            writeIntList(buff, pack.ids2);

            return buff;
        }

        public static ByteBuf ToPack(MessageBuffPack pack) {
            if (pack.imgData == null)
                pack.imgData = new byte[0];
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(95)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid)
                    .writeInt(pack.type)
                    .writeBoolean(pack.send);
            writeStringList(buff, pack.text);
            writeString(buff, pack.imgurl);
            writeBytes1(buff, pack.imgData);

            return buff;
        }

        public static ByteBuf ToPack(SendFriendDicePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeInt(pack.dice);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupDicePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(97)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeInt(pack.dice);

            return buff;
        }

        public static ByteBuf ToPack(SendGroupPrivateDicePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(98)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid)
                    .writeInt(pack.dice);

            return buff;
        }

        public static ByteBuf ToPack(GroupAddFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(99)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.file);
            writeString(buff, pack.name);

            return buff;
        }

        public static ByteBuf ToPack(GroupDeleteFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(100)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.fid);

            return buff;
        }

        public static ByteBuf ToPack(GroupGetFilesPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(101)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(GroupMoveFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(102)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.fid);
            writeString(buff, pack.dir);

            return buff;
        }

        public static ByteBuf ToPack(GroupRenameFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(103)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.fid);
            writeString(buff, pack.now);

            return buff;
        }

        public static ByteBuf ToPack(GroupAddDirPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(104)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.dir);

            return buff;
        }

        public static ByteBuf ToPack(GroupDeleteDirPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(105)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.dir);

            return buff;
        }

        public static ByteBuf ToPack(GroupRenameDirPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(106)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.old);
            writeString(buff, pack.now);

            return buff;
        }

        public static ByteBuf ToPack(GroupDownloadFilePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(107)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.fid);
            writeString(buff, pack.dir);

            return buff;
        }

        public static ByteBuf ToPack(GroupSetAdminPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(108)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeLong(pack.fid)
                    .writeBoolean(pack.type);

            return buff;
        }

        public static ByteBuf ToPack(GroupGetAnnouncementsPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(109)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.uuid);

            return buff;
        }

        public static ByteBuf ToPack(GroupAddAnnouncementPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(110)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.imageFile);
            buff.writeBoolean(pack.sendToNewMember)
                    .writeBoolean(pack.isPinned)
                    .writeBoolean(pack.showEditCard)
                    .writeBoolean(pack.showPopup)
                    .writeBoolean(pack.requireConfirmation);
            writeString(buff, pack.text);

            return buff;
        }

        public static ByteBuf ToPack(GroupDeleteAnnouncementPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(111)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.fid);

            return buff;
        }

        public static ByteBuf ToPack(SendFriendSoundFilePack pack) {
            if (pack.ids == null)
                pack.ids = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(112)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.file);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(GroupSetAllowMemberInvitePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(114)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeBoolean(pack.enable);

            return buff;
        }

        public static ByteBuf ToPack(GroupSetAnonymousChatEnabledPack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(115)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeBoolean(pack.enable);

            return buff;
        }

        public static ByteBuf ToPack(SendStrangerMessagePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(117)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeStringList(buff, pack.message);

            return buff;
        }

        public static ByteBuf ToPack(SendStrangerImageFilePack pack) {
            if (pack.ids == null)
                pack.ids = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(118)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.file);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendStrangerDicePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(119)
                    .writeLong(pack.qq)
                    .writeLong(pack.id)
                    .writeInt(pack.dice);

            return buff;
        }

        public static ByteBuf ToPack(SendStrangerNudgePack pack) {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(120)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);

            return buff;
        }

        public static ByteBuf ToPack(SendStrangerSoundFilePack pack) {
            if (pack.ids == null)
                pack.ids = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(121)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeString(buff, pack.file);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf ToPack(SendFriendSoundPack pack) {
            if (pack.data == null)
                pack.data = new byte[0];
            if (pack.ids == null)
                pack.ids = new ArrayList<>();
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(126)
                    .writeLong(pack.qq)
                    .writeLong(pack.id);
            writeBytes1(buff, pack.data);
            writeLongList(buff, pack.ids);

            return buff;
        }

        public static ByteBuf TestPack() {
            ByteBuf buff = Unpooled.buffer();
            buff.writeByte(60);
            return buff;
        }
    }
}

