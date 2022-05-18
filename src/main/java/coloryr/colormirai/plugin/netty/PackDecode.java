package coloryr.colormirai.plugin.netty;

import coloryr.colormirai.plugin.pack.from.*;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PackDecode {
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

    public static StartPack startPack(ByteBuf buff) {
        StartPack pack = new StartPack();
        pack.Name = readString(buff);
        int length = buff.readInt();
        pack.Reg = new ArrayList<>();
        for (int a = 0; a < length; a++) {
            pack.Reg.add(buff.readInt());
        }
        pack.Groups = readLongList(buff);
        pack.QQs = readLongList(buff);
        pack.RunQQ = buff.readLong();
        return pack;
    }

    public static SendGroupMessagePack sendGroupMessagePack(ByteBuf buff) {
        SendGroupMessagePack pack = new SendGroupMessagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.message = readStringList(buff);
        return pack;
    }

    public static SendGroupPrivateMessagePack sendGroupPrivateMessagePack(ByteBuf buff) {
        SendGroupPrivateMessagePack pack = new SendGroupPrivateMessagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.message = readStringList(buff);
        return pack;
    }

    public static SendFriendMessagePack sendFriendMessagePack(ByteBuf buff) {
        SendFriendMessagePack pack = new SendFriendMessagePack();
        pack.qq = buff.readLong();
        pack.message = readStringList(buff);
        return pack;
    }

    public static GetPack getPack(ByteBuf buff) {
        GetPack pack = new GetPack();
        pack.qq = buff.readLong();
        return pack;
    }

    public static GroupGetMemberInfoPack groupGetMemberInfoPack(ByteBuf buff) {
        GroupGetMemberInfoPack pack = new GroupGetMemberInfoPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static GroupGetSettingPack groupGetSettingPack(ByteBuf buff) {
        GroupGetSettingPack pack = new GroupGetSettingPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static EventCallPack eventCallPack(ByteBuf buff) {
        EventCallPack pack = new EventCallPack();
        pack.qq = buff.readLong();
        pack.eventid = buff.readLong();
        pack.dofun = buff.readInt();
        pack.arg = readStringList(buff);
        return pack;
    }

    public static SendGroupImagePack sendGroupImagePack(ByteBuf buff) {
        SendGroupImagePack pack = new SendGroupImagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.data = readBytes(buff);
        return pack;
    }

    public static SendGroupPrivateImagePack sendGroupPrivateImagePack(ByteBuf buff) {
        SendGroupPrivateImagePack pack = new SendGroupPrivateImagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.data = readBytes(buff);
        return pack;
    }

    public static SendFriendImagePack sendFriendImagePack(ByteBuf buff) {
        SendFriendImagePack pack = new SendFriendImagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.data = readBytes(buff);
        return pack;
    }

    public static GroupKickMemberPack groupKickMemberPack(ByteBuf buff) {
        GroupKickMemberPack pack = new GroupKickMemberPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.black = buff.readBoolean();
        return pack;
    }

    public static GroupMuteMemberPack groupMuteMemberPack(ByteBuf buff) {
        GroupMuteMemberPack pack = new GroupMuteMemberPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.time = buff.readInt();
        return pack;
    }

    public static GroupUnmuteMemberPack groupUnmuteMemberPack(ByteBuf buff) {
        GroupUnmuteMemberPack pack = new GroupUnmuteMemberPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        return pack;
    }

    public static GroupMuteAllPack groupMuteAllPack(ByteBuf buff) {
        GroupMuteAllPack pack = new GroupMuteAllPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static GroupUnmuteAllPack groupUnmuteAllPack(ByteBuf buff) {
        GroupUnmuteAllPack pack = new GroupUnmuteAllPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static GroupSetMemberCardPack groupSetMemberCard(ByteBuf buff) {
        GroupSetMemberCardPack pack = new GroupSetMemberCardPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.card = readString(buff);
        return pack;
    }

    public static GroupSetNamePack groupSetNamePack(ByteBuf buff) {
        GroupSetNamePack pack = new GroupSetNamePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.name = readString(buff);
        return pack;
    }

    public static ReCallMessagePack reCallMessagePack(ByteBuf buff) {
        ReCallMessagePack pack = new ReCallMessagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        return pack;
    }

    public static SendGroupSoundPack sendGroupSoundPack(ByteBuf buff) {
        SendGroupSoundPack pack = new SendGroupSoundPack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        pack.data = readBytes(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendGroupImageFilePack sendGroupImageFilePack(ByteBuf buff) {
        SendGroupImageFilePack pack = new SendGroupImageFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendGroupPrivateImageFilePack sendGroupPrivateImageFilePack(ByteBuf buff){
        SendGroupPrivateImageFilePack pack = new SendGroupPrivateImageFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        pack.fid = buff.readInt();
        pack.file = readString(buff);
        return pack;
    }

    public static SendFriendImageFilePack sendFriendImageFilePack(ByteBuf buff){
        SendFriendImageFilePack pack = new SendFriendImageFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendGroupSoundFilePack sendGroupSoundFilePack(ByteBuf buff){
        SendGroupSoundFilePack pack = new SendGroupSoundFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendFriendNudgePack sendFriendNudgePack(ByteBuf buff){
        SendFriendNudgePack pack = new SendFriendNudgePack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        return pack;
    }

    public static SendGroupMemberNudgePack sendGroupMemberNudgePack(ByteBuf buff){
        SendGroupMemberNudgePack pack = new SendGroupMemberNudgePack();
        pack.qq = buff.readLong();
        pack.id = buff.readInt();
        pack.fid = buff.readLong();
        return pack;
    }

    public static  GetImageUrlPack getImageUrlPack(ByteBuf buff){
        GetImageUrlPack pack = new GetImageUrlPack();
        pack.qq = buff.readLong();
        pack.uuid = readString(buff);
        return pack;
    }

    public static GetMemberInfoPack getMemberInfo(ByteBuf buff){
        GetMemberInfoPack pack = new GetMemberInfoPack();
    }
}
