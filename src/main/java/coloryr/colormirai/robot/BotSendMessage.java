package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.*;

import java.util.List;

public class BotSendMessage {

    public static void sendGroupMessage(long qq, long group, List<String> message) {
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
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                if (item.startsWith("[mirai:")) {
                    messageChain = messageChain.plus(MiraiCode.deserializeMiraiCode(item));
                } else {
                    messageChain = messageChain.plus(item);
                }
            }
            MessageSource source = group1.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.sourceQQ = qq;
                call.source = source;
                call.time = 120;
                call.id = source.getIds()[0];
                BotStart.addMessage(qq, call.id, call);
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群消息失败", e);
        }
    }

    public static void sendGroupPrivateMessage(long qq, long group, long fid, List<String> message) {
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
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            NormalMember member = group1.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群：" + group + "不存在群员:" + fid);
                return;
            }
            MessageSource source = member.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.source = source;
                call.time = 120;
                call.id = temp[0];
                BotStart.addMessage(qq, call.id, call);
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群私聊消息失败", e);
        }
    }

    public static void sendFriendMessage(long qq, long fid, List<String> message) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            Friend friend = bot.getFriend(fid);
            if (friend == null) {
                ColorMiraiMain.logger.warn("机器人" + qq + "不存在朋友:" + fid);
                return;
            }
            MessageSource source = friend.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.source = source;
                call.time = 120;
                call.id = temp[0];
                BotStart.addMessage(qq, call.id, call);
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送朋友消息失败", e);
        }
    }

}
