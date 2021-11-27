package coloryr.colormirai.plugin.socket;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.socket.obj.SendPackObj;
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
        new ThePlugin(new PluginSocket(socket));
    }

    public static void addPlugin(WebSocket socket) {
        new ThePlugin(new PluginSocket(socket));
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
            service.scheduleAtFixedRate(() -> BotStart.addTask(new SendPackObj(60, "{}", 0, 0, 0)),
                    0, 30, TimeUnit.SECONDS);
        }
    }

    public static void Stop() {
        for (Map.Entry<String, ThePlugin> item : pluginList.entrySet()) {
            item.getValue().close();
        }
    }
}
