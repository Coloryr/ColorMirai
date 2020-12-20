package Color_yr.ColorMirai.Plugin.PluginSocket;

import Color_yr.ColorMirai.Start;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;


public class MyWebSocket implements IPluginSocket {

    private WebSocketServer SocketServer;
    @Override
    public boolean pluginServerStart() {
        SocketServer = new WebSocketServer(new InetSocketAddress(Start.Config.Port)) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {

            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {

            }

            @Override
            public void onMessage(WebSocket conn, String message) {

            }

            @Override
            public void onError(WebSocket conn, Exception ex) {

            }

            @Override
            public void onStart() {

            }
        };
        SocketServer.start();
        return false;
    }

    @Override
    public void pluginServerStop() {
        try {
            SocketServer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
