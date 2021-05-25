package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.config.ConfigRead;
import Color_yr.ColorMirai.robot.BotStart;

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
        return allAuthed.get(key);
    }

    public static void create(String key, long qq) {
        Session session = ColorMiraiMain.Sessions.allSession.get(key);
        session.isAuth = true;
        session.qq = qq;
        Authed authed;
        if (allAuthed.containsKey(key)) {
            authed = allAuthed.get(key);
            authed.close();
        }
        authed = new Authed(qq);
        allAuthed.put(key, authed);
        ConfigRead.SaveSession();
    }

    public static void tickReset(String key) {
        if (!haveKey(key))
            return;
        ColorMiraiMain.Sessions.allSession.get(key).reset();
    }

    public static void close(String key) {
        if (allAuthed.containsKey(key)) {
            Authed authed = allAuthed.get(key);
            authed.close();
            allAuthed.remove(key);
        }
        if (haveKey(key)) {
            ColorMiraiMain.Sessions.allSession.remove(key);
        }
        ConfigRead.SaveSession();
    }

    public static Session createTempSession() {
        Session session = new Session();
        String temp;
        do {
            temp = generateRandomSessionKey();
        } while (ColorMiraiMain.Sessions.allSession.containsKey(temp));
        session.isAuth = false;
        session.key = temp;
        session.qq = 0;
        session.time = ColorMiraiMain.Config.authTime;
        ColorMiraiMain.Sessions.allSession.put(temp, session);
        return session;
    }

    public static void start() {
        for (Session session : ColorMiraiMain.Sessions.allSession.values()) {
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
                Iterator<Session> list = ColorMiraiMain.Sessions.allSession.values().iterator();
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
                    ConfigRead.SaveSession();
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                ColorMiraiMain.logger.error("发生错误", e);
            }
        }
    }

    public static boolean haveKey(String key)
    {
        return ColorMiraiMain.Sessions.allSession.containsKey(key);
    }

    private static String generateRandomSessionKey() {
        StringBuilder builder = new StringBuilder();
        int data = ColorMiraiMain.random.nextInt(all.length() - 1);
        for (int a = 0; a < 8; a++) {
            builder.append(all.charAt(data));
        }
        return builder.toString();
    }
}
