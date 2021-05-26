package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.plugin.ISocket;
import Color_yr.ColorMirai.plugin.http.context.AuthModule.Auth;
import Color_yr.ColorMirai.plugin.http.context.AuthModule.Release;
import Color_yr.ColorMirai.plugin.http.context.AuthModule.Verify;
import Color_yr.ColorMirai.plugin.http.context.CommandModule.Command;
import Color_yr.ColorMirai.plugin.http.context.CommandModule.Managers;
import Color_yr.ColorMirai.plugin.http.context.CommandModule.Register;
import Color_yr.ColorMirai.plugin.http.context.CommandModule.Send;
import Color_yr.ColorMirai.plugin.http.context.MessageModule.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer implements ISocket {
    private HttpServer server;

    @Override
    public boolean pluginServerStart() {
        try {
            server = HttpServer.create(new InetSocketAddress(ColorMiraiMain.Config.HttpPort), 10);
            server.createContext("/auth", new Auth());
            server.createContext("/verify", new Verify());
            server.createContext("/release", new Release());
            server.createContext("/command/register", new Register());
            server.createContext("/command/send", new Send());
            server.createContext("/managers", new Managers());
            server.createContext("/command", new Command());
            server.createContext("/countMessage", new CountMessage());
            server.createContext("/fetchMessage", new FetchMessage());
            server.createContext("/fetchLatestMessage", new FetchLatestMessage());
            server.createContext("/peekMessage", new PeekMessage());
            server.createContext("/peekLatestMessage", new PeekLatestMessage());
            server.createContext("/messageFromId", new MessageFromId());
            server.createContext("/sendFriendMessage", new SendFriendMessage());
            server.createContext("/sendGroupMessage", new SendGroupMessage());
            server.createContext("/sendTempMessage", new SendTempMessage());
            server.createContext("/sendImageMessage", new UploadImage());
            server.createContext("/uploadImage", new FetchLatestMessage());
            server.createContext("/uploadVoice", new FetchLatestMessage());
            server.createContext("/recall", new FetchLatestMessage());
            server.createContext("/setEssence", new FetchLatestMessage());

            server.setExecutor(null);
            server.start();
            ColorMiraiMain.logger.info("mirai-http-api已启动:" + ColorMiraiMain.Config.HttpPort);
        } catch (IOException e) {
            ColorMiraiMain.logger.error("mirai-http-api启动错误", e);
            return false;
        }
        return true;
    }

    @Override
    public void pluginServerStop() {
        if (server != null) {
            server.stop(0);
        }
    }

}
