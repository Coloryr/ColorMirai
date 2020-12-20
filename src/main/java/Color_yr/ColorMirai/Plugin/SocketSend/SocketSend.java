package Color_yr.ColorMirai.Plugin.SocketSend;

import Color_yr.ColorMirai.Start;

import java.net.Socket;

public class SocketSend {
    public static boolean send(byte[] data, Socket socket) {
        try {
            if (!socket.isConnected() || socket.isOutputShutdown())
                return false;
            socket.getOutputStream().write(data);
            socket.getOutputStream().flush();
            return false;
        } catch (Exception e) {
            Start.logger.error("插件通信出现问题", e);
            return true;
        }
    }
}
