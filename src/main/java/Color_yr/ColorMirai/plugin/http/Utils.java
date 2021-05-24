package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;

public class Utils {
    private static final String all = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm";

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
}
