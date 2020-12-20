package Color_yr.ColorMirai.Plugin.PluginSocket;

import Color_yr.ColorMirai.Plugin.PluginUtils;
import Color_yr.ColorMirai.Start;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer implements IPluginSocket {

    private ServerSocket ServerSocket;
    private Thread ServerThread;
    private boolean isStart;

    public boolean pluginServerStart() {
        try {
            ServerSocket = new ServerSocket(Start.Config.Port);
            Start.logger.info("Socket已启动:" + Start.Config.Port);
            isStart = true;
            ServerThread = new Thread(() -> {
                while (isStart) {
                    try {
                        Socket socket = ServerSocket.accept();
                        Start.logger.info("有插件连接");
                        PluginUtils.addPlugin(socket);
                    } catch (IOException e) {
                        if (!isStart)
                            return;
                        Start.logger.error("Socket发生错误", e);
                    }
                }
            });
            ServerThread.start();
            return true;
        } catch (Exception e) {
            Start.logger.error("Socket启动失败", e);
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
                Start.logger.error("关闭出现错误", e);
            }
        }
    }
}
