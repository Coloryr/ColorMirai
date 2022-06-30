package coloryr.colormirai.plugin.websocket;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.PluginUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;


public class PluginWebSocketServer {

    public static Map<WebSocket, Queue<String>> websocketsData = new ConcurrentHashMap<>();
    private static WebSocketServer websocketServer;

    public static String read(WebSocket webSocket) {
        try {
            if (websocketsData.containsKey(webSocket)) {
                Queue<String> list = websocketsData.get(webSocket);
                if (list == null)
                    return null;
                return list.poll();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean send(String data, WebSocket socket) {
        try {
            if (!socket.isOpen())
                return true;
            socket.send(data);
            return false;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件通信出现问题", e);
            return true;
        }
    }

    public static void start() {
        websocketServer = new WebSocketServer(new InetSocketAddress(ColorMiraiMain.config.webSocketPort)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                Queue<String> list = new ConcurrentLinkedDeque<>();
                websocketsData.put(conn, list);
                PluginUtils.addPlugin(conn);
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                if (websocketsData.containsKey(conn)) {
                    websocketsData.get(conn).clear();
                    websocketsData.remove(conn);
                }
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                if (websocketsData.containsKey(conn)) {
                    websocketsData.get(conn).add(message);
                }
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                ColorMiraiMain.logger.error("插件通信出现问题:" + conn.toString(), ex);
            }

            @Override
            public void onStart() {
                ColorMiraiMain.logger.info("WebSocket已启动:" + ColorMiraiMain.config.webSocketPort);
            }
        };
        try {
            websocketServer.start();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("WebSocket初始化失败", e);
        }
    }

    public static void stop() {
        try {
            websocketServer.stop();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("WebSocket关闭失败", e);
        }
    }
}
