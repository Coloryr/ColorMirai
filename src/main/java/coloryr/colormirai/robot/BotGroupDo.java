package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;
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
    public static void deleteGroupMember(ThePlugin plugin, long qq, long id, long fid, boolean black) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            member.kick("", black);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.kick + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void muteGroupMember(ThePlugin plugin, long qq, long id, long fid, int time) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            member.mute(time);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.mute + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void unmuteGroupMember(ThePlugin plugin, long qq, long id, long fid) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            member.unmute();
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.unmute + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void groupMuteAll(ThePlugin plugin, long qq, long id) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getSettings().setMuteAll(true);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.all + Msg.mute + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void groupUnmuteAll(ThePlugin plugin, long qq, long id) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.getSettings().setMuteAll(false);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.all + Msg.unmute + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void setGroupMemberCard(ThePlugin plugin, long qq, long id, long fid, String card) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            member.setNameCard(card);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.name_card + Msg.set + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void setGroupName(ThePlugin plugin, long qq, long id, String name) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            group.setName(name);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.name + Msg.set + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void setEssenceMessage(ThePlugin plugin, long qq, long id, int[] ids1, int[] ids2) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            MessageSource source = new MessageSourceBuilder()
                    .id(ids1)
                    .internalId(ids2)
                    .build(qq, MessageSourceKind.GROUP);
            boolean res = group.setEssenceMessage(source);
            if (!res) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.essence_message(ids1, ids2) + Msg.set + Msg.fail;
                plugin.sendPluginMessage(qq, "", temp);
                ColorMiraiMain.logger.warn(temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.essence_message(ids1, ids2) + Msg.set + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void setAdmin(ThePlugin plugin, long qq, long id, long fid, boolean set) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (group.getBotAsMember().getPermission() != MemberPermission.OWNER) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.set + Msg.admin + Msg.non_permission;
                plugin.sendPluginMessage(qq, "", temp);
                ColorMiraiMain.logger.warn(temp);
                return;
            }
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            member.modifyAdmin(set);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.admin + Msg.set + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static List<OnlineAnnouncement> getAnnouncements(ThePlugin plugin, long qq, long id, String uuid) {
        try {
            Bot bot = BotCheck.qq(plugin, uuid, qq);
            if (bot == null) return null;
            Group group = BotCheck.group(plugin, bot, id, uuid);
            if (group == null) return null;
            return group.getAnnouncements().toList();
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.announcement + Msg.get + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
        return null;
    }

    public static void setAnnouncement(ThePlugin plugin, long qq, long id, String image, boolean sendToNewMember, boolean isPinned, boolean showEditCard, boolean showPopup, boolean requireConfirmation, String text) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (group.getBotAsMember().getPermission() == MemberPermission.MEMBER) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.announcement + Msg.set + Msg.non_permission;
                plugin.sendPluginMessage(qq, "", temp);
                ColorMiraiMain.logger.warn(temp);
                return;
            }
            Announcements announcements = group.getAnnouncements();
            AnnouncementImage image1 = null;
            if (image != null && !image.isEmpty()) {
                File file = new File(image);
                if (!file.exists()) {
                    String temp = Msg.non_existent + Msg.image(image);
                    plugin.sendPluginMessage(qq, "", temp);
                    ColorMiraiMain.logger.warn(temp);
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
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.announcement + Msg.get + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void deleteAnnouncement(ThePlugin plugin, long qq, long id, String fid) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (group.getBotAsMember().getPermission() == MemberPermission.MEMBER) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.announcement(fid) + Msg.delete + Msg.non_permission;
                plugin.sendPluginMessage(qq, "", temp);
                ColorMiraiMain.logger.warn(temp);
                return;
            }
            Announcements announcements = group.getAnnouncements();
            List<OnlineAnnouncement> list = announcements.toList();
            for (OnlineAnnouncement item : list) {
                if (item.getFid().equalsIgnoreCase(fid)) {
                    boolean res = item.delete();
                    if (!res) {
                        String temp = Msg.qq(qq) + Msg.group(id) + Msg.announcement(fid) + Msg.delete + Msg.fail;
                        plugin.sendPluginMessage(qq, "", temp);
                        ColorMiraiMain.logger.warn(temp);
                    }
                    return;
                }
            }
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.announcement(fid) + Msg.non_existent;
            plugin.sendPluginMessage(qq, "", temp);
            ColorMiraiMain.logger.warn(temp);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.announcement(fid) + Msg.delete + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void setAnonymousChatEnabled(ThePlugin plugin, long qq, long id, boolean enable) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (group.getBotPermission() == MemberPermission.MEMBER) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.anonymous_chat + Msg.set + Msg.non_permission;
                plugin.sendPluginMessage(qq, "", temp);
                ColorMiraiMain.logger.warn(temp);
                return;
            }
            group.getSettings().setAnonymousChatEnabled(enable);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.anonymous_chat + Msg.set + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void setAllowMemberInvite(ThePlugin plugin, long qq, long id, boolean enable) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (group.getBotPermission() == MemberPermission.MEMBER) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.allow_member_invite + Msg.set + Msg.non_permission;
                plugin.sendPluginMessage(qq, "", temp);
                ColorMiraiMain.logger.warn(temp);
                return;
            }
            group.getSettings().setAllowMemberInvite(enable);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.allow_member_invite + Msg.set + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void setMemberSpecialTitle(ThePlugin plugin, long qq, long id, long fid, String name) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (group.getBotPermission() != MemberPermission.OWNER) {
                String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.special_title + Msg.set + Msg.non_permission;
                plugin.sendPluginMessage(qq, "", temp);
                ColorMiraiMain.logger.warn(temp);
                return;
            }
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            member.setSpecialTitle(name);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.special_title + Msg.set + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
