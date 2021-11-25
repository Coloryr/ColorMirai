package coloryr.colormirai.robot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Audio;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.FileInputStream;
import java.io.IOException;

public class BotUpload {
    public static Image upImage(Bot bot, String file) {
        ExternalResource resource;
        try {
            FileInputStream stream = new FileInputStream(file);
            resource = ExternalResource.create(stream).toAutoCloseable();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bot.getAsFriend().uploadImage(resource);
    }

    public static Audio upAudio(Bot bot, String file) {
        ExternalResource resource;
        try {
            FileInputStream stream = new FileInputStream(file);
            resource = ExternalResource.create(stream).toAutoCloseable();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bot.getAsFriend().uploadAudio(resource);
    }
}
