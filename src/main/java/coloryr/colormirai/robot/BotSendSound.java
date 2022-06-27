package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Audio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BotSendSound {
    public static void sendGroupSound(long qq, long id, byte[] sound, List<Long> ids) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            if (ids == null) {
                ids = new ArrayList<>();
            }
            if (!ids.contains(id)) {
                ids.add(id);
            }
            ids.removeIf(Objects::isNull);
            Audio audio = BotUpload.upAudio(bot, sound);
            for (long item : ids) {
                Group group = bot.getGroup(item);
                if (group == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + item);
                    return;
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
            if (!ids.contains(id)) {
                ids.add(id);
            }
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                Group group = bot.getGroup(item);
                if (group == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
                    continue;
                }
                if (audio == null) {
                    audio = BotUpload.upAudio(bot, file);
                    if (audio == null)
                        throw new Exception("声音为空");
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
            if (!ids.contains(id)) {
                ids.add(id);
            }
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                Friend friend = bot.getFriend(item);
                if (friend == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在好友:" + id);
                    continue;
                }
                if (audio == null) {
                    audio = BotUpload.upAudio(bot, file);
                    if (audio == null)
                        throw new Exception("声音为空");
                }

                MessageReceipt<Friend> message = friend.sendMessage(audio);
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

    public static void sendStrangerFile(long qq, long id, String file, List<Long> ids) {
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
            if (!ids.contains(id)) {
                ids.add(id);
            }
            ids.removeIf(Objects::isNull);
            for (long item : ids) {
                Stranger stranger = bot.getStranger(item);
                if (stranger == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在陌生人:" + id);
                    continue;
                }
                if (audio == null) {
                    audio = BotUpload.upAudio(bot, file);
                    if (audio == null)
                        throw new Exception("声音为空");
                }

                MessageReceipt<Stranger> message = stranger.sendMessage(audio);
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
            ColorMiraiMain.logger.error("发送陌生人语音失败", e);
        }
    }

    public static void sendFriend(long qq, long id, byte[] data, List<Long> ids) {

    }
}
