package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.ThePlugin;

public class BotCheck {
    public static boolean qq(ThePlugin plugin, long qq){
        if (!BotStart.getBots().containsKey(qq)) {
            String temp = "不存在QQ号:" + qq;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(qq, "", temp);
            return false;
        }

        return true;
    }

    public static boolean qq(ThePlugin plugin, String uuid, long qq){
        if (!BotStart.getBots().containsKey(qq)) {
            String temp = "不存在QQ号:" + qq;
            ColorMiraiMain.logger.warn(temp);
            plugin.sendPluginMessage(qq, uuid, temp);
            return false;
        }

        return true;
    }
}
