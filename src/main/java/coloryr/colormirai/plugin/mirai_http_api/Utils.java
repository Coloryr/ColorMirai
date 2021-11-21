package coloryr.colormirai.plugin.mirai_http_api;

import coloryr.colormirai.ColorMiraiMain;
import com.sun.net.httpserver.HttpExchange;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Utils {
    private static final Map<Integer, String> id2Name = new HashMap<>();
    private static final Map<String, Integer> name2Id = new HashMap<>();

    private static final Map<Integer, String> poke2name = new HashMap<>();
    private static final Map<String, PokeMessage> name2Poke = new HashMap<>();

    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public static boolean checkKey(String key) {
        return key.equals(ColorMiraiMain.Config.authKey);
    }

    public static String getFace(int id) {
        if (id2Name.containsKey(id)) {
            return id2Name.get(id);
        }
        return "未知表情";
    }

    public static int getFace(String name) {
        if (name2Id.containsKey(name)) {
            return name2Id.get(name);
        }
        return 0xff;
    }

    public static String getPoke(int id) {
        if (poke2name.containsKey(id)) {
            return poke2name.get(id);
        }
        return "未知戳一戳";
    }

    public static PokeMessage getPoke(String name) {
        if (name2Poke.containsKey(name)) {
            return name2Poke.get(name);
        }
        return PokeMessage.ChuoYiChuo;
    }

    public static void send(HttpExchange t, String data) throws IOException {
        OutputStream outputStream = t.getResponseBody();
        byte[] res = data.getBytes(StandardCharsets.UTF_8);
        t.sendResponseHeaders(200, res.length);
        outputStream.write(res);
        t.close();
    }

    public static MessageReceipt<Contact> sendMessage(QuoteReply quote, MessageChain messageChain, Contact target) {
        MessageChain send;
        if (quote == null) {
            send = messageChain;
        } else {
            send = MessageUtils.newChain(quote, messageChain);
        }
        return target.sendMessage(send);
    }

    public static byte[] getBytes(String url) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.connect();
            byte[] buffer = new byte[connection.getInputStream().available()];
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                is.read(buffer);
                is.close();
            }
            connection.disconnect();// 关闭远程连接
            return buffer;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取图片发生错误", e);
        }
        return null;
    }

    public static byte[] getBytesFile(String url) {
        try {
            File file = new File(url);
            if (!file.exists())
                return null;
            FileInputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            return buffer;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取图片发生错误", e);
        }
        return null;
    }

    public static File saveFile(byte[] datas) {
        try {
            UUID uuid = UUID.randomUUID();
            File file = new File(ColorMiraiMain.tempDir, uuid.toString() + ".tmp");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(datas);
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("获取图片发生错误", e);
        }
        return null;
    }

    public static String toUHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length);
        String sTemp;
        for (byte datum : data) {
            sTemp = Integer.toHexString(0xFF & datum);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    static {
        for (PokeMessage item : PokeMessage.values) {
            poke2name.put(item.getId(), item.getName());
            name2Poke.put(item.getName(), item);
        }
        for (int a = 0; a < Face.names.length; a++) {
            Face face = new Face(a);
            id2Name.put(face.getId(), face.getName());
            name2Id.put(face.getName(), face.getId());
        }
    }
}
