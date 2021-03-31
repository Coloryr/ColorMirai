package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;

public class BotSendNudge {
    public static void SendNudge(long qq, long id) {
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

    public static void SendNudge(long qq, long id, long fid) {
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
