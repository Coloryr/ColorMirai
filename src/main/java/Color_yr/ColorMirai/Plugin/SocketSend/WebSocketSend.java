package Color_yr.ColorMirai.Plugin.SocketSend;

import Color_yr.ColorMirai.Start;
import org.java_websocket.WebSocket;

public class WebSocketSend {
    public static boolean send(byte[] data, WebSocket socket) {
        try {
            if (!socket.isOpen())
                return true;
            socket.send(new String(data, Start.SendCharset));
            return false;
        } catch (Exception e) {
            Start.logger.error("插件通信出现问题", e);
            return true;
        }
    }
}
