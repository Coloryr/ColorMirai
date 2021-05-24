package Color_yr.ColorMirai.robot;

import Color_yr.ColorMirai.ColorMiraiMain;
import io.github.mzdluo123.silk4j.AudioUtils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

public class BotSendSound {
    public static void SendGroupSound(long qq, long id, String sound) {
        try {
            if (!BotStart.getBots().containsKey(qq)) {
                ColorMiraiMain.logger.warn("不存在QQ号:" + qq);
                return;
            }
            Bot bot = BotStart.getBots().get(qq);
            File res = AudioUtils.mp3ToSilk(new ByteArrayInputStream(ColorMiraiMain.decoder.decode(sound)));
            FileInputStream inputStream = new FileInputStream(res);
            ExternalResource voice = ExternalResource.create(inputStream);
            Group group = bot.getGroup(id);
            if (group == null) {
                ColorMiraiMain.logger.warn("机器人:" + qq + "不存在群:" + id);
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
            voice.close();
            res.delete();
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
            File res = AudioUtils.mp3ToSilk(stream);
            FileInputStream stream1 = new FileInputStream(res);
            ExternalResource voice = ExternalResource.create(stream1);
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
            stream1.close();
            res.delete();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("发送群语音失败", e);
        }
    }
}
