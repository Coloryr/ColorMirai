package Color_yr.ColorMirai.plugin.mirai_http_api;

import Color_yr.ColorMirai.ColorMiraiMain;

public class Session {
    public String key;
    public long time;
    public boolean isAuth;
    public long qq;

    public void tick() {
        time--;
    }

    public void reset() {
        time = ColorMiraiMain.Config.authTime;
    }
}
