package coloryr.colormirai.plugin.mirai_http_api;

import coloryr.colormirai.ColorMiraiMain;

public class Session {
    public String key;
    public long time;
    public boolean isAuth;
    public long qq;

    public void tick() {
        time--;
    }

    public void reset() {
        time = ColorMiraiMain.config.authTime;
    }
}
