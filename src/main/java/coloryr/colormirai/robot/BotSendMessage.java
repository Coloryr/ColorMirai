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
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class BotSendMessage {

    public static void sendGroupMessage(ThePlugin plugin, long qq, long id, List<String> message, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                if (item.startsWith("quote:")) {
                    String temp = item.replaceFirst("quote:", "");
                    String[] args = temp.split(",");
                    int b = 0;
                    int a = Integer.parseInt(args[b++]);
                    int[] ids1 = new int[a];
                    for (int c = 0; c < a; c++) {
                        ids1[c] = Integer.parseInt(args[b++]);
                    }
                    int d = Integer.parseInt(args[b++]);
                    int[] ids2 = new int[d];
                    for (int c = 0; c < d; c++) {
                        ids2[c] = Integer.parseInt(args[b++]);
                    }
                    MessageKey key = new MessageKey(ids1, ids2);
                    MessageSource messageSource = BotStart.getMessage(qq, key);
                    QuoteReply reply = new QuoteReply(messageSource);
                    messageChain = messageChain.plus(reply);
                } else if (item.startsWith("[mirai:")) {
                    messageChain = messageChain.plus(MiraiCode.deserializeMiraiCode(item));
                } else {
                    messageChain = messageChain.plus(item);
                }
            }
            for (long item : ids) {
                id = item;
                Group group = BotCheck.group(plugin, bot, id, "");
                if (group == null) continue;

                group.sendMessage(messageChain).getSource();
            }

        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.message + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendGroupPrivateMessage(ThePlugin plugin, long qq, long id, long fid, List<String> message, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            for (long item : ids) {
                fid = item;
                NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
                if (member == null) continue;
                member.sendMessage(messageChain);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.message + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendFriendMessage(ThePlugin plugin, long qq, long fid, List<String> message, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(fid);
            ids.removeIf(Objects::isNull);
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            for (long item : ids) {
                fid = item;
                Friend friend = BotCheck.friend(plugin, bot, fid, "");
                if (friend == null) continue;
                friend.sendMessage(messageChain);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(fid) + Msg.message + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendStrangerMessage(ThePlugin plugin, long qq, long fid, List<String> message, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(fid);
            ids.removeIf(Objects::isNull);
            MessageChain messageChain = MessageUtils.newChain();
            for (String item : message) {
                messageChain = messageChain.plus(item);
            }
            for (long item : ids) {
                fid = item;
                Stranger stranger = BotCheck.stranger(plugin, bot, fid);
                if (stranger == null) continue;
                stranger.sendMessage(messageChain);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.stranger(fid) + Msg.message + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
