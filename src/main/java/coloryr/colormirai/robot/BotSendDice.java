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
import net.mamoe.mirai.message.data.Dice;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;

public class BotSendDice {
    public static void sendGroupDice(ThePlugin plugin, long qq, long id, int dice) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            group.sendMessage(messageChain);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.dice(dice) + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendGroupPrivateDice(ThePlugin plugin, long qq, long id, long fid, int dice) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
            if (member == null) return;
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            member.sendMessage(messageChain);
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.dice(dice) + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendFriendDice(ThePlugin plugin, long qq, long fid, int dice) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Friend friend = BotCheck.friend(plugin, bot, fid, "");
            if (friend == null) return;
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            friend.sendMessage(messageChain).getSource();
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(fid) + Msg.dice(dice) + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendStrangerDice(ThePlugin plugin, long qq, long fid, int dice) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Stranger stranger = BotCheck.stranger(plugin, bot, fid);
            if (stranger == null) return;
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            stranger.sendMessage(messageChain).getSource();
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.stranger(fid) + Msg.dice(dice) + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
