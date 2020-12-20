package Color_yr.ColorMirai.Plugin.Objs;

import Color_yr.ColorMirai.Plugin.SocketRead.IRead;
import Color_yr.ColorMirai.Plugin.SocketRead.SocketRead;
import Color_yr.ColorMirai.Plugin.SocketRead.WebSocketRead;
import Color_yr.ColorMirai.Plugin.SocketSend.ISend;
import Color_yr.ColorMirai.Plugin.SocketSend.SocketSend;
import Color_yr.ColorMirai.Plugin.SocketSend.WebSocketSend;
import org.java_websocket.WebSocket;

import java.net.Socket;
import java.net.SocketException;

public class SocketObj {
    public Socket Socket;
    public WebSocket WebSocket;
    public int available;
    private final ISend Send;
    private final IRead Read;

    public SocketObj(Socket socket) {
        this.Socket = socket;
        Send = (data) -> SocketSend.send(data, Socket);
        Read = () -> SocketRead.read(Socket);
        try {
            this.Socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public SocketObj(WebSocket socket) {
        this.WebSocket = socket;
        Send = (data) -> WebSocketSend.send(data, WebSocket);
        Read = () -> WebSocketRead.read(WebSocket);
    }

    public boolean send(byte[] data) {
        return Send.send(data);
    }

    public byte[] Read() {
        return Read.read();
    }
}
