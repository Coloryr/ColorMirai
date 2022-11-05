package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.friendgroup.FriendGroup;

public class BotFriendGroup {
    public static void create(ThePlugin plugin, long qq, String name) {
        try {
            Bot bot = BotCheck.qq(plugin, qq);
            if (bot == null) return;
            bot.getFriendGroups().create(name);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend_group(name) + Msg.create + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void rename(ThePlugin plugin, long qq, int id, String name) {
        try {
            Bot bot = BotCheck.qq(plugin, qq);
            if (bot == null) return;
            FriendGroup group = BotCheck.friendGroup(plugin, bot, id);
            if (group == null) return;
            boolean res = group.renameTo(name);
            if (!res) {
                String temp = Msg.qq(qq) + Msg.friend_group(id) + Msg.rename + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend_group(id) + Msg.rename + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void move(ThePlugin plugin, long qq, int id, long fid) {
        try {
            Bot bot = BotCheck.qq(plugin, qq);
            if (bot == null) return;
            FriendGroup group = BotCheck.friendGroup(plugin, bot, id);
            if (group == null) return;
            Friend friend = bot.getFriend(fid);
            if (friend == null) {
                String temp = Msg.qq(qq) + Msg.non_existent + Msg.friend(fid);
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            boolean res = group.moveIn(friend);
            if (!res) {
                String temp = Msg.qq(qq) + Msg.friend_group(id) +
                        Msg.friend(fid) + Msg.move + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend_group(id) +
                    Msg.friend(fid) + Msg.move + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void delete(ThePlugin plugin, long qq, int id) {
        try {
            Bot bot = BotCheck.qq(plugin, qq);
            if (bot == null) return;
            FriendGroup group = BotCheck.friendGroup(plugin, bot, id);
            if (group == null) return;

            if (!group.delete()) {
                String temp = Msg.qq(qq) + Msg.friend_group(id) +
                        Msg.delete + Msg.fail;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend_group(id) +
                    Msg.delete + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
