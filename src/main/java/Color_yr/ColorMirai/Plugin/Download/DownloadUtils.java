package Color_yr.ColorMirai.Plugin.Download;

import Color_yr.ColorMirai.ColorMiraiMain;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DownloadUtils {
    private static final List<DownloadTask> tasks = new CopyOnWriteArrayList<>();
    private static Thread downThread;
    private static boolean isRun;
    private static final Runnable Task = () -> {
        HttpURLConnection connection = null;
        InputStream is = null;
        int bytesum = 0;
        int byteread = 0;
        while (isRun) {
            try {
                if (!tasks.isEmpty()) {
                    DownloadTask task = tasks.remove(0);
                    URL url = new URL(task.url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(15000);
                    connection.setReadTimeout(60000);
                    connection.connect();
                    if (connection.getResponseCode() == 200) {
                        is = connection.getInputStream();
                        FileOutputStream fs = new FileOutputStream(task.dir + "/" + task.name);
                        byte[] buffer = new byte[8196];
                        while ((byteread = is.read(buffer)) != -1) {
                            bytesum += byteread;
                            System.out.println(bytesum);
                            fs.write(buffer, 0, byteread);
                        }
                        fs.close();
                        is.close();
                    }
                    connection.disconnect();// 关闭远程连接
                }
                Thread.sleep(50);
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
    };

    public static void addTask(String url, String name, String dir) {
        DownloadTask task = new DownloadTask();
        task.url = url;
        task.name = name;
        task.dir = dir;
        tasks.add(task);
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
