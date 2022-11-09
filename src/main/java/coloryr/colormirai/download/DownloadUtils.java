package coloryr.colormirai.download;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.Msg;
import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.ThePlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class DownloadUtils {
    private static final Queue<DownloadTask> tasks = new ConcurrentLinkedQueue<>();
    private static final Semaphore semaphore = new Semaphore(0, true);
    private static final int downloadNumber = 10;
    private static Thread[] downThread;
    private static boolean isRun;

    private static void run() {
        HttpURLConnection connection = null;
        InputStream is = null;
        int bytesum = 0;
        int byteread;
        DownloadTask task = null;
        while (isRun) {
            try {
                semaphore.acquire();
                task = tasks.poll();
                if (task == null)
                    continue;
                URL url = new URL(task.url);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(6000);
                connection.setReadTimeout(6000);
                connection.connect();
                if (connection.getResponseCode() == 200) {
                    is = connection.getInputStream();
                    File file = new File(task.dir);
                    if (!file.exists())
                        file.createNewFile();
                    else {
                        String temp = Msg.qq(task.qq) + Msg.group(task.group) + Msg.file(task.fid) + Msg.file + Msg.existent;
                        ColorMiraiMain.logger.warn(temp);
                        task.plugin.sendPluginMessage(task.qq, "", temp);
                        continue;
                    }
                    FileOutputStream fs = new FileOutputStream(file);
                    byte[] buffer = new byte[10000000];
                    while ((byteread = is.read(buffer)) != -1) {
                        bytesum += byteread;
                        System.out.println(bytesum);
                        fs.write(buffer, 0, byteread);
                    }
                    fs.close();
                    is.close();
                }
                connection.disconnect();// 关闭远程连接
            } catch (Exception e) {
                String temp = Msg.qq(task.qq) + Msg.group(task.group) + Msg.file(task.fid) + Msg.file + Msg.download + Msg.fail;
                ColorMiraiMain.logger.error(temp, e);
                task.plugin.sendPluginMessage(task.qq, "", temp + "\r\n" + Utils.printError(e));
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ioException) {
                        ColorMiraiMain.logger.error("下载文件发生错误:", ioException);
                    }
                }
                if (connection != null)
                    connection.disconnect();
            }
        }
    }

    public static void addTask(ThePlugin plugin, long qq, long group, String url, String fid, String dir) {
        DownloadTask task = new DownloadTask();
        task.plugin = plugin;
        task.qq = qq;
        task.group = group;
        task.url = url;
        task.fid = fid;
        task.dir = dir;
        tasks.add(task);
        semaphore.release();
    }

    public static void start() {
        downThread = new Thread[downloadNumber];
        isRun = true;
        for (int a = 0; a < downloadNumber; a++) {
            downThread[a] = new Thread(DownloadUtils::run);
            downThread[a].start();
        }
    }

    public static void stop() {
        isRun = false;
    }
}
