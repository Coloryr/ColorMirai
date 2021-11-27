package coloryr.colormirai.plugin.socket;

import coloryr.colormirai.plugin.socket.obj.RePackObj;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class PluginSocket {
    private final ISend send;
    private final IRead read;
    private final Socket socket;
    private final WebSocket websocket;

    public PluginSocket(Socket socket) {
        this.socket = socket;
        this.websocket = null;
        send = (data) -> PluginSocketServer.send(data, this.socket);
        read = () -> PluginSocketServer.read(this.socket);
        try {
            this.socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public PluginSocket(WebSocket socket) {
        this.websocket = socket;
        this.socket = null;
        send = (data) -> PluginWebSocketServer.send(data, websocket);
        read = () -> PluginWebSocketServer.read(websocket);
    }

    public boolean send(byte[] data) {
        return send.send(data);
    }

    public RePackObj Read() {
        return read.read();
    }

    public void close() {
        if (this.socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.websocket != null) {
            this.websocket.close();
        }
    }
}
