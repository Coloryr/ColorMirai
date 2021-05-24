package Color_yr.ColorMirai.plugin.http;

public class Session {
    public String key;
    public long time;
    public boolean isAuth;

    public void tick() {
        time--;
    }
}
