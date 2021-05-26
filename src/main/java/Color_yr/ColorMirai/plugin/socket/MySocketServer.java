package Color_yr.ColorMirai.plugin.socket;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.plugin.ISocket;
import Color_yr.ColorMirai.plugin.PluginUtils;
import Color_yr.ColorMirai.plugin.socket.obj.RePackObj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer implements ISocket {

    private ServerSocket ServerSocket;
    private Thread ServerThread;
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
                        sb.append(new String(bytes, 0, len, ColorMiraiMain.ReadCharset));
                    else {
                        index = bytes[len - 1];
                        bytes[len - 1] = 0;
                        sb.append(new String(bytes, 0, len - 1, ColorMiraiMain.ReadCharset));
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
            ServerSocket = new ServerSocket(ColorMiraiMain.Config.SocketPort);
            ColorMiraiMain.logger.info("Socket已启动:" + ColorMiraiMain.Config.SocketPort);
            isStart = true;
            ServerThread = new Thread(() -> {
                while (isStart) {
                    try {
                        Socket socket = ServerSocket.accept();
                        ColorMiraiMain.logger.info("有插件连接");
                        PluginUtils.addPlugin(socket);
                    } catch (IOException e) {
                        if (!isStart)
                            return;
                        ColorMiraiMain.logger.error("Socket发生错误", e);
                    }
                }
            });
            ServerThread.start();
            return true;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("Socket启动失败", e);
            return false;
        }
    }

    public void pluginServerStop() {
        if (ServerThread != null && ServerThread.isAlive()) {
            isStart = false;
            try {
                if (ServerSocket != null) {
                    ServerSocket.close();
                }
                ServerThread.join();
            } catch (Exception e) {
                ColorMiraiMain.logger.error("关闭出现错误", e);
            }
        }
    }
}
