package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.plugin.pack.re.*;
import coloryr.colormirai.plugin.pack.to.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.mamoe.mirai.contact.active.MemberMedalType;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

public class PackEncode {
    public static void writeString(ByteBuf buf, String data) {
        if (data == null)
            return;
        byte[] temp = data.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(temp.length);
        buf.writeBytes(temp);
    }

    public static void writeIntList(ByteBuf buf, int[] ids) {
        int temp = ids.length;
        buf.writeInt(temp);
        for (int item : ids) {
            buf.writeInt(item);
        }
    }

    public static void writeIntList(ByteBuf buf, Set<Integer> ids) {
        int temp = ids.size();
        buf.writeInt(temp);
        for (int item : ids) {
            buf.writeInt(item);
        }
    }

    public static void writeStringList(ByteBuf buf, List<String> message) {
        buf.writeInt(message.size());
        for (String item : message) {
            writeString(buf, item);
        }
    }

    public static void writeLongList(ByteBuf buf, List<Long> ids) {
        buf.writeInt(ids.size());
        for (long item : ids) {
            buf.writeLong(item);
        }
    }

    public static void writeMemberInfoPack(ByteBuf buf, ReMemberInfoPack info) {
        buf.writeLong(info.id);
        buf.writeLong(info.fid);
        writeString(buf, info.nick);
        writeString(buf, info.img);
        buf.writeInt(info.per.getLevel());
        writeString(buf, info.nameCard);
        writeString(buf, info.specialTitle);
        writeString(buf, info.avatarUrl);
        buf.writeInt(info.muteTimeRemaining);
        buf.writeInt(info.joinTimestamp);
        buf.writeInt(info.lastSpeakTimestamp);
        writeString(buf, info.rankTitle);
        buf.writeInt(info.active.rank);
        buf.writeInt(info.active.point);
        writeIntList(buf, info.active.honors);
        buf.writeInt(info.active.temperature);
        writeString(buf, info.active.title);
        writeString(buf, info.active.color);
        buf.writeInt(info.active.wearing.getMask());
        buf.writeInt(info.active.medals.size());
        for (MemberMedalType item : info.active.medals) {
            buf.writeInt(item.getMask());
        }
        writeString(buf, info.uuid);
    }

    public static void writeFriendInfoPack(ByteBuf buf, ReFriendInfoPack info) {
        buf.writeLong(info.id);
        writeString(buf, info.img);
        writeString(buf, info.remark);
        writeString(buf, info.userProfile.getNickname());
        writeString(buf, info.userProfile.getEmail());
        buf.writeInt(info.userProfile.getAge());
        buf.writeInt(info.userProfile.getQLevel());
        buf.writeInt(info.userProfile.getSex().ordinal());
        writeString(buf, info.userProfile.getSign());
        buf.writeInt(info.groupId);
        writeString(buf, info.uuid);
    }

    public static void writeGroupFileInfo(ByteBuf buf, GroupFileInfo info) {
        writeString(buf, info.name);
        writeString(buf, info.id);
        writeString(buf, info.absolutePath);
        buf.writeBoolean(info.isFile);
        buf.writeBoolean(info.isFolder);
        buf.writeLong(info.size);
        buf.writeLong(info.uploaderId);
        buf.writeLong(info.uploadTime);
        buf.writeLong(info.lastModifyTime);
        buf.writeLong(info.expiryTime);
        writeString(buf, info.sha1);
        writeString(buf, info.md5);
    }

    public static void writeGroupAnnouncement(ByteBuf buf, GroupAnnouncement info) {
        buf.writeLong(info.senderId);
        writeString(buf, info.fid);
        buf.writeBoolean(info.allConfirmed);
        buf.writeInt(info.confirmedMembersCount);
        buf.writeLong(info.publicationTime);
        writeString(buf, info.content);
        writeString(buf, info.image);
        buf.writeBoolean(info.sendToNewMember);
        buf.writeBoolean(info.isPinned);
        buf.writeBoolean(info.showEditCard);
        buf.writeBoolean(info.showPopup);
        buf.writeBoolean(info.requireConfirmation);
    }

