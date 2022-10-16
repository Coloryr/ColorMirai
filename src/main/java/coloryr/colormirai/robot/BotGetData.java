package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
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
    public static List<GroupInfo> getGroups(long qq) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
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
            ColorMiraiMain.logger.error("获取群数据失败", e);
            return null;
        }
    }

    public static ReFriendInfoPack getFriend(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            Friend item = bot.getFriend(id);
            if (item == null) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "不存在朋友:" + id);
                return null;
            }
            FriendGroup group1 = item.getFriendGroup();
            ReFriendInfoPack info = new ReFriendInfoPack();
            info.groupId = group1.getId();
            info.id = item.getId();
            info.img = item.getAvatarUrl();
            info.remark = item.getRemark();
            info.userProfile = item.queryProfile();
            return info;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取朋友数据失败", e);
            return null;
        }
    }

    public static List<ReFriendInfoPack> getFriends(long qq) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            List<ReFriendInfoPack> list = new ArrayList<>();
            for (Friend item : bot.getFriends()) {
                ReFriendInfoPack info = new ReFriendInfoPack();
                info.userProfile = item.queryProfile();
                info.id = item.getId();
                info.img = item.getAvatarUrl();
                info.remark = item.getRemark();
                info.uuid = "";
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取朋友数据失败", e);
            return null;
        }
    }

    public static List<ReMemberInfoPack> getMembers(long qq, long id, boolean fast) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            if (bot.getGroups().contains(id)) {
                List<ReMemberInfoPack> list = new ArrayList<>();
                Group group1 = bot.getGroup(id);
                if (group1 == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                for (NormalMember item : group1.getMembers()) {
                    list.add(makeMemberInfo(item, fast));
                }
                return list;
            } else {
                ColorMiraiMain.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取群成员数据失败", e);
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

    public static ReMemberInfoPack getMemberInfo(long qq, long id, long fid) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            if (bot.getGroups().contains(id)) {
                Group group1 = bot.getGroup(id);
                if (group1 == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                NormalMember item = group1.get(fid);
                if (item == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "群:" + id + "不存在成员：" + fid);
                    return null;
                }

                return makeMemberInfo(item, false);
            } else {
                ColorMiraiMain.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取群成员数据失败", e);
            return null;
        }
    }

    public static GroupSettings getGroupInfo(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            if (bot.getGroups().contains(id)) {
                Group item = bot.getGroup(id);
                if (item == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                    return null;
                }
                return item.getSettings();
            } else {
                ColorMiraiMain.logger.warn("不存在群:" + id);
                return null;
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取群成员数据失败", e);
            return null;
        }
    }

    public static List<FriendGroupInfo> getFriendGroups(long qq) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            List<FriendGroupInfo> list = new ArrayList<>();
            for (FriendGroup item : bot.getFriendGroups().asCollection()) {
                FriendGroupInfo info = new FriendGroupInfo();
                info.id = item.getId();
                info.count = item.getCount();
                info.name = item.getName();
                info.friends = new ArrayList<>();
                for (Friend item1 : item.getFriends()) {
                    info.friends.add(item1.getId());
                }
                list.add(info);
            }
            return list;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取好友分组数据失败", e);
            return null;
        }
    }

    public static FriendGroupInfo getFriendGroup(long qq, int id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            FriendGroup item = bot.getFriendGroups().get(id);
            if (item == null) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "不存在好友分组:" + id);
                return null;
            }
            FriendGroupInfo info = new FriendGroupInfo();
            info.id = item.getId();
            info.count = item.getCount();
            info.name = item.getName();
            info.friends = new ArrayList<>();
            for (Friend item1 : item.getFriends()) {
                info.friends.add(item1.getId());
            }

            return info;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取好友分组数据失败", e);
            return null;
        }
    }

    public static String getImg(long qq, String uuid) {
        if (!BotStart.getBots().containsKey(qq)) {
            ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
            return null;
        }
        Bot bot = BotStart.getBots().get(qq);
        Image image = Mirai.getInstance().createImage(uuid);
        return Mirai.getInstance().queryImageUrl(bot, image);
    }
}
