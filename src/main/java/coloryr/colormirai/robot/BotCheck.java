package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.plugin.ThePlugin;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFolder;
import net.mamoe.mirai.contact.friendgroup.FriendGroup;

public class BotCheck {
    public static Bot qq(ThePlugin plugin, long qq) {
        return qq(plugin, "", qq);
    }

    public static Bot qq(ThePlugin plugin, String uuid, long qq) {
        Bot bot = BotStart.getBots().get(qq);
        if (bot == null) {
            String temp = Msg.qq(qq) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(qq, uuid, temp);
            return null;
        }

        return bot;
    }

    public static FriendGroup friendGroup(ThePlugin plugin, Bot bot, int id) {
        return friendGroup(plugin, bot, id, "");
    }

    public static FriendGroup friendGroup(ThePlugin plugin, Bot bot, int id, String uuid) {
        FriendGroup group = bot.getFriendGroups().get(id);
        if (group == null) {
            String temp = Msg.qq(bot.getId()) + Msg.friend_group(id) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(bot.getId(), uuid, temp);
            return null;
        }

        return group;
    }

    public static Friend friend(ThePlugin plugin, Bot bot, long id, String uuid) {
        Friend friend = bot.getFriend(id);
        if (friend == null) {
            String temp = Msg.qq(bot.getId()) + Msg.friend(id) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(bot.getId(), uuid, temp);
            return null;
        }
        return friend;
    }

    public static Group group(ThePlugin plugin, Bot bot, long id, String uuid) {
        Group group = bot.getGroup(id);
        if (group == null) {
            String temp = Msg.qq(bot.getId()) + Msg.group(id) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(bot.getId(), uuid, temp);
            return null;
        }
        return group;
    }

    public static NormalMember member(ThePlugin plugin, Bot bot, Group group, long id, String uuid) {
        NormalMember member = group.get(id);
        if (member == null) {
            String temp = Msg.qq(bot.getId()) + Msg.group(group.getId()) + Msg.member(id) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(bot.getId(), uuid, temp);
            return null;
        }
        return member;
    }

    public static AbsoluteFile file(ThePlugin plugin, Bot bot, Group group, String id1) {
        AbsoluteFile file = group.getFiles().getRoot().resolveFileById(id1);
        if (file == null) {
            String temp = Msg.qq(bot.getId()) + Msg.group(group.getId()) + Msg.file(id1) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(bot.getId(), "", temp);
            return null;
        }

        return file;
    }

    public static AbsoluteFolder folder(ThePlugin plugin, Bot bot, Group group, String dir) {
        AbsoluteFolder remoteFile = group.getFiles().getRoot().resolveFolder(dir);
        if (remoteFile == null) {
            String temp = Msg.qq(bot.getId()) + Msg.group(group.getId()) + Msg.dir(dir) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(bot.getId(), "", temp);
            return null;
        }

        return remoteFile;
    }

    public static Stranger stranger(ThePlugin plugin, Bot bot, long id) {
        Stranger stranger = bot.getStranger(id);
        if (stranger == null) {
            String temp = Msg.qq(bot.getId()) + Msg.stranger(id) + Msg.non_existent;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(bot.getId(), "", temp);
            return null;
        }

        return stranger;
    }
}
