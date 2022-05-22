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
        pack.name = readString(buff);
        int length = buff.readInt();
        pack.reg = new ArrayList<>();
        for (int a = 0; a < length; a++) {
            pack.reg.add(buff.readInt());
        }
        pack.groups = readLongList(buff);
        pack.qqList = readLongList(buff);
        pack.qq = buff.readLong();
        return pack;
    }

    public static SendGroupMessagePack sendGroupMessagePack(ByteBuf buff) {
        SendGroupMessagePack pack = new SendGroupMessagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.message = readStringList(buff);
        pack.ids = readLongList(buff);
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
        pack.ids = readLongList(buff);
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
        pack.ids = readLongList(buff);
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

    public static GroupSetMemberCardPack groupSetMemberCardPack(ByteBuf buff) {
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
        pack.id = buff.readLong();
        pack.data = readBytes(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendGroupImageFilePack sendGroupImageFilePack(ByteBuf buff) {
        SendGroupImageFilePack pack = new SendGroupImageFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendGroupPrivateImageFilePack sendGroupPrivateImageFilePack(ByteBuf buff) {
        SendGroupPrivateImageFilePack pack = new SendGroupPrivateImageFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.file = readString(buff);
        return pack;
    }

    public static SendFriendImageFilePack sendFriendImageFilePack(ByteBuf buff) {
        SendFriendImageFilePack pack = new SendFriendImageFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendGroupSoundFilePack sendGroupSoundFilePack(ByteBuf buff) {
        SendGroupSoundFilePack pack = new SendGroupSoundFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendFriendNudgePack sendFriendNudgePack(ByteBuf buff) {
        SendFriendNudgePack pack = new SendFriendNudgePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static SendGroupMemberNudgePack sendGroupMemberNudgePack(ByteBuf buff) {
        SendGroupMemberNudgePack pack = new SendGroupMemberNudgePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        return pack;
    }

    public static GetImageUrlPack getImageUrlPack(ByteBuf buff) {
        GetImageUrlPack pack = new GetImageUrlPack();
        pack.qq = buff.readLong();
        pack.uuid = readString(buff);
        return pack;
    }

    public static GetMemberInfoPack getMemberInfoPack(ByteBuf buff) {
        GetMemberInfoPack pack = new GetMemberInfoPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        return pack;
    }

    public static GetFriendInfoPack getFriendInfoPack(ByteBuf buff) {
        GetFriendInfoPack pack = new GetFriendInfoPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static SendMusicSharePack sendMusicSharePack(ByteBuf buff) {
        SendMusicSharePack pack = new SendMusicSharePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.type = buff.readInt();
        pack.type1 = buff.readInt();
        pack.title = readString(buff);
        pack.summary = readString(buff);
        pack.jumpUrl = readString(buff);
        pack.pictureUrl = readString(buff);
        pack.musicUrl = readString(buff);
        return pack;
    }

    public static GroupSetEssenceMessagePack groupSetEssenceMessagePack(ByteBuf buff) {
        GroupSetEssenceMessagePack pack = new GroupSetEssenceMessagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.mid = buff.readInt();
        return pack;
    }

    public static MessageBuffPack messageBuffPack(ByteBuf buff) {
        MessageBuffPack pack = new MessageBuffPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.type = buff.readInt();
        pack.send = buff.readBoolean();
        pack.text = readStringList(buff);
        pack.imgurl = readString(buff);
        pack.imgData = readBytes(buff);
        return pack;
    }

    public static SendFriendDicePack sendFriendDicePack(ByteBuf buff) {
        SendFriendDicePack pack = new SendFriendDicePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.dice = buff.readInt();
        return pack;
    }

    public static SendGroupDicePack sendGroupDicePack(ByteBuf buff) {
        SendGroupDicePack pack = new SendGroupDicePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.dice = buff.readInt();
        return pack;
    }

    public static SendGroupPrivateDicePack sendGroupPrivateDicePack(ByteBuf buff) {
        SendGroupPrivateDicePack pack = new SendGroupPrivateDicePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.dice = buff.readInt();
        return pack;
    }

    public static GroupAddFilePack groupAddFilePack(ByteBuf buff) {
        GroupAddFilePack pack = new GroupAddFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.file = readString(buff);
        pack.name = readString(buff);
        return pack;
    }

    public static GroupDeleteFilePack groupDeleteFilePack(ByteBuf buff) {
        GroupDeleteFilePack pack = new GroupDeleteFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = readString(buff);
        return pack;
    }

    public static GroupGetFilesPack groupGetFilesPack(ByteBuf buff) {
        GroupGetFilesPack pack = new GroupGetFilesPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static GroupMoveFilePack groupMoveFilePack(ByteBuf buff) {
        GroupMoveFilePack pack = new GroupMoveFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = readString(buff);
        pack.dir = readString(buff);
        return pack;
    }

    public static GroupRenameFilePack groupRenameFilePack(ByteBuf buff) {
        GroupRenameFilePack pack = new GroupRenameFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = readString(buff);
        pack.now = readString(buff);
        return pack;
    }

    public static GroupAddDirPack groupAddDirPack(ByteBuf buff) {
        GroupAddDirPack pack = new GroupAddDirPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.dir = readString(buff);
        return pack;
    }

    public static GroupDeleteDirPack groupDeleteDirPack(ByteBuf buff) {
        GroupDeleteDirPack pack = new GroupDeleteDirPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.dir = readString(buff);
        return pack;
    }

    public static GroupRenameDirPack groupRenameDirPack(ByteBuf buff) {
        GroupRenameDirPack pack = new GroupRenameDirPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.old = readString(buff);
        pack.now = readString(buff);
        return pack;
    }

    public static GroupDownloadFilePack groupDownloadFilePack(ByteBuf buff) {
        GroupDownloadFilePack pack = new GroupDownloadFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = readString(buff);
        pack.dir = readString(buff);
        return pack;
    }

    public static GroupSetAdminPack groupSetAdminPack(ByteBuf buff) {
        GroupSetAdminPack pack = new GroupSetAdminPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = buff.readLong();
        pack.type = buff.readBoolean();
        return pack;
    }

    public static GroupGetAnnouncementsPack groupGetAnnouncementsPack(ByteBuf buff) {
        GroupGetAnnouncementsPack pack = new GroupGetAnnouncementsPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static GroupAddAnnouncementPack groupAddAnnouncementPack(ByteBuf buff) {
        GroupAddAnnouncementPack pack = new GroupAddAnnouncementPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.imageFile = readString(buff);
        pack.sendToNewMember = buff.readBoolean();
        pack.isPinned = buff.readBoolean();
        pack.showEditCard = buff.readBoolean();
        pack.showPopup = buff.readBoolean();
        pack.requireConfirmation = buff.readBoolean();
        pack.text = readString(buff);
        return pack;
    }

    public static GroupDeleteAnnouncementPack groupDeleteAnnouncementPack(ByteBuf buff) {
        GroupDeleteAnnouncementPack pack = new GroupDeleteAnnouncementPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.fid = readString(buff);
        return pack;
    }

    public static SendFriendSoundFilePack sendFriendSoundFilePack(ByteBuf buff) {
        SendFriendSoundFilePack pack = new SendFriendSoundFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static GroupSetAllowMemberInvitePack groupSetAllowMemberInvitePack(ByteBuf buff) {
        GroupSetAllowMemberInvitePack pack = new GroupSetAllowMemberInvitePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.enable = buff.readBoolean();
        return pack;
    }

    public static GroupSetAnonymousChatEnabledPack groupSetAnonymousChatEnabledPack(ByteBuf buff) {
        GroupSetAnonymousChatEnabledPack pack = new GroupSetAnonymousChatEnabledPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.enable = buff.readBoolean();
        return pack;
    }

    public static SendStrangerMessagePack sendStrangerMessagePack(ByteBuf buff) {
        SendStrangerMessagePack pack = new SendStrangerMessagePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.message = readStringList(buff);
        return pack;
    }

    public static SendStrangerImageFilePack sendStrangerImageFilePack(ByteBuf buff) {
        SendStrangerImageFilePack pack = new SendStrangerImageFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendStrangerDicePack sendStrangerDicePack(ByteBuf buff) {
        SendStrangerDicePack pack = new SendStrangerDicePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.dice = buff.readInt();
        return pack;
    }

    public static SendStrangerNudgePack sendStrangerNudgePack(ByteBuf buff) {
        SendStrangerNudgePack pack = new SendStrangerNudgePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        return pack;
    }

    public static SendStrangerSoundFilePack sendStrangerSoundFilePack(ByteBuf buff) {
        SendStrangerSoundFilePack pack = new SendStrangerSoundFilePack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.file = readString(buff);
        pack.ids = readLongList(buff);
        return pack;
    }

    public static SendFriendSoundPack sendFriendSoundPack(ByteBuf buff) {
        SendFriendSoundPack pack = new SendFriendSoundPack();
        pack.qq = buff.readLong();
        pack.id = buff.readLong();
        pack.data = readBytes(buff);
        pack.ids = readLongList(buff);
        return pack;
    }
}
