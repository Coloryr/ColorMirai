package Color_yr.ColorMirai.Socket;

import Color_yr.ColorMirai.Pack.PackBase;
import Color_yr.ColorMirai.Start;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SocketServer {
    private static final Map<String, Plugins> PluginList = new HashMap<>();
    private static final Object Lock = new Object();
    private static ServerSocket ServerSocket;
    private static Thread ServerThread;
    private static boolean isStart;

    public static void start() {
        try {
            ServerSocket = new ServerSocket(Start.Config.getPort());
            Start.logger.info("Socket已启动");
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
        } catch (Exception e) {
            Start.logger.info("Socket启动失败");
            e.printStackTrace();
        }
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

    public static void sendPack(PackBase pack, Socket socket) {
        String data = new Gson().toJson(pack);
        byte[] temp = data.getBytes(StandardCharsets.UTF_8);
        byte[] temp1 = new byte[temp.length + 1];
        temp1[0] = pack.getState();
        System.arraycopy(temp, 0, temp1, 1, temp.length);
        try {
            socket.getOutputStream().write(temp1);
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
