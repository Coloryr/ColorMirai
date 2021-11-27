package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Audio;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class BotSendSound {
    public static void sendGroupSound(long qq, long id, String sound) {
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

    public static void sendGroupSoundFile(long qq, long id, String file, List<Long> ids) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Audio audio = null;
            if (ids == null) {
                ids = new ArrayList<>();
            }
            if (id != 0) {
                ids.add(id);
            }
            for (long item : ids) {
                Group group = bot.getGroup(item);
                if (group == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                    continue;
                }
                if (audio == null) {
                    audio = BotUpload.upAudio(bot, file);
                }
                MessageReceipt<Group> message = group.sendMessage(audio);
                MessageSaveObj obj = new MessageSaveObj();
                obj.source = message.getSource();
                obj.sourceQQ = qq;
                int[] temp = obj.source.getIds();
                if (temp.length != 0 && temp[0] != -1) {
                    obj.id = temp[0];
                }
                BotStart.addMessage(qq, obj.id, obj);
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群语音失败", e);
        }
    }

    public static void sendFriendFile(long qq, long id, String file, List<Long> ids) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Audio audio = null;
            if (ids == null) {
                ids = new ArrayList<>();
            }
            if (id != 0) {
                ids.add(id);
            }
            for (long item : ids) {
                Friend friend = bot.getFriend(item);
                if (friend == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在好友:" + id);
                    continue;
                }
                if (audio == null) {
                    audio = BotUpload.upAudio(bot, file);
                }

                MessageReceipt message = friend.sendMessage(audio);
                MessageSaveObj obj = new MessageSaveObj();
                obj.source = message.getSource();
                obj.sourceQQ = qq;
                int[] temp = obj.source.getIds();
                if (temp.length != 0 && temp[0] != -1) {
                    obj.id = temp[0];
                }
                BotStart.addMessage(qq, obj.id, obj);
            }
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送好友语音失败", e);
        }
    }
}
