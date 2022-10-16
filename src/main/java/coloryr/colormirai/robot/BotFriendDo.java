package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.friendgroup.FriendGroup;

public class BotFriendDo {
    public static void create(long qq, String name) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            bot.getFriendGroups().create(name);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("创建好友组失败", e);
        }
    }

    public static void rename(long qq, int id, String name) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            FriendGroup group = bot.getFriendGroups().get(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "不存在好友分组:" + id);
                return;
            }
            boolean res = group.renameTo(name);
            if (!res) {
                ColorMiraiMain.logger.warn("QQ号:" + qq + "好友分组:" + id + "重命名失败");
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("重命名好友组失败", e);
        }
    }

    public static void move(long qq, int id, long fid) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
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

    public static void delete(long qq, int id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
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
