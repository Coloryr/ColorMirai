package Color_yr.ColorMirai.plugin.mirai_http_api;

import Color_yr.ColorMirai.ColorMiraiMain;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.botModule.DeleteFriend;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.fileModule.FileInfo;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.fileModule.FileList;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.infoModule.*;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.SendNudge;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.WebsocketRouteModule;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.authModule.Auth;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.authModule.Release;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.authModule.Verify;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.commandModule.Command;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.commandModule.Managers;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.commandModule.Register;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.commandModule.Send;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.configRouteModule.About;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.configRouteModule.Config;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.eventRouteModule.BotInvitedJoinGroupRequestEvent;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.eventRouteModule.MemberJoinRequestEvent;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.eventRouteModule.NewFriendRequestEvent;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.fileRouteModule.*;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.groupManageModule.*;
import Color_yr.ColorMirai.plugin.mirai_http_api.context.messageModule.*;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.GroupFileInfo;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MiraiHttpApiServer implements HttpHandler {
    private HttpServer server;

    public boolean pluginServerStart() {
        try {
            server = HttpServer.create(new InetSocketAddress(ColorMiraiMain.Config.HttpPort), 10);
            server.createContext("/verify", new Auth());
            server.createContext("/bind", new Verify());
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
            server.createContext("/setEssence", new SetEssence());
            server.createContext("/resp/newFriendRequestEvent", new NewFriendRequestEvent());
            server.createContext("/resp/memberJoinRequestEvent", new MemberJoinRequestEvent());
            server.createContext("/resp/botInvitedJoinGroupRequestEvent", new BotInvitedJoinGroupRequestEvent());
            server.createContext("/friendList", new FriendList());
            server.createContext("/groupList", new GroupList());
            server.createContext("/memberList", new MemberList());
            server.createContext("/groupFileList", new GroupFileList());
            server.createContext("/groupFileInfo", new GroupFileInfo());
            server.createContext("/muteAll", new MuteAll());
            server.createContext("/unmuteAll", new UnmuteAll());
            server.createContext("/mute", new Mute());
            server.createContext("/unmute", new Unmute());
            server.createContext("/kick", new Kick());
            server.createContext("/quit", new Quit());
            server.createContext("/groupConfig", new GroupConfig());
            server.createContext("/memberInfo", new MemberInfo());
            server.createContext("/about", new About());
            server.createContext("/config", new Config());
            server.createContext("/message", new WebsocketRouteModule());
            server.createContext("/event", new WebsocketRouteModule());
            server.createContext("/all", new WebsocketRouteModule());
            server.createContext("/sendNudge", new SendNudge());
            server.createContext("/botProfile", new BotProfile());
            server.createContext("friendProfile", new FriendProfile());
            server.createContext("/memberProfile", new MemberProfile());
            server.createContext("/file/list", new FileList());
            server.createContext("/file/info", new FileInfo());
            server.createContext("/file/mkdir", new GroupMkdir());
            server.createContext("/file/delete", new GroupFileDelete());
            server.createContext("/file/move", new GroupFileMove());
            server.createContext("/file/rename", new GroupFileRename());
            server.createContext("/file/upload", new UploadFileAndSend());
            server.createContext("/deleteFriend", new DeleteFriend());
            server.createContext("/memberAdmin", new MemberAdmin());
            server.createContext("/", this);

            server.setExecutor(null);
            server.start();
            ColorMiraiMain.logger.info("mirai-http-api已启动:" + ColorMiraiMain.Config.HttpPort);
        } catch (IOException e) {
            ColorMiraiMain.logger.error("mirai-http-api启动错误", e);
            return false;
        }
        return true;
    }

    public void pluginServerStop() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        t.getResponseHeaders().set("Location", "https://github.com/Coloryr/ColorMirai/blob/main/docs/http.md");
        t.sendResponseHeaders(301, 0);
        t.close();
    }
}
