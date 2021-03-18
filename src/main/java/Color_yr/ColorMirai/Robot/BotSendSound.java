package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.Start;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.Base64;

public class BotSendSound {
    public static void SendGroupSound(long qq, long id, String sound) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            ExternalResource voice = ExternalResource.create(new ByteArrayInputStream(Start.decoder.decode(sound)));
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            MessageReceipt message = group.sendMessage(group.uploadVoice(voice));
            MessageSaveObj obj = new MessageSaveObj();
            obj.source = message.getSource();
            obj.sourceQQ = qq;
            int[] temp = obj.source.getIds();
            if (temp.length != 0 && temp[0] != -1) {
                obj.id = temp[0];
            }
            BotStart.addMessage(qq, obj.id, obj);
        } catch (Exception e) {
            Start.logger.error("发送群语音失败", e);
        }
    }

    public static void SendGroupSoundFile(long qq, long id, String file) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                Start.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            Group group = bot.getGroup(id);
            if (group == null) {
                Start.logger.warn("机器人:" + qq + "不存在群:" + id);
                return;
            }
            FileInputStream stream = new FileInputStream(file);
            ExternalResource voice = ExternalResource.create(stream);
            MessageReceipt message = group.sendMessage(group.uploadVoice(voice));
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
            Start.logger.error("发送群语音失败", e);
        }
    }
}
