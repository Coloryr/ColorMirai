package Color_yr.ColorMirai.Plugin.SocketRead;

import Color_yr.ColorMirai.Plugin.Objs.RePackObj;
import Color_yr.ColorMirai.ColorMiraiMain;

import java.net.Socket;

public class SocketRead {
    public static RePackObj read(Socket socket) {
        try {
            byte[] bytes = new byte[8192];
            int len;
            byte index = 0;
            StringBuilder sb = new StringBuilder();
            if (socket.getInputStream().available() != 0) {
                while ((len = socket.getInputStream().read(bytes)) != -1) {
                    if (len == 8192)
                        sb.append(new String(bytes, 0, len, ColorMiraiMain.ReadCharset));
                    else {
                        index = bytes[len - 1];
                        bytes[len - 1] = 0;
                        sb.append(new String(bytes, 0, len - 1, ColorMiraiMain.ReadCharset));
                        break;
                    }
                }
                return new RePackObj(index, sb.toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            if (socket.isClosed()) {
                return new RePackObj((byte) -1, "");
            }
            ColorMiraiMain.logger.error("插件通信出现问题", e);
        }
        return null;
    }
}
