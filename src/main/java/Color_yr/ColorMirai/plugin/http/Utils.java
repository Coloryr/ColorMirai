package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;
import kotlinx.serialization.KSerializer;
import net.mamoe.mirai.message.data.Face;
import net.mamoe.mirai.message.data.MessageContent;
import net.mamoe.mirai.message.data.PokeMessage;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static final String all = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm";
    private static final Map<Integer, String> id2Name = new HashMap<>();
    private static final Map<String, Integer> name2Id = new HashMap<>();

    private static final Map<Integer, String> poke2name = new HashMap<>();
    private static final Map<String, Integer> name2Poke = new HashMap<>();

    public static boolean checkKey(String key) {
        return key.equals(ColorMiraiMain.Config.authKey);
    }

    public static String generateRandomSessionKey() {
        StringBuilder builder = new StringBuilder();
        int data = ColorMiraiMain.random.nextInt(all.length() - 1);
        for (int a = 0; a < 8; a++) {
            builder.append(all.charAt(data));
        }
        return builder.toString();
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

    public static int getPoke(String name) {
        if (name2Poke.containsKey(name)) {
            return name2Poke.get(name);
        }
        return 1;
    }

    static {
        for (PokeMessage item : PokeMessage.values) {
            poke2name.put(item.getId(), item.getName());
            name2Poke.put(item.getName(), item.getId());
        }
        for (int a = 0; a < Face.names.length; a++) {
            Face face = new Face(a);
            id2Name.put(face.getId(), face.getName());
            name2Id.put(face.getName(), face.getId());
        }
    }
}
