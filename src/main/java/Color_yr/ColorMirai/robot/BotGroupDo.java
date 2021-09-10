package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.announcement.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.util.List;

public class BotGroupDo {
    public static void DeleteGroupMember(long qq, long id, long fid, boolean black) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.kick("", black);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("踢出成员失败", e);
        }
    }

    public static void MuteGroupMember(long qq, long id, long fid, int time) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.mute(time);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("禁言成员失败", e);
        }
    }

    public static void UnmuteGroupMember(long qq, long id, long fid) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.unmute();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("解禁成员失败", e);
        }
    }

    public static void GroupMuteAll(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            group.getSettings().setMuteAll(true);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("全群禁言失败", e);
        }
    }

    public static void GroupUnmuteAll(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            group.getSettings().setMuteAll(false);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("全群解禁失败", e);
        }
    }

    public static void SetGroupMemberCard(long qq, long id, long fid, String card) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            member.setNameCard(card);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("修改群员名片失败", e);
        }
    }

    public static void SetGroupName(long qq, long id, String name) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            group.setName(name);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("设置群名失败", e);
        }
    }

    public static void setEssenceMessage(long qq, long id, int mid) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            MessageSaveObj obj = BotStart.getMessage(qq, mid);
            if (obj != null)
                group.setEssenceMessage(obj.source);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("设置群精华消息失败", e);
        }
    }

    public static void setAdmin(long qq, long id, long fid, boolean set) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            NormalMember member = group.getBotAsMember();
            if (member.getPermission() != MemberPermission.OWNER) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "在群:" + id + "不是群主");
                return;
            }
            member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "在群:" + id + "中没有群员:" + fid);
                return;
            }
            member.modifyAdmin(set);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("设置群精华消息失败", e);
        }
    }

    public static List<OnlineAnnouncement> getAnnouncements(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return null;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return null;
            }
            return group.getAnnouncements().toList();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取群公告失败", e);
        }
        return null;
    }

    public static void setAnnouncement(long qq, long id, String image, boolean sendToNewMember, boolean isPinned, boolean showEditCard, boolean showPopup, boolean requireConfirmation, String text) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            Announcements announcements = group.getAnnouncements();
            AnnouncementImage image1 = null;
            if (image != null && !image.isEmpty()) {
                File file = new File(image);
                if (!file.exists()) {
                    ColorMiraiMain.logger.warn("不存在图片:" + image);
                    return;
                }
                ExternalResource resource = ExternalResource.create(file);
                image1 = announcements.uploadImage(resource);
                resource.close();
            }
            AnnouncementParametersBuilder builder = new AnnouncementParametersBuilder();
            if (image1 != null) {
                builder = builder.image(image1);
            }
            builder = builder.isPinned(isPinned)
                    .sendToNewMember(sendToNewMember)
                    .showEditCard(showEditCard)
                    .showPopup(showPopup)
                    .requireConfirmation(requireConfirmation);
            OfflineAnnouncement announcement = OfflineAnnouncement.create(text, builder.build());
            announcements.publish(announcement);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("设置群公告失败", e);
        }
    }

    public static void deleteAnnouncement(long qq, long id, String fid) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            if (group.getBotPermission() == MemberPermission.MEMBER) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "在群:" + id + "权限不足");
                return;
            }
            Announcements announcements = group.getAnnouncements();
            List<OnlineAnnouncement> list = announcements.toList();
            for (OnlineAnnouncement item : list) {
                if (item.getFid().equalsIgnoreCase(fid)) {
                    item.delete();
                    return;
                }
            }
            ColorMiraiMain.logger.warn("机器人:" + qq + "群:" + id + "不存在公告:" + fid);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("删除群公告失败", e);
        }
    }
}
