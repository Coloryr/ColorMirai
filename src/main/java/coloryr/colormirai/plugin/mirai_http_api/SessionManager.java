package coloryr.colormirai.plugin.mirai_http_api;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.config.ConfigRead;
import coloryr.colormirai.robot.BotStart;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static final String all = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm";
    private static final Map<String, Authed> allAuthed = new ConcurrentHashMap<>();
    private static Thread thread;
    private static boolean isRun;
    private static int saveCount;

    public static Authed get(String key) {
        ColorMiraiMain.sessions.allSession.get(key).reset();
        return allAuthed.get(key);
    }

    public static void create(String key, long qq) {
        Session session = ColorMiraiMain.sessions.allSession.get(key);
        session.isAuth = true;
        session.qq = qq;
        Authed authed;
        if (allAuthed.containsKey(key)) {
            authed = allAuthed.get(key);
            authed.close();
        }
        authed = new Authed(qq);
        allAuthed.put(key, authed);
        ConfigRead.saveSession();
    }

    public static void close(String key) {
        if (allAuthed.containsKey(key)) {
            Authed authed = allAuthed.get(key);
            authed.close();
            allAuthed.remove(key);
        }
        if (haveKey(key)) {
            ColorMiraiMain.sessions.allSession.remove(key);
        }
        ConfigRead.saveSession();
    }

    public static Session createTempSession() {
        Session session = new Session();
        String temp;
        do {
            temp = generateRandomSessionKey();
        } while (ColorMiraiMain.sessions.allSession.containsKey(temp));
        session.isAuth = false;
        session.key = temp;
        session.qq = 0;
        session.time = ColorMiraiMain.config.authTime;
        ColorMiraiMain.sessions.allSession.put(temp, session);
        return session;
    }

    public static void start() {
        for (Session session : ColorMiraiMain.sessions.allSession.values()) {
            if (!session.isAuth) {
                continue;
            }
            if (!BotStart.getBots().containsKey(session.qq)) {
                continue;
            }
            Authed authed = new Authed(session.qq);
            allAuthed.put(session.key, authed);
        }
        thread = new Thread(SessionManager::tick);
        isRun = true;
        thread.start();
    }

    public static void stop() {
        isRun = false;
    }

    private static void tick() {
        while (isRun) {
            try {
                Iterator<Session> list = ColorMiraiMain.sessions.allSession.values().iterator();
                while (list.hasNext()) {
                    Session item = list.next();
                    if (item.time > 0) {
                        item.tick();
                    } else {
                        if (allAuthed.containsKey(item.key)) {
                            Authed authed = allAuthed.get(item.key);
                            authed.close();
                            allAuthed.remove(item.key);
                        }
                        list.remove();
                    }
                }
                saveCount++;
                if (saveCount == 59) {
                    saveCount = 0;
                    ConfigRead.saveSession();
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                ColorMiraiMain.logger.error("发生错误", e);
            }
        }
    }

    public static boolean haveKey(String key) {
        return ColorMiraiMain.sessions.allSession.containsKey(key);
    }

    private static String generateRandomSessionKey() {
        StringBuilder builder = new StringBuilder();
        for (int a = 0; a < 16; a++) {
            int data = ColorMiraiMain.random.nextInt(all.length() - 1);
            builder.append(all.charAt(data));
        }
        return builder.toString();
    }
}
