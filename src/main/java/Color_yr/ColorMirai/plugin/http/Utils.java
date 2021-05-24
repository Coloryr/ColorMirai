package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;
import kotlinx.serialization.KSerializer;
import net.mamoe.mirai.message.data.Face;
import net.mamoe.mirai.message.data.MessageContent;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    private static final String all = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm";
    private static final Map<Integer, String> id2Name = new HashMap<>();
    private static final Map<String, Integer> name2Id = new HashMap<>();

    public static boolean checkKey(String key)
    {
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

    public static String getFace(int id)
    {
        if(id2Name.containsKey(id))
        {
            return id2Name.get(id);
        }
        return "未知表情";
    }
    public static int getFace(String name)
    {
        if(name2Id.containsKey(name))
        {
            return name2Id.get(name);
        }
        return 0xff;
    }


    static {
        new Face(0).getName();
    }
}
