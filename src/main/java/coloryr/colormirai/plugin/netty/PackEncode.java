package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.plugin.pack.from.GetImageUrlPack;
import coloryr.colormirai.plugin.pack.re.*;
import coloryr.colormirai.plugin.pack.to.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class PackEncode {
    public static void writeString( ByteBuf buf, String data){
        byte[] temp = data.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(temp.length);
        buf.writeBytes(temp);
    }

    private static void writeStringList(ByteBuf buf, List<String> message) {
        buf.writeInt(message.size());
        for (String item : message){
            writeString(buf, item);
        }
    }

    public static void writeMemberInfoPack(ByteBuf buf, MemberInfoPack info){
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
    }

    public static void writeFriendInfoPack(ByteBuf buf, FriendInfoPack info){
        buf.writeLong(info.id);
        writeString(buf, info.img);
        writeString(buf, info.remark);
        writeString(buf, info.userProfile.getNickname());
        writeString(buf, info.userProfile.getEmail());
        buf.writeInt(info.userProfile.getAge());
        buf.writeInt(info.userProfile.getQLevel());
        buf.writeInt(info.userProfile.getSex().ordinal());
        writeString(buf, info.userProfile.getSign());
    }

    public static void writeGroupFileInfo(ByteBuf buf, GroupFileInfo info){
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

    public static void writeGroupAnnouncement(ByteBuf buf, GroupAnnouncement info){
        buf.writeLong(info.senderId);
        writeString(buf, info.fid);
        buf.writeBoolean(info.allConfirmed);
        buf.writeLong(info.publicationTime);
        writeString(buf, info.content);
        writeString(buf, info.image);
        buf.writeBoolean(info.sendToNewMember);
        buf.writeBoolean(info.isPinned);
        buf.writeBoolean(info.showEditCard);
        buf.writeBoolean(info.showPopup);
        buf.writeBoolean(info.requireConfirmation);
    }

    public static ByteBuf startPack(List<Long> botsKey) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(botsKey.size());
        for (long item : botsKey) {
            buf.writeLong(item);
        }
        return buf;
    }

    public static ByteBuf listGroupPack(ListGroupPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(55);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.groups.size());
        for (GroupInfo info : pack.groups){
            buf.writeLong(info.id);
            writeString(buf, info.name);
            writeString(buf, info.img);
            buf.writeLong(info.oid);
            buf.writeInt(info.per.getLevel());
        }
        return buf;
    }

    public static ByteBuf listFriendPack(ListFriendPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(56);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.friends.size());
        for (FriendInfoPack info : pack.friends){
            writeFriendInfoPack(buf, info);
        }
        return buf;
    }

    public static ByteBuf listMemberPack(ListMemberPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(57);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.members.size());
        for (MemberInfoPack info : pack.members){
            writeMemberInfoPack(buf, info);
        }
        return buf;
    }

    public static ByteBuf groupSettingPack(GroupSettingPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(58);
        buf.writeLong(pack.qq);
        buf.writeBoolean(pack.setting.isMuteAll());
        buf.writeBoolean(pack.setting.isAllowMemberInvite());
        buf.writeBoolean(pack.setting.isAutoApproveEnabled());
        buf.writeBoolean(pack.setting.isAnonymousChatEnabled());
        return buf;
    }

    public static ByteBuf getImageUrlPack(GetImageUrlPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(90);
        buf.writeLong(pack.qq);
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf memberInfoPack(MemberInfoPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(91);
        buf.writeLong(pack.qq);
        writeMemberInfoPack(buf, pack);
        return buf;
    }

    public static ByteBuf friendInfoPack(FriendInfoPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(92);
        buf.writeLong(pack.qq);
        writeFriendInfoPack(buf, pack);
        return buf;
    }

    public static ByteBuf groupFilesPack(GroupFilesPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(101);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.files.size());
        for (GroupFileInfo info : pack.files){
            writeGroupFileInfo(buf, info);
        }
        return buf;
    }

    public static ByteBuf groupAnnouncementsPack(GroupAnnouncementsPack pack) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(109);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.list.size());
        for (GroupAnnouncement info : pack.list){
            writeGroupAnnouncement(buf, info);
        }
        return buf;
    }

    public static ByteBuf testPack(){
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(60);
        return buf;
    }

    public static ByteBuf beforeImageUploadPack(BeforeImageUploadPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(1);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.name);
        return buf;
    }

    public static ByteBuf botAvatarChangedPack(BotAvatarChangedPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(2);
        buf.writeLong(pack.qq);
        return buf;
    }

    public static ByteBuf botGroupPermissionChangePack(BotGroupPermissionChangePack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(3);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.old.getLevel());
        buf.writeInt(pack.now.getLevel());
        return buf;
    }

    public static ByteBuf botInvitedJoinGroupRequestEventPack(BotInvitedJoinGroupRequestEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(4);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eventid);
        return buf;
    }

    public static ByteBuf botJoinGroupEventAPack(BotJoinGroupEventAPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(5);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf botJoinGroupEventBPack (BotJoinGroupEventBPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(6);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf botLeaveEventAPack(BotLeaveEventAPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(7);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf botLeaveEventBPack(BotLeaveEventBPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(8);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf botMuteEventPack(BotMuteEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(9);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf botOfflineEventAPack(BotOfflineEventAPack pack, int index){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(index);
        buf.writeLong(pack.qq);
        writeString(buf, pack.message);
        return buf;
    }

    public static ByteBuf botOfflineEventBPack(BotOfflineEventBPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(11);
        buf.writeLong(pack.qq);
        writeString(buf, pack.message);
        writeString(buf, pack.title);
        return buf;
    }

    public static ByteBuf botOfflineEventCPack(BotOfflineEventCPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(14);
        buf.writeLong(pack.qq);
        return buf;
    }

    public static ByteBuf botOnlineEventPack(BotOnlineEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(15);
        buf.writeLong(pack.qq);
        return buf;
    }

    public static ByteBuf botReloginEventPack(BotReloginEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(16);
        buf.writeLong(pack.qq);
        writeString(buf, pack.message);
        return buf;
    }

    public static ByteBuf botUnmuteEventPack(BotUnmuteEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(17);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf friendAddEventPack(FriendAddEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(18);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.nick);
        return buf;
    }

    public static ByteBuf friendAvatarChangedEventPack(FriendAvatarChangedEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(19);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.url);
        return buf;
    }

    public static ByteBuf friendDeleteEventPack(FriendDeleteEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(20);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf friendMessagePostSendEventPack(FriendMessagePostSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(21);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf friendMessagePreSendEventPack(FriendMessagePreSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(22);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf friendRemarkChangeEventPack(FriendRemarkChangeEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(23);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf groupAllowAnonymousChatEventPack(GroupAllowAnonymousChatEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(24);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    public static ByteBuf groupAllowConfessTalkEventPack(GroupAllowConfessTalkEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(25);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    public static ByteBuf groupAllowMemberInviteEventPack(GroupAllowMemberInviteEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(26);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    public static ByteBuf groupEntranceAnnouncementChangeEventPack(GroupEntranceAnnouncementChangeEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(27);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf groupMessagePostSendEventPack(GroupMessagePostSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(28);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf groupMessagePreSendEventPack(GroupMessagePreSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(29);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupMuteAllEventPack(GroupMuteAllEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(30);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeBoolean(pack.old);
        buf.writeBoolean(pack.now);
        return buf;
    }

    public static ByteBuf groupNameChangeEventPack(GroupNameChangeEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(31);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf imageUploadEventAPack(ImageUploadEventAPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(32);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.uuid);
        return buf;
    }

    public static ByteBuf imageUploadEventBPack(ImageUploadEventBPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(33);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.index);
        writeString(buf, pack.name);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf memberCardChangeEventPack(MemberCardChangeEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(34);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf inviteMemberJoinEventPack(InviteMemberJoinEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(35);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        buf.writeLong(pack.ifid);
        writeString(buf, pack.iname);
        return buf;
    }

    public static ByteBuf memberJoinEventPack(MemberJoinEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(36);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        return buf;
    }

    public static ByteBuf memberJoinRequestEventPack(MemberJoinRequestEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(37);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.qif);
        writeString(buf, pack.message);
        buf.writeLong(pack.eventid);
        return buf;
    }

    public static ByteBuf memberLeaveEventAPack(MemberLeaveEventAPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(38);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eid);
        return buf;
    }

    public static ByteBuf memberLeaveEventBPack(MemberLeaveEventBPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(39);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf memberMuteEventPack(MemberMuteEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(40);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eid);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf memberPermissionChangeEventPack(MemberPermissionChangeEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(41);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeInt(pack.old.getLevel());
        buf.writeInt(pack.now.getLevel());
        return buf;
    }

    public static ByteBuf memberSpecialTitleChangeEventPack(MemberSpecialTitleChangeEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(42);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf memberUnmuteEventPack(MemberUnmuteEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(43);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.eid);
        return buf;
    }

    public static ByteBuf messageRecallEventAPack(MessageRecallEventAPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(44);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.mid.length);
        for (int item : pack.mid){
            buf.writeInt(item);
        }
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf messageRecallEventBPack(MessageRecallEventBPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(45);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.mid.length);
        for (int item : pack.mid){
            buf.writeInt(item);
        }
        buf.writeInt(pack.time);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.oid);
        return buf;
    }

    public static ByteBuf newFriendRequestEventPack(NewFriendRequestEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(46);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.message);
        buf.writeLong(pack.eventid);
        return buf;
    }

    public static ByteBuf tempMessagePostSendEventPack(TempMessagePostSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(47);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf tempMessagePreSendEventPack(TempMessagePreSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(48);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupMessageEventPack(GroupMessageEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(49);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        buf.writeInt(pack.permission.getLevel());
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf tempMessageEventPack(TempMessageEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(50);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        writeString(buf, pack.name);
        buf.writeInt(pack.permission.getLevel());
        writeStringList(buf, pack.message);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf friendMessageEventPack(FriendMessageEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(51);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.name);
        writeStringList(buf, pack.message);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf friendInputStatusChangedEventPack(FriendInputStatusChangedEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(72);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.input);
        return buf;
    }

    public static ByteBuf friendNickChangedEventPack(FriendNickChangedEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(73);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.old);
        writeString(buf, pack.now);
        return buf;
    }

    public static ByteBuf memberJoinRetrieveEventPack(MemberJoinRetrieveEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(79);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        return buf;
    }

    public static ByteBuf botJoinGroupEventRetrieveEventPack(BotJoinGroupEventRetrieveEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(80);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf nudgedEventPack(NudgedEventPack pack, int index){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(index);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.fid);
        buf.writeLong(pack.aid);
        writeString(buf, pack.action);
        writeString(buf, pack.suffix);
        return buf;
    }

    public static ByteBuf groupTalkativeChangePack(GroupTalkativeChangePack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(85);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeLong(pack.old);
        buf.writeLong(pack.now);
        return buf;
    }

    public static ByteBuf otherClientOnlineEventPack(OtherClientOnlineEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(86);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.appId);
        writeString(buf, pack.kind);
        writeString(buf, pack.platform);
        writeString(buf, pack.deviceName);
        writeString(buf, pack.deviceKind);
        return buf;
    }

    public static ByteBuf otherClientOfflineEventPack(OtherClientOfflineEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(87);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.appId);
        writeString(buf, pack.platform);
        writeString(buf, pack.deviceName);
        writeString(buf, pack.deviceKind);
        return buf;
    }

    public static ByteBuf otherClientMessageEventPack(OtherClientMessageEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(88);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.appId);
        writeString(buf, pack.platform);
        writeString(buf, pack.deviceName);
        writeString(buf, pack.deviceKind);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupMessageSyncEventPack(GroupMessageSyncEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(89);
        buf.writeLong(pack.qq);
        buf.writeInt(pack.time);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf groupDisbandPack(GroupDisbandPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(113);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        return buf;
    }

    public static ByteBuf strangerMessageEventPack(StrangerMessageEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(116);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeString(buf, pack.name);
        writeStringList(buf, pack.message);
        buf.writeInt(pack.time);
        return buf;
    }

    public static ByteBuf strangerMessagePreSendEventPack(StrangerMessagePreSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(122);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        writeStringList(buf, pack.message);
        return buf;
    }

    public static ByteBuf strangerMessagePostSendEventPack(StrangerMessagePostSendEventPack pack){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(123);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeBoolean(pack.res);
        writeStringList(buf, pack.message);
        writeString(buf, pack.error);
        return buf;
    }

    public static ByteBuf strangerRelationChangePack(StrangerRelationChangePack pack, int index){
        ByteBuf buf = Unpooled.buffer();
        buf.writeByte(index);
        buf.writeLong(pack.qq);
        buf.writeLong(pack.id);
        buf.writeInt(pack.type);
        return buf;
    }
}
