package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.Start;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.*;

import java.util.List;

public class BotSendMessage {

    public static void sendGroupMessage(long qq, long group, List<String> message) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Group group1 = BotStart.getBots().get(qq).getGroup(group);
            if (group1 == null) {
                Start.logger.warn("机器人" + qq + "不存在群:" + group);
                return;
            }
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                if (item.startsWith("at:")) {
                    Member member = group1.get(Long.parseLong(item.replace("at:", "")));
                    if (member == null)
                        continue;
                    messageChain = messageChain.plus(new At(member.getId()));
                } else if (item.startsWith("quote:")) {
                    int id = Integer.parseInt(item.replace("quote:", ""));
                    MessageSaveObj call = BotStart.getMessage(id);
                    if (call == null)
                        continue;
                    if (call.source == null)
                        continue;
                    QuoteReply quote = new QuoteReply(call.source);
                    messageChain = messageChain.plus(quote);
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
                BotStart.addMessage(call.id, call);
            }
        } catch (Exception e) {
            Start.logger.error("发送群消息失败", e);
        }
    }

    public static void sendGroupPrivateMessage(long qq, long group, long fid, List<String> message) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Group group1 = BotStart.getBots().get(qq).getGroup(group);
            if (group1 == null) {
                Start.logger.warn("机器人" + qq + "不存在群:" + group);
                return;
            }
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            NormalMember member = group1.get(fid);
            if (member == null) {
                Start.logger.warn("群：" + group + "不存在群员:" + fid);
                return;
            }
            MessageSource source = member.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.source = source;
                call.time = 120;
                call.id = temp[0];
                BotStart.addMessage(call.id, call);
            }
        } catch (Exception e) {
            Start.logger.error("发送群私聊消息失败", e);
        }
    }

    public static void sendFriendMessage(long qq, long fid, List<String> message) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            Friend friend = bot.getFriend(fid);
            if (friend == null) {
                Start.logger.warn("机器人" + qq + "不存在朋友:" + fid);
                return;
            }
            MessageSource source = friend.sendMessage(messageChain).getSource();
            int[] temp = source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                MessageSaveObj call = new MessageSaveObj();
                call.source = source;
                call.time = 120;
                call.id = temp[0];
                BotStart.addMessage(call.id, call);
            }
        } catch (Exception e) {
            Start.logger.error("发送朋友消息失败", e);
        }
    }

}
