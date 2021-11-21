package coloryr.colormirai.config;

import coloryr.colormirai.plugin.mirai_http_api.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionObj {
    public Map<String, Session> allSession;

    public SessionObj() {
        allSession = new ConcurrentHashMap<>();
    }
}
