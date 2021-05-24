package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.config.ConfigRead;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static Map<String, Authed> allAuthed = new ConcurrentHashMap<>();
    private static Thread thread;
    private static boolean isRun;
    private static int saveCount;

    public static Session createTempSession() {
        Session session = new Session();
        String temp;
        do {
            temp = Utils.generateRandomSessionKey();
        } while (ColorMiraiMain.Sessions.allSession.containsKey(temp));
        session.isAuth = false;
        session.key = temp;
        session.time = ColorMiraiMain.Config.authTime;
        ColorMiraiMain.Sessions.allSession.put(temp, session);
        return session;
    }

    public static void start() {
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
}
