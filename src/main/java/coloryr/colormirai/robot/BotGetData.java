package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;
import coloryr.colormirai.plugin.pack.re.*;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.GroupSettings;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.active.MemberActive;
import net.mamoe.mirai.contact.active.MemberMedalInfo;
import net.mamoe.mirai.contact.active.MemberMedalType;
import net.mamoe.mirai.contact.friendgroup.FriendGroup;
import net.mamoe.mirai.data.GroupHonorType;
import net.mamoe.mirai.message.data.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BotGetData {
    public static List<GroupInfo> getGroups(ThePlugin plugin, String uuid, long qq) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            List<GroupInfo> list = new ArrayList<>();
            for (Group item : bot.getGroups()) {
                GroupInfo info = new GroupInfo();
                info.id = item.getId();
                info.name = item.getName();
                info.img = item.getAvatarUrl();
                info.oid = item.getOwner().getId();
                info.per = item.getBotPermission();
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group + Msg.data + Msg.get + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    private static ReFriendInfoPack makeFriendInfo(Friend item, String uuid) {
        FriendGroup group1 = item.getFriendGroup();
        ReFriendInfoPack info = new ReFriendInfoPack();
        info.groupId = group1.getId();
        info.id = item.getId();
        info.img = item.getAvatarUrl();
        info.remark = item.getRemark();
        info.userProfile = item.queryProfile();
        info.uuid = uuid;
        return info;
    }

    public static ReFriendInfoPack getFriend(ThePlugin plugin, String uuid, long qq, long id) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            Friend item = BotCheck.friend(plugin, bot, id, uuid);
            if (item == null) return null;
            return makeFriendInfo(item, uuid);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(id) + Msg.data + Msg.get + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    public static List<ReFriendInfoPack> getFriends(ThePlugin plugin, String uuid, long qq) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            List<ReFriendInfoPack> list = new ArrayList<>();
            for (Friend item : bot.getFriends()) {
                list.add(makeFriendInfo(item, ""));
            }
            return list;
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend + Msg.data + Msg.get + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    public static List<ReMemberInfoPack> getMembers(ThePlugin plugin, String uuid, long qq, long id, boolean fast) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            Group group = BotCheck.group(plugin, bot, id, uuid);
            if (group == null) return null;
            List<ReMemberInfoPack> list = new ArrayList<>();
            for (NormalMember item : group.getMembers()) {
                list.add(makeMemberInfo(item, fast));
            }
            return list;
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member + Msg.get + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    private static ReMemberInfoPack makeMemberInfo(NormalMember item, boolean fast) {
        ReMemberInfoPack info = new ReMemberInfoPack();
        info.img = item.getAvatarUrl();
        info.nick = item.getNick();
        info.per = item.getPermission();
        info.nameCard = item.getNameCard();
        info.specialTitle = item.getSpecialTitle();
        info.avatarUrl = item.getAvatarUrl();
        info.muteTimeRemaining = item.getMuteTimeRemaining();
        info.joinTimestamp = item.getJoinTimestamp();
        info.lastSpeakTimestamp = item.getLastSpeakTimestamp();
        info.rankTitle = item.getRankTitle();
        if (info.rankTitle.isEmpty()) {
            info.rankTitle = item.getTemperatureTitle();
        }
        info.active = new MemberActiveInfo();
        if (!fast) {
            MemberActive active = item.getActive();
            info.active.rank = active.getRank();
            info.active.point = active.getPoint();
            info.active.honors = new HashSet<>();
            for (GroupHonorType item1 : active.getHonors()) {
                info.active.honors.add(item1.getId());
            }
            info.active.temperature = active.getTemperature();
            MemberMedalInfo info1 = active.queryMedal();
            info.active.title = info1.getTitle();
            info.active.color = info1.getColor();
            info.active.wearing = info1.getWearing();
            info.active.medals = info1.getMedals();
        } else {
            info.active.rank = 0;
            info.active.point = 0;
            info.active.honors = new HashSet<>();
            info.active.temperature = 0;
            info.active.title = "";
            info.active.color = "";
            info.active.wearing = MemberMedalType.ACTIVE;
            info.active.medals = new HashSet<>();
        }
        info.uuid = "";
        return info;
    }

    public static ReMemberInfoPack getMemberInfo(ThePlugin plugin, String uuid, long qq, long id, long fid) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            Group group = BotCheck.group(plugin, bot, id, uuid);
            if (group == null) return null;
            NormalMember item = BotCheck.member(plugin, bot, group, fid, uuid);
            if (item == null) return null;
            return makeMemberInfo(item, false);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.get + Msg.data + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    public static GroupSettings getGroupInfo(ThePlugin plugin, String uuid, long qq, long id) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            Group group = BotCheck.group(plugin, bot, id, uuid);
            if (group == null) return null;
            return group.getSettings();
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.get + Msg.data + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    public static List<FriendGroupInfo> getFriendGroups(ThePlugin plugin, String uuid, long qq) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            List<FriendGroupInfo> list = new ArrayList<>();
            for (FriendGroup item : bot.getFriendGroups().asCollection()) {
                list.add(makeFriendGroup(item));
            }
            return list;
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend_group + Msg.get + Msg.data + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    private static FriendGroupInfo makeFriendGroup(FriendGroup item) {
        FriendGroupInfo info = new FriendGroupInfo();
        info.id = item.getId();
        info.count = item.getCount();
        info.name = item.getName();
        info.friends = new ArrayList<>();
        for (Friend item1 : item.getFriends()) {
            info.friends.add(item1.getId());
        }
        return info;
    }

    public static FriendGroupInfo getFriendGroup(ThePlugin plugin, String uuid, long qq, int id) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            FriendGroup item = BotCheck.friendGroup(plugin, bot, id, uuid);
            if (item == null) return null;

            return makeFriendGroup(item);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend_group(id) + Msg.get + Msg.data + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, uuid, temp + "\r\n" + Utils.printError(e));
            return null;
        }
    }

    public static String getImg(ThePlugin plugin, String uuid, long qq, String img) {
        Bot bot = BotCheck.qq(plugin, uuid, qq);
        if (bot == null) return null;
        Image image = Mirai.getInstance().createImage(img);
        return Mirai.getInstance().queryImageUrl(bot, image);
    }
}
