package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.Stranger;

public class BotSendNudge {
    public static void sendFriendNudge(ThePlugin plugin, long qq, long id) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Friend friend = BotCheck.friend(plugin, bot, id, "");
            if (friend == null) return;
            friend.nudge().sendTo(friend);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(id) + Msg.nudge + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendStrangerNudge(ThePlugin plugin, long qq, long id) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Stranger stranger = BotCheck.stranger(plugin, bot, id);
            if (stranger == null) return;
            stranger.nudge().sendTo(stranger);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.stranger(id) + Msg.nudge + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendGroupNudge(ThePlugin plugin, long qq, long id, long fid) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            member.nudge().sendTo(group);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.nudge + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
