package coloryr.colormirai.plugin.socket;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.PluginUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PluginSocketServer {

    private static ServerSocket serverSocket;
    private static Thread serverThread;
    private static boolean isStart;

    public static void start() {
        try {
            serverSocket = new ServerSocket(ColorMiraiMain.config.socketPort);
            ColorMiraiMain.logger.info("Socket已启动:" + ColorMiraiMain.config.socketPort);
            isStart = true;
            serverThread = new Thread(() -> {
                while (isStart) {
                    try {
                        Socket socket = serverSocket.accept();
                        ColorMiraiMain.logger.info("有插件连接");
                        PluginUtils.addPlugin(socket);
                    } catch (IOException e) {
                        if (!isStart)
                            return;
                        ColorMiraiMain.logger.error("Socket发生错误", e);
                    }
                }
            });
            serverThread.start();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("Socket启动失败", e);
        }
    }

    public static void stop() {
        if (serverThread != null && serverThread.isAlive()) {
            isStart = false;
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
                serverThread.join();
            } catch (Exception e) {
                ColorMiraiMain.logger.error("关闭出现错误", e);
            }
        }
    }
}
