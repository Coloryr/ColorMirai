package coloryr.colormirai.plugin.one_bot;

import coloryr.colormirai.Utils;
import coloryr.colormirai.robot.BotUpload;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.*;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;

public class CQUtils {
    public CQCode toCQ(String data) {
        if (data.startsWith("[CQ:") && data.endsWith("]")) {
            CQCode code = new CQCode();
            data = data.substring(3);
            String[] args = data.split(",");
            code.type = args[0];
            if (args.length > 1) {
                code.data = new HashMap<>();
                for (int a = 1; a < args.length; a++) {
                    String item = args[a];
                    int b = item.indexOf('=');
                    if (b != -1) {
                        code.data.put(cqToqc(item.substring(0, b)), cqToqc(item.substring(b + 1)));
                    }
                    code.data.put(cqToqc(item), "");
                }
            }

            return code;
        }
        return null;
    }

    public String cqToqc(String data) {
        return data.replace("&amp;", "&")
                .replace("&#91;", "[")
                .replace("&#93;", "]")
                .replace("&#44;", ",");
    }

    public String qcTocq(String data) {
        return data.replace("&", "&amp;")
                .replace("[", "&#91;")
                .replace("]", "&#93;")
                .replace(",", "&#44;");
    }

    public Message buildMessage(Bot bot, CQCode code) throws Exception {
        String type = code.type;
        if (type.equalsIgnoreCase("text")) {
            String text = code.data.get("text");
            return new PlainText(text);
        } else if (type.equalsIgnoreCase("face")) {
            String id = code.data.get("id");
            return new Face(Integer.parseInt(id));
        } else if (type.equalsIgnoreCase("image")) {
            String file = code.data.get("file");
            String type1 = code.data.get("type");
            String url = code.data.get("url");
            boolean flash = type1.equalsIgnoreCase("flash");
            if (url != null) {
                byte[] data = Utils.getUrlBytes(url);
                if (data == null)
                    return null;
                Image image = BotUpload.upImage(bot, data);
                return flash ? new FlashImage(image) : image;
            }
            if (file != null) {
                if (file.startsWith("base64://")) {
                    byte[] data = Utils.base64D(file.replace("base64://", ""));
                    if (data == null)
                        return null;
                    Image image = BotUpload.upImage(bot, data);
                    return flash ? new FlashImage(image) : image;
                } else {
                    URI uri = new URI(file);
                    InputStream is = uri.toURL().openStream();
                    Image image = BotUpload.upImage(bot, is);
                    if (image == null)
                        return null;
                    return flash ? new FlashImage(image) : image;
                }
            }
        }else if (type.equalsIgnoreCase("record")) {
            String file = code.data.get("file");
//            String type1 = code.data.get("magic");
            String url = code.data.get("url");
//            boolean magic = type1.equalsIgnoreCase("1");
            if (url != null) {
                byte[] data = Utils.getUrlBytes(url);
                if (data == null)
                    return null;
                return BotUpload.upAudio(bot, data);
            }
            if (file != null) {
                if (file.startsWith("base64://")) {
                    byte[] data = Utils.base64D(file.replace("base64://", ""));
                    if (data == null)
                        return null;
                    return BotUpload.upAudio(bot, data);
                } else {
                    URI uri = new URI(file);
                    InputStream is = uri.toURL().openStream();
                    return BotUpload.upAudio(bot, is);
                }
            }
        }else if (type.equalsIgnoreCase("record")) {
            String file = code.data.get("file");
//            String type1 = code.data.get("magic");
            String url = code.data.get("url");
//            boolean magic = type1.equalsIgnoreCase("1");
            if (url != null) {
                byte[] data = Utils.getUrlBytes(url);
                if (data == null)
                    return null;
                return BotUpload.upAudio(bot, data);
            }
            if (file != null) {
                if (file.startsWith("base64://")) {
                    byte[] data = Utils.base64D(file.replace("base64://", ""));
                    if (data == null)
                        return null;
                    return BotUpload.upAudio(bot, data);
                } else {
                    URI uri = new URI(file);
                    InputStream is = uri.toURL().openStream();
                    return BotUpload.upAudio(bot, is);
                }
            }
        }
        return null;
    }
}
