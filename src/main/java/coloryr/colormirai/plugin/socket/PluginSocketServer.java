package coloryr.colormirai.plugin.socket;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.socket.obj.RePackObj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PluginSocketServer {

    private ServerSocket serverSocket;
    private Thread serverThread;
    private boolean isStart;

    public static RePackObj read(Socket socket) {
        try {
            byte[] bytes = new byte[8192];
            int len;
            byte index = 0;
            StringBuilder sb = new StringBuilder();
            if (socket.getInputStream().available() != 0) {
                while ((len = socket.getInputStream().read(bytes)) != -1) {
                    if (len == 8192)
                        sb.append(new String(bytes, 0, len, ColorMiraiMain.readCharset));
                    else {
                        index = bytes[len - 1];
                        bytes[len - 1] = 0;
                        sb.append(new String(bytes, 0, len - 1, ColorMiraiMain.readCharset));
                        break;
                    }
                }
                return new RePackObj(index, sb.toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            if (socket.isClosed()) {
                return new RePackObj((byte) -1, "");
            }
            ColorMiraiMain.logger.error("插件通信出现问题", e);
        }
        return null;
    }

    public static boolean send(byte[] data, Socket socket) {
        try {
            if (!socket.isConnected() || socket.isOutputShutdown())
                return false;
            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();
            return false;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件通信出现问题", e);
            return true;
        }
    }

    public boolean pluginServerStart() {
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
            return true;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("Socket启动失败", e);
            return false;
        }
    }

    public void pluginServerStop() {
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
