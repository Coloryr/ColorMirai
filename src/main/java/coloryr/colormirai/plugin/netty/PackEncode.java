package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.plugin.pack.from.GetImageUrlPack;
import coloryr.colormirai.plugin.pack.re.*;
import coloryr.colormirai.plugin.pack.to.BeforeImageUploadPack;
import coloryr.colormirai.plugin.pack.to.BotAvatarChangedPack;
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
        buf.writeByte(2);-
    }
}
