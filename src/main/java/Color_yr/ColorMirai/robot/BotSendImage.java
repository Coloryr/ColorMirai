package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

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
            ExternalResource image = ExternalResource.create(new ByteArrayInputStream(ColorMiraiMain.decoder.decode(img)));
            MessageReceipt message = group.sendMessage(group.uploadImage(image));
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

    public static void sendGroupImageFile(long qq, long id, String file) {
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
            ExternalResource image = ExternalResource.create(stream);
            MessageReceipt message = group.sendMessage(group.uploadImage(image));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
            stream.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群图片失败", e);
        }
    }

    public static void sendGroupPrivateImage(long qq, long id, long fid, String img) {
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
            ExternalResource image = ExternalResource.create(new ByteArrayInputStream(ColorMiraiMain.decoder.decode(img)));
            MessageReceipt message = member.sendMessage(member.uploadImage(image));
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
            FileInputStream stream = new FileInputStream(file);
            ExternalResource image = ExternalResource.create(stream);
            MessageReceipt message = member.sendMessage(member.uploadImage(image));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
            stream.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送私聊图片失败", e);
        }
    }

    public static void sendFriendImage(long qq, long id, String img) {
        if (!BotStart.getBots().containsKey(qq)) {
            ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
            return;
        }
        Bot bot = BotStart.getBots().get(qq);
        try {
            Friend friend = bot.getFriend(id);
            if (friend == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在朋友:" + id);
                return;
            }
            ExternalResource image = ExternalResource.create(new ByteArrayInputStream(ColorMiraiMain.decoder.decode(img)));
            MessageReceipt message = friend.sendMessage(friend.uploadImage(image));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送朋友失败", e);
        }
    }

    public static void sendFriendImageFile(long qq, long id, String file) {
        try {
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
            FileInputStream stream = new FileInputStream(file);
            ExternalResource image = ExternalResource.create(stream);
            MessageReceipt message = friend.sendMessage(friend.uploadImage(image));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
            stream.close();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送朋友失败", e);
        }
    }
}
