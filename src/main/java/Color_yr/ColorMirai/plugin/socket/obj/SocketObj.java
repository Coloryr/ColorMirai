package Color_yr.ColorMirai.plugin.socket.obj;

import Color_yr.ColorMirai.plugin.socket.IRead;
import Color_yr.ColorMirai.plugin.socket.ISend;
import Color_yr.ColorMirai.plugin.socket.MySocketServer;
import Color_yr.ColorMirai.plugin.socket.MyWebSocket;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class SocketObj {
    private final ISend Send;
    private final IRead Read;
    private final Socket Socket;
    private final WebSocket WebSocket;

    public SocketObj(Socket socket) {
        this.Socket = socket;
        this.WebSocket = null;
        Send = (data) -> MySocketServer.send(data, Socket);
        Read = () -> MySocketServer.read(Socket);
        try {
            this.Socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public SocketObj(WebSocket socket) {
        this.WebSocket = socket;
        this.Socket = null;
        Send = (data) -> MyWebSocket.send(data, WebSocket);
        Read = () -> MyWebSocket.read(WebSocket);
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
