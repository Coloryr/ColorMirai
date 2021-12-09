package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Image;

import java.util.ArrayList;
import java.util.List;

public class BotSendImage {

    public static void sendGroupImage(long qq, long id, String img) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }

            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.error("没有群：" + id);
                return;
            }
            MessageReceipt<Group> message = group.sendMessage(BotUpload.upImage(bot, ColorMiraiMain.decoder.decode(img)));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
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
            if (id != 0) {
                ids.add(id);
            }
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

    public static void sendGroupPrivateImage(long qq, long id, long fid, String file) {
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

    public static void sendFriendImage(long qq, long id, String img, List<Long> ids) {
        if (!BotStart.getBots().containsKey(qq)) {
            ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
            return;
        }
        Bot bot = BotStart.getBots().get(qq);
        Image image = null;
        if (ids == null) {
            ids = new ArrayList<>();
        }
        if (id != 0) {
            ids.add(id);
        }
        try {
            for (long item : ids) {
                Friend friend = bot.getFriend(item);
                if (friend == null) {
                    ColorMiraiMain.logger.warn("机器人:" + qq + "不存在朋友:" + id);
                    return;
                }
                if (image == null) {
                    image = BotUpload.upImage(bot, img);
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
            if (id != 0) {
                ids.add(id);
            }
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
}
