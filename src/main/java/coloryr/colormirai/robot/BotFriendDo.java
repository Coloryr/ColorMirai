package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.friendgroup.FriendGroup;

public class BotFriendDo {
    public static void create(ThePlugin plugin, long qq, String name) {
        try {
            if (!BotCheck.qq(plugin, qq))
                return;
            Bot bot = BotStart.getBots().get(qq);
            bot.getFriendGroups().create(name);
        } catch (Exception e) {
            String temp = "QQ号:" + qq + "创建好友组" + name + "失败";
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void rename(ThePlugin plugin, long qq, int id, String name) {
        try {
            if (!BotCheck.qq(plugin, qq))
                return;
            Bot bot = BotStart.getBots().get(qq);
            FriendGroup group = bot.getFriendGroups().get(id);
            if (group == null) {
                String temp = "QQ号:" + qq + "不存在好友分组:" + id;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            boolean res = group.renameTo(name);
            if (!res) {
                String temp = "QQ号:" + qq + "好友分组:" + id + "重命名失败";
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
            }
        } catch (Exception e) {
            String temp = "QQ号:" + qq + "好友分组:" + id + "重命名失败";
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void move(ThePlugin plugin, long qq, int id, long fid) {
        try {
            if (!BotCheck.qq(plugin, qq))
                return;
            Bot bot = BotStart.getBots().get(qq);
            FriendGroup group = bot.getFriendGroups().get(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "不存在好友分组:" + id);
                return;
            }
            Friend friend = bot.getFriend(fid);
            if (friend == null) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "不存在好友:" + id);
                return;
            }
            boolean res = group.moveIn(friend);
            if (!res) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "好友分组:" + id + "移动好友:" + fid + "失败");
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("移动好友失败", e);
        }
    }

    public static void delete(ThePlugin plugin, long qq, int id) {
        try {
            if (!BotCheck.qq(plugin, qq))
                return;
            Bot bot = BotStart.getBots().get(qq);
            FriendGroup group = bot.getFriendGroups().get(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "不存在好友分组:" + id);
                return;
            }

            if (!group.delete()) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "删除好友分组" + id + "失败");
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("删除好友组失败", e);
        }
    }
}
