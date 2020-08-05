package Color_yr.ColorMirai.Socket;

import Color_yr.ColorMirai.Start;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketServer {
    public static final Map<String, Plugins> PluginList = new ConcurrentHashMap<>();
    private static final Object Lock = new Object();
    private static ServerSocket ServerSocket;
    private static Thread ServerThread;
    private static boolean isStart;

    public static boolean start() {
        try {
            ServerSocket = new ServerSocket(Start.Config.getPort());
            Start.logger.info("Socket已启动:" + Start.Config.getPort());
            Runnable accept = () -> {
                while (isStart) {
                    try {
                        Socket socket = ServerSocket.accept();
                        Start.logger.info("有插件连接");
                        new Plugins(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            isStart = true;
            ServerThread = new Thread(accept);
            ServerThread.start();
            return true;
        } catch (Exception e) {
            Start.logger.error("Socket启动失败", e);
            return false;
        }
    }

    public static boolean havePlugin() {
        return PluginList.isEmpty();
    }

    public static void addPlugin(String name, Plugins plugin) {
        synchronized (Lock) {
            if (PluginList.containsKey(name)) {
                Plugins temp = PluginList.get(name);
                temp.close();
                PluginList.put(name, plugin);
            } else
                PluginList.put(name, plugin);
            Start.logger.info("插件[" + name + "]已连接");
        }
    }

    public static void removePlugin(String name) {
        synchronized (Lock) {
            if (PluginList.containsKey(name)) {
                Plugins temp = PluginList.get(name);
                temp.close();
            }
            PluginList.remove(name);
            Start.logger.info("插件[" + name + "]已断开");
        }
    }

    public static void sendPack(byte[] data, Socket socket) {
        try {
            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();
        } catch (IOException e) {
            Start.logger.error("插件通信出现问题", e);
        }
    }

    public static void stop() {
        if (ServerThread != null && ServerThread.isAlive()) {
            isStart = false;
            try {
                if (ServerSocket != null) {
                    ServerSocket.close();
                }
                ServerThread.join();
                for (Map.Entry<String, Plugins> item : PluginList.entrySet()) {
                    item.getValue().close();
                }
            } catch (Exception e) {
                Start.logger.error("关闭出现错误", e);
            }
        }
    }
}
