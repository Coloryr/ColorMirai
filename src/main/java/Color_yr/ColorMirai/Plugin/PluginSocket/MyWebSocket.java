package Color_yr.ColorMirai.Plugin.PluginSocket;

import Color_yr.ColorMirai.Plugin.PluginUtils;
import Color_yr.ColorMirai.ColorMiraiMain;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MyWebSocket {

    public static Map<WebSocket, List<String>> WebSocketData = new ConcurrentHashMap<>();
    private WebSocketServer SocketServer;

    public boolean pluginServerStart() {
        SocketServer = new WebSocketServer(new InetSocketAddress(ColorMiraiMain.Config.WebSocketPort)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                List<String> list = new ArrayList<>();
                WebSocketData.put(conn, list);
                PluginUtils.addPlugin(conn);
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                if (WebSocketData.containsKey(conn)) {
                    WebSocketData.get(conn).clear();
                    WebSocketData.remove(conn);
                }
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                if (WebSocketData.containsKey(conn)) {
                    WebSocketData.get(conn).add(message);
                }
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                ColorMiraiMain.logger.error("插件通信出现问题:" + conn.toString(), ex);
            }

            @Override
            public void onStart() {
                ColorMiraiMain.logger.info("WebSocket已启动");
            }
        };
        try {
            SocketServer.start();
            return true;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("WebSocket初始化失败", e);
            return false;
        }
    }

    public void pluginServerStop() {
        try {
            SocketServer.stop();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("WebSocket关闭失败", e);
        }
    }
}