    public static void writeFriendGroup(ByteBuf buf, FriendGroupInfo info) {
        buf.writeInt(info.id);
        writeString(buf, info.name);
        buf.writeInt(info.count);
        writeLongList(buf, info.friends);
    }

    public static ByteBuf startPack(List<Long> botsKey) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(botsKey.size());
        for (long item : botsKey) {
            buf.writeLong(item);
        }
        return buf;
    }

    public static ByteBuf listGroupPack(ReListGroupPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(55);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.groups.size());
        for (GroupInfo info : pack.groups) {
            buf.writeLong(info.id);
            writeString(buf, info.name);
            writeString(buf, info.img);
            buf.writeLong(info.oid);
            buf.writeInt(info.per.getLevel());
        }
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf listFriendPack(ReListFriendPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(56);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.friends.size());
        for (ReFriendInfoPack info : pack.friends) {
            writeFriendInfoPack(buf, info);
        }
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf listMemberPack(ReListMemberPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(57);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.members.size());
        for (ReMemberInfoPack info : pack.members) {
            writeMemberInfoPack(buf, info);
        }
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf groupSettingPack(ReGroupSettingPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(58);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.setting.isMuteAll());
        buf.writeBoolean(pack.setting.isAllowMemberInvite());
        buf.writeBoolean(pack.setting.isAutoApproveEnabled());
        buf.writeBoolean(pack.setting.isAnonymousChatEnabled());
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf getImageUrlPack(ReGetImageUrlPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(90);
        buf.writeLong(pack.qq);
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf memberInfoPack(ReMemberInfoPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(91);
        buf.writeLong(pack.qq);
        writeMemberInfoPack(buf, pack);
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf friendInfoPack(ReFriendInfoPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(92);
        buf.writeLong(pack.qq);
        writeFriendInfoPack(buf, pack);
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf groupFilesPack(ReGroupFilesPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(101);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.files.size());
        for (GroupFileInfo info : pack.files) {
            writeGroupFileInfo(buf, info);
        }
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf groupAnnouncementsPack(ReGroupAnnouncementsPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(109);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.list.size());
        for (GroupAnnouncement info : pack.list) {
            writeGroupAnnouncement(buf, info);
        }
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf testPack() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(60);
        return buf;
    }

    public static ByteBuf beforeImageUploadPack(BeforeImageUploadPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.name);
        return buf;
    }

    public static ByteBuf botAvatarChangedPack(BotAvatarChangedPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(2);
        buf.writeLong(pack.qq);
        return buf;
    }

    public static ByteBuf botGroupPermissionChangePack(BotGroupPermissionChangePack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(3);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.old.getLevel());
        buf.writeInt(pack.now.getLevel());
        return buf;
    }

    public static ByteBuf botInvitedJoinGroupRequestEventPack(BotInvitedJoinGroupRequestEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(4);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eventid);
        return buf;
    }

    public static ByteBuf botJoinGroupEventAPack(BotJoinGroupEventAPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf botJoinGroupEventBPack(BotJoinGroupEventBPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(6);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf botLeaveEventAPack(BotLeaveEventAPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(7);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf botLeaveEventBPack(BotLeaveEventBPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(8);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf botMuteEventPack(BotMuteEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(9);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf botOfflineEventAPack(BotOfflineEventAPack pack, int index) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(index);
        buf.writeLong(pack.qq);
        writeString(buf, pack.message);
        return buf;
    }

    public static ByteBuf botOfflineEventBPack(BotOfflineEventBPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(11);
        buf.writeLong(pack.qq);
        writeString(buf, pack.message);
        writeString(buf, pack.title);
        return buf;
    }

    public static ByteBuf botOfflineEventCPack(BotOfflineEventCPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(14);
        buf.writeLong(pack.qq);
        return buf;
    }

    public static ByteBuf botOnlineEventPack(BotOnlineEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(15);
        buf.writeLong(pack.qq);
        return buf;
    }

    public static ByteBuf botReloginEventPack(BotReloginEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(16);
        buf.writeLong(pack.qq);
        writeString(buf, pack.message);
        return buf;
    }

    public static ByteBuf botUnmuteEventPack(BotUnmuteEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(17);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf friendAddEventPack(FriendAddEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(18);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.nick);
        return buf;
    }

    public static ByteBuf friendAvatarChangedEventPack(FriendAvatarChangedEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(19);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.url);
        return buf;
    }

    public static ByteBuf friendDeleteEventPack(FriendDeleteEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(20);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf friendMessagePostSendEventPack(FriendMessagePostSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(21);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeIntList(buf, pack.ids1);
        writeIntList(buf, pack.ids2);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf friendMessagePreSendEventPack(FriendMessagePreSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(22);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf friendRemarkChangeEventPack(FriendRemarkChangeEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(23);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf groupAllowAnonymousChatEventPack(GroupAllowAnonymousChatEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(24);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    public static ByteBuf groupAllowConfessTalkEventPack(GroupAllowConfessTalkEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(25);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    public static ByteBuf groupAllowMemberInviteEventPack(GroupAllowMemberInviteEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(26);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    //好友文件还未支持
//    public static ByteBuf downloadFilePack(ReDownloadFilePack pack) {
//        ByteBuf buf = Unpooled.buffer();
//        buf.writeInt(27);
//        buf.writeLong(pack.qq);
//        writeString(buf, pack.uuid);
//        buf.writeBoolean(pack.done);
//        writeString(buf, pack.message);
//        return buf;
//    }

    public static ByteBuf groupMessagePostSendEventPack(GroupMessagePostSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(28);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeIntList(buf, pack.ids1);
        writeIntList(buf, pack.ids2);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf groupMessagePreSendEventPack(GroupMessagePreSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(29);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupMuteAllEventPack(GroupMuteAllEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(30);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    public static ByteBuf groupNameChangeEventPack(GroupNameChangeEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(31);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf imageUploadEventAPack(ImageUploadEventAPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(32);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf imageUploadEventBPack(ImageUploadEventBPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(33);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.index);
        writeString(buf, pack.name);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf memberCardChangeEventPack(MemberCardChangeEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(34);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf inviteMemberJoinEventPack(InviteMemberJoinEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(35);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        buf.writeLong(pack.ifid);
        writeString(buf, pack.iname);
        return buf;
    }

    public static ByteBuf memberJoinEventPack(MemberJoinEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(36);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        return buf;
    }

    public static ByteBuf memberJoinRequestEventPack(MemberJoinRequestEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(37);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.qif);
        writeString(buf, pack.message);
        buf.writeLong(pack.eventid);
        return buf;
    }

    public static ByteBuf memberLeaveEventAPack(MemberLeaveEventAPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(38);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eid);
        return buf;
    }

    public static ByteBuf memberLeaveEventBPack(MemberLeaveEventBPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(39);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf memberMuteEventPack(MemberMuteEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(40);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eid);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf memberPermissionChangeEventPack(MemberPermissionChangeEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(41);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeInt(pack.old.getLevel());
        buf.writeInt(pack.now.getLevel());
        return buf;
    }

    public static ByteBuf memberSpecialTitleChangeEventPack(MemberSpecialTitleChangeEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(42);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf memberUnmuteEventPack(MemberUnmuteEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(43);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eid);
        return buf;
    }

    public static ByteBuf messageRecallEventAPack(MessageRecallEventAPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(44);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.time);
        buf.writeInt(pack.mid.length);
        for (int item : pack.mid) {
            buf.writeInt(item);
        }
        return buf;
    }

    public static ByteBuf messageRecallEventBPack(MessageRecallEventBPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(45);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.time);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.oid);
        buf.writeInt(pack.mid.length);
        for (int item : pack.mid) {
            buf.writeInt(item);
        }
        return buf;
    }

    public static ByteBuf newFriendRequestEventPack(NewFriendRequestEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(46);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.message);
        buf.writeLong(pack.eventid);
        return buf;
    }

    public static ByteBuf tempMessagePostSendEventPack(TempMessagePostSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(47);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeIntList(buf, pack.ids1);
        writeIntList(buf, pack.ids2);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf tempMessagePreSendEventPack(TempMessagePreSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(48);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupMessageEventPack(GroupMessageEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(49);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        buf.writeInt(pack.time);
        writeIntList(buf, pack.ids1);
        writeIntList(buf, pack.ids2);
        buf.writeInt(pack.permission.getLevel());
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf tempMessageEventPack(TempMessageEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(50);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        writeIntList(buf, pack.ids1);
        writeIntList(buf, pack.ids2);
        buf.writeInt(pack.permission.getLevel());
        writeStringList(buf, pack.message);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf friendMessageEventPack(FriendMessageEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(51);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.name);
        writeIntList(buf, pack.ids1);
        writeIntList(buf, pack.ids2);
        writeStringList(buf, pack.message);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf friendInputStatusChangedEventPack(FriendInputStatusChangedEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(72);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.input);
        return buf;
    }

    public static ByteBuf friendNickChangedEventPack(FriendNickChangedEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(73);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf memberJoinRetrieveEventPack(MemberJoinRetrieveEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(79);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf botJoinGroupEventRetrieveEventPack(BotJoinGroupEventRetrieveEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(80);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf nudgedEventPack(NudgedEventPack pack, int index) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(index);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.aid);
        writeString(buf, pack.action);
        writeString(buf, pack.suffix);
        return buf;
    }

    public static ByteBuf groupTalkativeChangePack(GroupTalkativeChangePack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(85);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.old);
        buf.writeLong(pack.now);
        return buf;
    }

    public static ByteBuf otherClientOnlineEventPack(OtherClientOnlineEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(86);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.appId);
        writeString(buf, pack.kind);
        writeString(buf, pack.platform);
        writeString(buf, pack.deviceName);
        writeString(buf, pack.deviceKind);
        return buf;
    }

    public static ByteBuf otherClientOfflineEventPack(OtherClientOfflineEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(87);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.appId);
        writeString(buf, pack.platform);
        writeString(buf, pack.deviceName);
        writeString(buf, pack.deviceKind);
        return buf;
    }

    public static ByteBuf otherClientMessageEventPack(OtherClientMessageEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(88);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.appId);
        writeString(buf, pack.platform);
        writeString(buf, pack.deviceName);
        writeString(buf, pack.deviceKind);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupMessageSyncEventPack(GroupMessageSyncEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(89);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.time);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupDisbandPack(GroupDisbandPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(113);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf strangerMessageEventPack(StrangerMessageEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(116);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.name);
        writeIntList(buf, pack.ids1);
        writeIntList(buf, pack.ids2);
        writeStringList(buf, pack.message);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf strangerMessagePreSendEventPack(StrangerMessagePreSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(122);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf strangerMessagePostSendEventPack(StrangerMessagePostSendEventPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(123);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf strangerRelationChangePack(StrangerRelationChangePack pack, int index) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(index);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.type);
        return buf;
    }

    public static ByteBuf reFriendGroupPack(ReFriendGroupPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(128);
        buf.writeLong(pack.qq);
        writeFriendGroup(buf, pack.info);
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf reListFriendGroupPack(ReListFriendGroupPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(129);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.infos.size());
        for (FriendGroupInfo info : pack.infos) {
            writeFriendGroup(buf, info);
        }
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf reMessagePack(ReMessagePack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(0);
        buf.writeLong(pack.qq);
        writeString(buf, pack.uuid);
        writeString(buf, pack.msg);
        return buf;
    }
}
