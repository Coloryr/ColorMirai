package Color_yr.ColorMirai.Plugin.SocketRead;

import java.net.Socket;

public class SocketRead {
    public static byte[] read(Socket socket) {
        try {
            if (socket.getInputStream().available() != 0) {
                byte[] buf = new byte[socket.getInputStream().available()];
                int len = socket.getInputStream().read(buf);
                if (len > 0) {
                    return buf;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
