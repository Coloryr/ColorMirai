package coloryr.colormirai.robot;

import coloryr.colormirai.Utils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Audio;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import okhttp3.OkHttpClient;

import java.io.*;

public class BotUpload {
    public static Image upImage(Bot bot, String file) {
        ExternalResource resource;
        try {
            Image image;
            if (file.startsWith("http")) {
                byte[] data = Utils.getUrlBytes(file);
                if(data == null)
                    return null;
                ByteArrayInputStream stream = new ByteArrayInputStream(data);
                resource = ExternalResource.create(stream).toAutoCloseable();
                image = bot.getAsFriend().uploadImage(resource);
                stream.close();
            } else {
                FileInputStream stream = new FileInputStream(file);
                resource = ExternalResource.create(stream).toAutoCloseable();
                image = bot.getAsFriend().uploadImage(resource);
                stream.close();
            }
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image upImage(Bot bot, InputStream stream) {
        ExternalResource resource;
        try {
            resource = ExternalResource.create(stream).toAutoCloseable();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bot.getAsFriend().uploadImage(resource);
    }

    public static Audio upAudio(Bot bot, InputStream stream) {
        ExternalResource resource;
        try {
            resource = ExternalResource.create(stream).toAutoCloseable();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bot.getAsFriend().uploadAudio(resource);
    }

    public static Audio upAudio(Bot bot, String file) {
        ExternalResource resource;
        try {
            Audio audio;
            if (file.startsWith("http")) {
                byte[] data = Utils.getUrlBytes(file);
                if(data == null)
                    return null;
                ByteArrayInputStream stream = new ByteArrayInputStream(data);
                resource = ExternalResource.create(stream).toAutoCloseable();
                audio = bot.getAsFriend().uploadAudio(resource);
                stream.close();
            }else {
                FileInputStream stream = new FileInputStream(file);
                resource = ExternalResource.create(stream).toAutoCloseable();
                audio = bot.getAsFriend().uploadAudio(resource);
                stream.close();
            }
            return audio;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Image upImage(Bot bot, byte[] file) {
        ExternalResource resource;
        resource = ExternalResource.create(file).toAutoCloseable();
        return bot.getAsFriend().uploadImage(resource);
    }

    public static Audio upAudio(Bot bot, byte[] file) {
        ExternalResource resource;
        resource = ExternalResource.create(file).toAutoCloseable();
        return bot.getAsFriend().uploadAudio(resource);
    }

    public static ExternalResource up(Bot bot, byte[] file) {
        ExternalResource resource;
        resource = ExternalResource.create(file).toAutoCloseable();
        return resource;
    }

    public static ExternalResource up(File file) {
        ExternalResource resource;
        resource = ExternalResource.create(file).toAutoCloseable();
        return resource;
    }

    public static ExternalResource up(String file) throws Exception {
        ExternalResource resource;
        FileInputStream stream = new FileInputStream(file);
        resource = ExternalResource.create(stream).toAutoCloseable();
        stream.close();
        return resource;
    }
}
