package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BotSendMusicShare {
    public static void sendFriendMusicShare(ThePlugin plugin, long qq, long id, int type, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl, Set<Long> ids) {
        try {
            MusicKind kind;
            if (type == 1) {
                kind = MusicKind.NeteaseCloudMusic;
            } else if (type == 2) {
                kind = MusicKind.QQMusic;
            } else if (type == 3) {
                kind = MusicKind.MiguMusic;
            } else if (type == 4) {
                kind = MusicKind.KugouMusic;
            } else if (type == 5) {
                kind = MusicKind.KuwoMusic;
            } else {
                String temp = Msg.qq(qq) + Msg.music + Msg.type_error;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            MusicShare music = new MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl);
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                id = item;
                Friend friend = BotCheck.friend(plugin, bot, id, "");
                if (friend == null) continue;
                friend.sendMessage(music);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.friend(id) + Msg.music + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

//    public static void sendStrangerMusicShare(ThePlugin plugin, long qq, long id, int type, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl, Set<Long> ids ) {
//        MusicKind kind;
//        if (type == 1) {
//            kind = MusicKind.NeteaseCloudMusic;
//        } else if (type == 2) {
//            kind = MusicKind.QQMusic;
//        } else if (type == 3) {
//            kind = MusicKind.MiguMusic;
//        } else if (type == 4) {
//            kind = MusicKind.KugouMusic;
//        } else if (type == 5) {
//            kind = MusicKind.KuwoMusic;
//        } else {
//            ColorMiraiMain.logger.warn("不存在音乐类型:" + type);
//            return;
//        }
//        MusicShare music = new MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl);
//        if (!BotStart.getBots().containsKey(qq)) {
//            ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
//            return;
//        }
//        Bot bot = BotStart.getBots().get(qq);
//        Stranger stranger = bot.getStranger(id);
//        if (stranger == null) {
//            ColorMiraiMain.logger.warn("机器人:" + qq + "不存在陌生人:" + id);
//            return;
//        }
//        stranger.sendMessage(music);
//    }

    public static void sendMusicShareGroup(ThePlugin plugin, long qq, long id, int type, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl, Set<Long> ids) {
        try {
            MusicKind kind;
            if (type == 1) {
                kind = MusicKind.NeteaseCloudMusic;
            } else if (type == 2) {
                kind = MusicKind.QQMusic;
            } else if (type == 3) {
                kind = MusicKind.MiguMusic;
            } else if (type == 4) {
                kind = MusicKind.KugouMusic;
            } else if (type == 5) {
                kind = MusicKind.KuwoMusic;
            } else {
                String temp = Msg.qq(qq) + Msg.music + Msg.type_error;
                ColorMiraiMain.logger.warn(temp);
                plugin.sendPluginMessage(qq, "", temp);
                return;
            }
            MusicShare music = new MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl);
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                id = item;
                Group group = BotCheck.group(plugin, bot, id, "");
                if (group == null) continue;
                group.sendMessage(music);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.music + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }

    public static void sendMusicShareMember(ThePlugin plugin, long qq, long id, long fid, int type, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl, Set<Long> ids) {
        try {
            MusicKind kind;
            if (type == 1) {
                kind = MusicKind.NeteaseCloudMusic;
            } else if (type == 2) {
                kind = MusicKind.QQMusic;
            } else if (type == 3) {
                kind = MusicKind.MiguMusic;
            } else {
                ColorMiraiMain.logger.warn("不存在音乐类型:" + type);
                return;
            }
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotCheck.qq(plugin, "", qq);
            if (bot == null) return;
            Group group = BotCheck.group(plugin, bot, id, "");
            if (group == null) return;
            MusicShare music = new MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl);
            if (ids == null) {
                ids = new HashSet<>();
            }
            ids.add(id);
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                fid = item;
                NormalMember member = BotCheck.member(plugin, bot, group, fid, "");
                if (member == null) continue;
                member.sendMessage(music);
            }
        } catch (Exception e) {
            String temp = Msg.qq(qq) + Msg.group(id) + Msg.member(fid) + Msg.music + Msg.send + Msg.fail;
            ColorMiraiMain.logger.error(temp, e);
            plugin.sendPluginMessage(qq, "", temp + "\r\n" + Utils.printError(e));
        }
    }
}
