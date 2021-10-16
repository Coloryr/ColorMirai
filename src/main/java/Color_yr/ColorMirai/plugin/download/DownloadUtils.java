package Color_yr.ColorMirai.plugin.download;

import Color_yr.ColorMirai.ColorMiraiMain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class DownloadUtils {
    private static final Queue<DownloadTask> tasks = new ConcurrentLinkedQueue<>();
    private static Thread downThread;
    private static boolean isRun;

    private static void Task() {
        HttpURLConnection connection = null;
        InputStream is = null;
        int bytesum = 0;
        int byteread;
        while (isRun) {
            try {
                Thread.sleep(50);
                DownloadTask task = tasks.poll();
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
                    File file = new File(task.dir + "/" + task.name);
                    if (!file.exists())
                        file.createNewFile();
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
                ColorMiraiMain.logger.error("下载文件发生错误:", e);
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

    public static void addTask(String url, String name, String dir) {
        DownloadTask task = new DownloadTask();
        task.url = url;
        task.name = name;
        task.dir = dir;
        tasks.add(task);
    }

    public static void start() {
        downThread = new Thread(DownloadUtils::Task);
        isRun = true;
        downThread.start();
    }

    public static void stop() {
        isRun = false;
    }
}
