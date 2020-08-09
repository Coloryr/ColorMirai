package Color_yr.ColorMirai.Socket;

import Color_yr.ColorMirai.Start;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SocketServer {
    public static final Map<String, Plugins> PluginList = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static ServerSocket ServerSocket;
    private static Thread ServerThread;
    private static boolean isStart;

    public static boolean start() {
        try {
            ServerSocket = new ServerSocket(Start.Config.getPort());
            Start.logger.info("Socket已启动:" + Start.Config.getPort());
            isStart = true;
            ServerThread = new Thread(() ->
            {
                while (isStart) {
                    try {
                        var socket = ServerSocket.accept();
                        Start.logger.info("有插件连接");
                        new Plugins(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            ServerThread.start();
            service.scheduleAtFixedRate(() -> {
                for (Plugins plugin : PluginList.values()) {
                    plugin.pack();
                }
            }, 0, 30, TimeUnit.SECONDS);
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
        if (PluginList.containsKey(name)) {
            var temp = PluginList.get(name);
            temp.close();
            PluginList.put(name, plugin);
        } else
            PluginList.put(name, plugin);
        Start.logger.info("插件[" + name + "]已连接");
    }

    public static void removePlugin(String name) {
        PluginList.remove(name);
        Start.logger.info("插件[" + name + "]已断开");
    }

    public static boolean sendPack(byte[] data, Socket socket) {
        try {
            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();
            return false;
        } catch (IOException e) {
            Start.logger.error("插件通信出现问题", e);
            return true;
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
