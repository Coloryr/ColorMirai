package Color_yr.ColorMirai.Plugin.SocketSend;

import org.java_websocket.WebSocket;
import Color_yr.ColorMirai.Start;

public class WebSocketSend {
    public static boolean send(byte[] data, WebSocket socket) {
        try {
            if (!socket.isOpen())
                return false;
            socket.send(data);
            return false;
        } catch (Exception e) {
            Start.logger.error("插件通信出现问题", e);
            return true;
        }
    }
}
