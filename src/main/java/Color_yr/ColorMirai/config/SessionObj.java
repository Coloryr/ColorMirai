package Color_yr.ColorMirai.config;

import Color_yr.ColorMirai.plugin.mirai_http_api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionObj {
    public Map<String, Session> allSession;

    public SessionObj() {
        allSession = new ConcurrentHashMap<>();
    }
}
