package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Audio;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BotSendSound {
    public static void sendGroupSound(ThePlugin plugin, long qq, long id, byte[] sound, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            Audio audio = null;
            for (long item : ids) {
                id = item;
                Group group = BotCheck.group(plugin, bot, id, "");
                if (group == null) continue;
                if (audio == null) audio = BotUpload.upAudio(bot, sound);
                group.sendMessage(audio);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.sound + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendGroupSoundFile(ThePlugin plugin, long qq, long id, String file, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            Audio audio = null;
            for (long item : ids) {
                id = item;
                Group group = BotCheck.group(plugin, bot, id, "");
                if (group == null) continue;
                if (audio == null) audio = BotUpload.upAudio(bot, file);
                group.sendMessage(audio);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.sound + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendFriendFile(ThePlugin plugin, long qq, long id, String file, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            Audio audio = null;
            for (long item : ids) {
                id = item;
                Friend friend = BotCheck.friend(plugin, bot, id, "");
                if (friend == null) continue;
                if (audio == null) audio = BotUpload.upAudio(bot, file);
                friend.sendMessage(audio);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(id) + Msg.sound + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendStrangerFile(ThePlugin plugin, long qq, long id, String file, Set<Long> ids) {
        try {
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            Audio audio = null;
            for (long item : ids) {
                id = item;
                Friend friend = BotCheck.friend(plugin, bot, id, "");
                if (friend == null) continue;
                if (audio == null) audio = BotUpload.upAudio(bot, file);
                friend.sendMessage(audio);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(id) + Msg.sound + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendFriend(long qq, long id, byte[] data, Set<Long> ids) {

    }
}
