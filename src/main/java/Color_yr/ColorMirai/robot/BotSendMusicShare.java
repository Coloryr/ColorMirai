package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;

public class BotSendMusicShare {
    public static void SendMusicShare(long qq, long id, int type, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl) {
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
            ColorMiraiMain.logger.warn("不存在音乐类型:" + type);
            return;
        }
        MusicShare music = new MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl);
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
        friend.sendMessage(music);
    }

    public static void SendMusicShareGroup(long qq, long id, int type, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl) {
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
            ColorMiraiMain.logger.warn("不存在音乐类型:" + type);
            return;
        }
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
        MusicShare music = new MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl);
        group.sendMessage(music);
    }

    public static void SendMusicShareMember(long qq, long id, long fid, int type, String title, String summary, String jumpUrl, String pictureUrl, String musicUrl) {
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
        MusicShare music = new MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl);
        member.sendMessage(music);
    }
}
