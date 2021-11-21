package coloryr.colormirai.plugin.socket;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.plugin.socket.obj.RePackObj;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class PluginWebSocketServer {

    public static Map<WebSocket, List<String>> WebSocketData = new ConcurrentHashMap<>();
    private WebSocketServer SocketServer;

    public static RePackObj read(WebSocket webSocket) {
        try {
            if (WebSocketData.containsKey(webSocket)) {
                List<String> list = WebSocketData.get(webSocket);
                if (list.size() != 0) {
                    String data = list.remove(0);
                    if (data == null)
                        return null;
                    byte[] temp = data.getBytes(ColorMiraiMain.ReadCharset);
                    return new RePackObj(temp[temp.length - 1], data.substring(0, data.length() - 1));
                }
                return null;
            } else {
                return new RePackObj((byte) -1, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean send(byte[] data, WebSocket socket) {
        try {
            if (!socket.isOpen())
                return true;
            socket.send(new String(data, ColorMiraiMain.SendCharset));
            return false;
        } catch (Exception e) {
            ColorMiraiMain.logger.error("插件通信出现问题", e);
            return true;
        }
    }

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
                ColorMiraiMain.logger.info("WebSocket已启动:" + ColorMiraiMain.Config.WebSocketPort);
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
