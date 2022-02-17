package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.Stranger;

public class BotSendNudge {
    public static void sendFriendNudge(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Friend friend = bot.getFriend(id);
            if (friend == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在朋友:" + id);
                return;
            }
            friend.nudge().sendTo(friend);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送好友戳一戳失败", e);
        }
    }

    public static void sendStrangerNudge(long qq, long id) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Stranger stranger = bot.getStranger(id);
            if (stranger == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在陌生人:" + id);
                return;
            }
            stranger.nudge().sendTo(stranger);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送陌生人戳一戳失败", e);
        }
    }

    public static void sendGroupNudge(long qq, long id, long fid) {
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
            member.nudge().sendTo(group);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群成员戳一戳失败", e);
        }
    }
}
