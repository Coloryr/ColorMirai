package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.plugin.http.context.HttpAuth;
import Color_yr.ColorMirai.plugin.ISocket;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer implements ISocket {
    private HttpServer server;

    @Override
    public boolean pluginServerStart() {
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
            server.createContext("/auth", new HttpAuth());
            server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException e) {
            ColorMiraiMain.logger.error("mirai-http-api启动错误", e);
            return false;
        }
        return true;
    }

    @Override
    public void pluginServerStop() {

    }

}
