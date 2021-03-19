package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.Pack.ReturnPlugin.FriendInfoPack;
import Color_yr.ColorMirai.Pack.ReturnPlugin.GroupInfo;
import Color_yr.ColorMirai.Pack.ReturnPlugin.MemberInfoPack;
import Color_yr.ColorMirai.Start;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.GroupSettings;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.Image;

import java.util.ArrayList;
import java.util.List;

public class BotGetData {
    public static List<GroupInfo> getGroups(long qq) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
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
            Start.logger.error("获取群数据失败", e);
            return null;
        }
    }

    public static FriendInfoPack getFriend(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            Friend item = bot.getFriend(id);
            if (item == null) {
                Start.logger.warn("QQ号:" + qq + "不存在朋友:" + id);
                return null;
            }
            FriendInfoPack info = new FriendInfoPack();
            info.id = item.getId();
            info.img = item.getAvatarUrl();
            info.remark = item.getRemark();
            info.userProfile = item.queryProfile();
            return info;
        } catch (Exception e) {
            Start.logger.error("获取朋友数据失败", e);
            return null;
        }
    }

    public static List<FriendInfoPack> getFriends(long qq) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            List<FriendInfoPack> list = new ArrayList<>();
            for (Friend item : bot.getFriends()) {
                FriendInfoPack info = new FriendInfoPack();
                info.id = item.getId();
                info.img = item.getAvatarUrl();
                info.remark = item.getRemark();
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            Start.logger.error("获取朋友数据失败", e);
            return null;
        }
    }

    public static List<MemberInfoPack> getMembers(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            if (bot.getGroups().contains(id)) {
                List<MemberInfoPack> list = new ArrayList<>();
                Group group1 = bot.getGroup(id);
                if (group1 == null) {
                    Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                for (NormalMember item : group1.getMembers()) {
                    MemberInfoPack info = new MemberInfoPack();
                    info.id = item.getId();
                    info.img = item.getAvatarUrl();
                    info.nick = item.getNick();
                    info.per = item.getPermission();
                    info.nameCard = item.getNameCard();
                    info.specialTitle = item.getSpecialTitle();
                    info.avatarUrl = item.getAvatarUrl();
                    info.muteTimeRemaining = item.getMuteTimeRemaining();
                    info.joinTimestamp = item.getJoinTimestamp();
                    info.lastSpeakTimestamp = item.getLastSpeakTimestamp();
                    list.add(info);
                }
                return list;
            } else {
                Start.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            Start.logger.error("获取群成员数据失败", e);
            return null;
        }
    }

    public static MemberInfoPack getMemberInfo(long qq, long id, long fid) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            if (bot.getGroups().contains(id)) {
                Group group1 = bot.getGroup(id);
                if (group1 == null) {
                    Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                NormalMember item = group1.get(fid);
                if (item == null) {
                    Start.logger.warn("机器人:" + qq + "群:" + id + "不存在成员：" + fid);
                    return null;
                }
                MemberInfoPack info = new MemberInfoPack();
                info.id = item.getId();
                info.img = item.getAvatarUrl();
                info.nick = item.getNick();
                info.per = item.getPermission();
                info.nameCard = item.getNameCard();
                info.specialTitle = item.getSpecialTitle();
                info.avatarUrl = item.getAvatarUrl();
                info.muteTimeRemaining = item.getMuteTimeRemaining();
                info.joinTimestamp = item.getJoinTimestamp();
                info.lastSpeakTimestamp = item.getLastSpeakTimestamp();
                return info;
            } else {
                Start.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            Start.logger.error("获取群成员数据失败", e);
            return null;
        }
    }

    public static GroupSettings getGroupInfo(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            if (bot.getGroups().contains(id)) {
                Group item = bot.getGroup(id);
                if (item == null) {
                    Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                return item.getSettings();
            } else {
                Start.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            Start.logger.error("获取群成员数据失败", e);
            return null;
        }
    }

    public static String GetImg(long qq, String uuid) {
        if (!BotStart.getBots().containsKey(qq)) {
            Start.logger.warn("不存在QQ号:" + qq);
            return null;
        }
        Bot bot = BotStart.getBots().get(qq);
        Image image = Mirai.getInstance().createImage(uuid);
        return Mirai.getInstance().queryImageUrl(bot, image);
    }
}
