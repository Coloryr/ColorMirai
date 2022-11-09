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
import net.mamoe.mirai.message.data.Image;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BotSendImage {

    public static void sendGroupImage(ThePlugin plugin, long qq, long id, byte[] img, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            Image image = null;
            for (long item : ids) {
                id = item;
                Group group = BotCheck.group(plugin, bot, id, "");
                if (group == null) continue;
                if (image == null) image = BotUpload.upImage(bot, img);
                group.sendMessage(image);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.image + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendGroupImageFile(ThePlugin plugin, long qq, long id, String file, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Image image = null;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                id = item;
                Group group = BotCheck.group(plugin, bot, id, "");
                if (group == null) continue;
                if (image == null) image = BotUpload.upImage(bot, file);
                group.sendMessage(image);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.image + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendGroupPrivateImage(ThePlugin plugin, long qq, long id, long fid, byte[] file, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(fid);
            ids.removeIf(Objects::isNull);
            Image image = null;
            for (long item : ids) {
                fid = item;
                NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
                if (member == null) continue;
                if (image == null) image = BotUpload.upImage(bot, file);
                member.sendMessage(image);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.image + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendGroupPrivateImageFile(ThePlugin plugin, long qq, long id, long fid, String file, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(fid);
            ids.removeIf(Objects::isNull);
            Image image = null;
            for (long item : ids) {
                fid = item;
                NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
                if (member == null) continue;
                if (image == null) image = BotUpload.upImage(bot, file);
                member.sendMessage(image);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.image + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendFriendImage(ThePlugin plugin, long qq, long id, byte[] img, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Image image = null;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                id = item;
                Friend friend = BotCheck.friend(plugin, bot, id, "");
                if (friend == null) continue;
                if (image == null) image = BotUpload.upImage(bot, img);
                friend.sendMessage(image);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(id) + Msg.image + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendFriendImageFile(ThePlugin plugin, long qq, long id, String img, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Image image = null;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                id = item;
                Friend friend = BotCheck.friend(plugin, bot, id, "");
                if (friend == null) continue;
                if (image == null) image = BotUpload.upImage(bot, img);
                friend.sendMessage(image);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(id) + Msg.image + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

//    public static void sendStrangerImage(long qq, long id, String img, List<Long> ids) {
//        if (!BotStart.getBots().containsKey(qq)) {
//            ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
//            return;
//        }
//        Bot bot = BotStart.getBots().get(qq);
//        Image image = null;
//        if (ids == null) {
//            ids = new ArrayList<>();
//        }
//        if (!ids.contains(id)) {
//            ids.add(id);
//        }
//        ids.removeIf(Objects::isNull);
//        try {
//            for (long item : ids) {
//                Stranger stranger = bot.getStranger(item);
//                if (stranger == null) {
//                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在陌生人:" + id);
//                    return;
//                }
//                if (image == null) {
//                    image = BotUpload.upImage(bot, img);
//                    if (image == null)
//                        throw new Exception("图片为空");
//                }
//
//                stranger.sendMessage(image);
//            }
//        } catch (Exception e) {
//            ColorMiraiMain.logger.error("发送陌生人失败", e);
//        }
//    }

    public static void sendStrangerImageFile(ThePlugin plugin, long qq, long id, String img, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Image image = null;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                id = item;
                Stranger friend = BotCheck.stranger(plugin, bot, id);
                if (friend == null) continue;
                if (image == null) image = BotUpload.upImage(bot, img);
                friend.sendMessage(image);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.stranger(id) + Msg.image + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
