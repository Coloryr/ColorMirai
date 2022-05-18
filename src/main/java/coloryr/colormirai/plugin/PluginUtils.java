package coloryr.colormirai.plugin;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.obj.SendPackObj;
import coloryr.colormirai.plugin.socket.SocketThread;
import coloryr.colormirai.plugin.websocket.WebSocketThread;
import coloryr.colormirai.robot.BotStart;
import org.java_websocket.WebSocket;

import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PluginUtils {
    private static final Map<String, ThePlugin> pluginList = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static Collection<ThePlugin> getAll() {
        return pluginList.values();
    }

    public static boolean havePlugin(String name) {
        return pluginList.containsKey(name);
    }

    public static boolean havePlugin() {
        return pluginList.isEmpty();
    }

    public static void addPlugin(Socket socket) {
        SocketThread thread = new SocketThread(socket);
        thread.setPlugin(new ThePlugin(thread));
    }

    public static void addPlugin(WebSocket socket) {
        WebSocketThread thread = new WebSocketThread(socket);
        thread.setPlugin(new ThePlugin(thread));
    }

    public static void addPlugin(String name, ThePlugin plugin) {
        if (pluginList.containsKey(name)) {
            ThePlugin temp = pluginList.get(name);
            temp.close();
        }
        pluginList.put(name, plugin);
        ColorMiraiMain.logger.info("插件[" + name + "]已连接");
    }

    public static void removePlugin(String name) {
        pluginList.remove(name);
        ColorMiraiMain.logger.info("插件[" + name + "]已断开");
    }

    public static void init() {
        if (ColorMiraiMain.config.pack) {
            service.scheduleAtFixedRate(() -> BotStart.addTask(new SendPackObj(60, null, 0, 0, 0)),
                    0, 30, TimeUnit.SECONDS);
        }
    }

    public static void Stop() {
        for (Map.Entry<String, ThePlugin> item : pluginList.entrySet()) {
            item.getValue().close();
        }
    }
}
