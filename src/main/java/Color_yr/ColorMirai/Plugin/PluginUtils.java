package Color_yr.ColorMirai.Plugin;

import Color_yr.ColorMirai.Plugin.Objs.SendPackObj;
import Color_yr.ColorMirai.Plugin.Objs.SocketObj;
import Color_yr.ColorMirai.Robot.BotStart;
import Color_yr.ColorMirai.ColorMiraiMain;
import org.java_websocket.WebSocket;

import java.net.Socket;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PluginUtils {
    private static final Map<String, ThePlugin> PluginList = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static Collection<ThePlugin> getAll() {
        return PluginList.values();
    }

    public static boolean havePlugin(String name) {
        return PluginList.containsKey(name);
    }

    public static boolean havePlugin() {
        return PluginList.isEmpty();
    }

    public static void addPlugin(Socket socket) {
        new ThePlugin(new SocketObj(socket));
    }

    public static void addPlugin(WebSocket socket) {
        new ThePlugin(new SocketObj(socket));
    }

    public static void addPlugin(String name, ThePlugin plugin) {
        if (PluginList.containsKey(name)) {
            ThePlugin temp = PluginList.get(name);
            temp.close();
        }
        PluginList.put(name, plugin);
        ColorMiraiMain.logger.info("插件[" + name + "]已连接");
    }

    public static void removePlugin(String name) {
        PluginList.remove(name);
        ColorMiraiMain.logger.info("插件[" + name + "]已断开");
    }

    public static void init() {
        if (ColorMiraiMain.Config.Pack) {
            service.scheduleAtFixedRate(() -> BotStart.addTask(new SendPackObj(60, "{}", 0, 0, 0)),
                    0, 30, TimeUnit.SECONDS);
        }
    }

    public static void Stop() {
        for (Map.Entry<String, ThePlugin> item : PluginList.entrySet()) {
            item.getValue().close();
        }
    }
}
