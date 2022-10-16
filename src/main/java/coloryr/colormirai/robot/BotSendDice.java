package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.message.data.Dice;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;

public class BotSendDice {
    public static void sendGroupDice(long qq, long group, int dice) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Group group1 = BotStart.getBots().get(qq).getGroup(group);
            if (group1 == null) {
                ColorMiraiMain.logger.warn("机器人" + qq + "不存在群:" + group);
                return;
            }
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            group1.sendMessage(messageChain).getSource();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群骰子失败", e);
        }
    }

    public static void sendGroupPrivateDice(long qq, long group, long fid, int dice) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Group group1 = BotStart.getBots().get(qq).getGroup(group);
            if (group1 == null) {
                ColorMiraiMain.logger.warn("机器人" + qq + "不存在群:" + group);
                return;
            }
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            NormalMember member = group1.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群：" + group + "不存在群员:" + fid);
                return;
            }
            member.sendMessage(messageChain).getSource();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群私聊骰子失败", e);
        }
    }

    public static void sendFriendDice(long qq, long fid, int dice) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            Friend friend = bot.getFriend(fid);
            if (friend == null) {
                ColorMiraiMain.logger.warn("机器人" + qq + "不存在朋友:" + fid);
                return;
            }
            friend.sendMessage(messageChain).getSource();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送朋友骰子失败", e);
        }
    }

    public static void sendStrangerDice(long qq, long fid, int dice) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            MessageChain messageChain = MessageUtils.newChain(dice == -1 ? Dice.random() : new Dice(dice));
            Stranger stranger = bot.getStranger(fid);
            if (stranger == null) {
                ColorMiraiMain.logger.warn("机器人" + qq + "不存在陌生人:" + fid);
                return;
            }
            stranger.sendMessage(messageChain).getSource();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送陌生人骰子失败", e);
        }
    }
}
