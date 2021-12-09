package coloryr.colormirai.plugin.mirai_http_api;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Utils;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiraiHttpUtils {
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
        return key.equals(ColorMiraiMain.config.miraiHttpAuthKey);
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

    public static MessageReceipt<Contact> sendMessage(QuoteReply quote, MessageChain messageChain, Contact target) {
        MessageChain send;
        if (quote == null) {
            send = messageChain;
        } else {
            send = MessageUtils.newChain(quote, messageChain);
        }
        return target.sendMessage(send);
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
