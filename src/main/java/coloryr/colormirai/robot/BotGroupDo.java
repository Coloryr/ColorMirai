package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.announcement.*;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.MessageSourceBuilder;
import net.mamoe.mirai.message.data.MessageSourceKind;

import java.io.File;
import java.util.List;

public class BotGroupDo {
    public static void deleteGroupMember(long qq, long id, long fid, boolean black) {
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

    public static void muteGroupMember(long qq, long id, long fid, int time) {
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

    public static void unmuteGroupMember(long qq, long id, long fid) {
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

    public static void groupMuteAll(long qq, long id) {
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

    public static void groupUnmuteAll(long qq, long id) {
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

    public static void setGroupMemberCard(long qq, long id, long fid, String card) {
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

    public static void setGroupName(long qq, long id, String name) {
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

    public static void setEssenceMessage(long qq, long id, int[] ids1, int[] ids2) {
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
            MessageSource source = new MessageSourceBuilder()
                    .id(ids1)
                    .internalId(ids2)
                    .build(qq, MessageSourceKind.GROUP);
            group.setEssenceMessage(source);
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
                image1 = announcements.uploadImage(BotUpload.up(file));
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

    public static void setAnonymousChatEnabled(long qq, long id, boolean enable) {
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
            group.getSettings().setAnonymousChatEnabled(enable);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("删除群公告失败", e);
        }
    }

    public static void setAllowMemberInvite(long qq, long id, boolean enable) {
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
            group.getSettings().setAllowMemberInvite(enable);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("删除群公告失败", e);
        }
    }

    public static void editSpecialTitle(long qq, long id, long fid, String name){
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
            if (group.getBotPermission() != MemberPermission.OWNER) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "在群:" + id + "不是群主");
                return;
            }
            NormalMember member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "在群:" + id + "中没有群员:" + fid);
                return;
            }
            member.setSpecialTitle(name);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("设置群头衔失败", e);
        }
    }
}
