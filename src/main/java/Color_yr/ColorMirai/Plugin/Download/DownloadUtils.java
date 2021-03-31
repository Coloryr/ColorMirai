package Color_yr.ColorMirai.Plugin.Download;

import Color_yr.ColorMirai.ColorMiraiMain;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DownloadUtils {
    private static final List<DownloadTask> task = new CopyOnWriteArrayList<>();
    private static Thread downThread;
    private static boolean isRun;
    private static final Runnable Task = () -> {
        while (isRun) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                ColorMiraiMain.logger.error("下载文件发生错误:", e);
            }
        }
    };

    public static void addTask(String url, String name, String dir) {
        DownloadTask task = new DownloadTask();

    }

    public static void start() {
        downThread = new Thread(Task);
        isRun = true;
        downThread.start();
    }

    public static void stop() {
        isRun = false;
    }
}
