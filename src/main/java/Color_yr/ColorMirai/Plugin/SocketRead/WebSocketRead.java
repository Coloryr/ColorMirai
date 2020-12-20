package Color_yr.ColorMirai.Plugin.SocketRead;

import Color_yr.ColorMirai.Plugin.Objs.RePackObj;
import Color_yr.ColorMirai.Plugin.PluginSocket.MyWebSocket;
import Color_yr.ColorMirai.Start;
import org.java_websocket.WebSocket;

import java.util.List;

public class WebSocketRead {

    public static RePackObj read(WebSocket webSocket) {
        try {
            if (MyWebSocket.WebSocketData.containsKey(webSocket)) {
                List<String> list = MyWebSocket.WebSocketData.get(webSocket);
                if (list.size() != 0) {
                    String data = list.remove(0);
                    if (data == null)
                        return null;
                    byte[] temp = data.getBytes(Start.ReadCharset);
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
}
