package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.FileInputStream;

public class BotSendSound {
    public static void SendGroupSound(long qq, long id, String sound) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            ExternalResource voice = ExternalResource.create(ColorMiraiMain.decoder.decode(sound));
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            MessageReceipt<Group> message = group.sendMessage(group.uploadAudio(voice));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
            voice.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群语音失败", e);
        }
    }

    public static void SendGroupSoundFile(long qq, long id, String file) {
        try {
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
            FileInputStream stream = new FileInputStream(file);
            ExternalResource voice = ExternalResource.create(stream);
            MessageReceipt<Group> message = group.sendMessage(group.uploadAudio(voice));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
            voice.close();
            stream.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群语音失败", e);
        }
    }

    public static void SendFriendFile(long qq, long id, String file) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Friend friend = bot.getFriend(id);
            if (friend == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在好友:" + id);
                return;
            }
            FileInputStream stream = new FileInputStream(file);
            ExternalResource voice = ExternalResource.create(stream);
            MessageReceipt<Friend> message = friend.sendMessage(friend.uploadAudio(voice));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
            voice.close();
            stream.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送好友语音失败", e);
        }
    }
}
