package Color_yr.ColorMirai.plugin.socket;

import Color_yr.ColorMirai.plugin.socket.obj.RePackObj;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class PluginSocket {
    private final ISend Send;
    private final IRead Read;
    private final Socket Socket;
    private final WebSocket WebSocket;

    public PluginSocket(Socket socket) {
        this.Socket = socket;
        this.WebSocket = null;
        Send = (data) -> PluginSocketServer.send(data, Socket);
        Read = () -> PluginSocketServer.read(Socket);
        try {
            this.Socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public PluginSocket(WebSocket socket) {
        this.WebSocket = socket;
        this.Socket = null;
        Send = (data) -> PluginWebSocketServer.send(data, WebSocket);
        Read = () -> PluginWebSocketServer.read(WebSocket);
    }

    public boolean send(byte[] data) {
        return Send.send(data);
    }

    public RePackObj Read() {
        return Read.read();
    }

    public void close() {
        if (this.Socket != null) {
            try {
                Socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.WebSocket != null) {
            this.WebSocket.close();
        }
    }
}
