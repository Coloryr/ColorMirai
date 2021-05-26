package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.plugin.ISocket;
import Color_yr.ColorMirai.plugin.http.context.authModule.Auth;
import Color_yr.ColorMirai.plugin.http.context.authModule.Release;
import Color_yr.ColorMirai.plugin.http.context.authModule.Verify;
import Color_yr.ColorMirai.plugin.http.context.commandModule.Command;
import Color_yr.ColorMirai.plugin.http.context.commandModule.Managers;
import Color_yr.ColorMirai.plugin.http.context.commandModule.Register;
import Color_yr.ColorMirai.plugin.http.context.commandModule.Send;
import Color_yr.ColorMirai.plugin.http.context.messageModule.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.fileupload.RequestContext;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer implements ISocket, HttpHandler {
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
            server.createContext("/sendImageMessage", new SendImageMessage());
            server.createContext("/uploadImage", new UploadImage());
            server.createContext("/uploadVoice", new UploadVoice());
            server.createContext("/recall", new Recall());
            server.createContext("/setEssence", new FetchLatestMessage());
            server.createContext("/resp/newFriendRequestEvent", new FetchLatestMessage());
            server.createContext("/resp/memberJoinRequestEvent", new FetchLatestMessage());
            server.createContext("/resp/botInvitedJoinGroupRequestEvent", new FetchLatestMessage());
            server.createContext("", this);

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

    @Override
    public void handle(HttpExchange t) throws IOException {
        t.getResponseHeaders().set("Localtion", "https://github.com/Coloryr/ColorMirai/blob/main/docs/http.md");
        t.sendResponseHeaders(301, 0);
        t.close();
    }
}
