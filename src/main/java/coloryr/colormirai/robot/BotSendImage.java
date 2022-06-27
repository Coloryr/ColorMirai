package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.Stranger;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BotSendImage {

    public static void sendGroupImage(long qq, long id, byte[] img, List<Long> ids) {
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
            Image image = BotUpload.upImage(bot, img);
            for (long item : ids) {
                Group group = bot.getGroup(item);
                if (group == null) {
                    ColorMiraiMain.logger.error("没有群：" + item);
                    return;
                }
                MessageReceipt<Group> message = group.sendMessage(image);
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
            ColorMiraiMain.logger.error("发送群图片失败", e);
        }
    }

    public static void sendGroupImageFile(long qq, long id, String file, List<Long> ids) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Image image = null;
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
                if (image == null) {
                    image = BotUpload.upImage(bot, file);
                    if (image == null)
                        throw new Exception();
                }

                MessageReceipt<Group> message = group.sendMessage(image);
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
            ColorMiraiMain.logger.error("发送群图片失败", e);
        }
    }

    public static void sendGroupPrivateImage(long qq, long id, long fid, byte[] file) {
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
            Member member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            Image image = BotUpload.upImage(bot, file);

            MessageReceipt<Member> message = member.sendMessage(image);
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送私聊图片失败", e);
        }
    }

    public static void sendGroupPrivateImageFile(long qq, long id, long fid, String file) {
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
            Member member = group.get(fid);
            if (member == null) {
                ColorMiraiMain.logger.warn("群:" + id + "不存在群成员:" + fid);
                return;
            }
            Image image = BotUpload.upImage(bot, file);
            if (image == null)
                throw new Exception("图片为空");

            MessageReceipt<Member> message = member.sendMessage(image);
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送私聊图片失败", e);
        }
    }

    public static void sendFriendImage(long qq, long id, byte[] img, List<Long> ids) {
        if (!BotStart.getBots().containsKey(qq)) {
            ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
            return;
        }
        Bot bot = BotStart.getBots().get(qq);
        Image image = null;
        if (ids == null) {
            ids = new ArrayList<>();
        }
        if (!ids.contains(id)) {
            ids.add(id);
        }
        ids.removeIf(Objects::isNull);
        try {
            for (long item : ids) {
                Friend friend = bot.getFriend(item);
                if (friend == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在朋友:" + id);
                    return;
                }
                if (image == null) {
                    image = BotUpload.upImage(bot, img);
                }
                MessageReceipt<Friend> message = friend.sendMessage(image);
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
            ColorMiraiMain.logger.error("发送朋友失败", e);
        }
    }

    public static void sendFriendImageFile(long qq, long id, String file, List<Long> ids) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Image image = null;
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
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在朋友:" + item);
                    return;
                }
                if (image == null) {
                    image = BotUpload.upImage(bot, file);
                    if (image == null)
                        throw new Exception("图片为空");
                }

                MessageReceipt<Friend> message = friend.sendMessage(image);
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
            ColorMiraiMain.logger.error("发送朋友失败", e);
        }
    }

    public static void sendStrangerImage(long qq, long id, String img, List<Long> ids) {
        if (!BotStart.getBots().containsKey(qq)) {
            ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
            return;
        }
        Bot bot = BotStart.getBots().get(qq);
        Image image = null;
        if (ids == null) {
            ids = new ArrayList<>();
        }
        if (!ids.contains(id)) {
            ids.add(id);
        }
        ids.removeIf(Objects::isNull);
        try {
            for (long item : ids) {
                Stranger stranger = bot.getStranger(item);
                if (stranger == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在陌生人:" + id);
                    return;
                }
                if (image == null) {
                    image = BotUpload.upImage(bot, img);
                    if (image == null)
                        throw new Exception("图片为空");
                }
                MessageReceipt<Stranger> message = stranger.sendMessage(image);
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
            ColorMiraiMain.logger.error("发送陌生人失败", e);
        }
    }

    public static void sendStrangerImageFile(long qq, long id, String file, List<Long> ids) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Image image = null;
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
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在陌生人:" + item);
                    return;
                }
                if (image == null) {
                    image = BotUpload.upImage(bot, file);
                    if (image == null)
                        throw new Exception("图片为空");
                }

                MessageReceipt<Stranger> message = stranger.sendMessage(image);
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
            ColorMiraiMain.logger.error("发送陌生人失败", e);
        }
    }
}
