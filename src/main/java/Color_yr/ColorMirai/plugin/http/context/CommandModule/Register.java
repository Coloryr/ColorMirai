package Color_yr.ColorMirai.plugin.http.context.CommandModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.auth.BindDTO;
import Color_yr.ColorMirai.plugin.http.obj.command.PostCommandDTO;
import Color_yr.ColorMirai.robot.BotStart;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Register implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        PostCommandDTO obj = JSONObject.parseObject(inputStream, PostCommandDTO.class);
        String response;
        if (!Utils.checkKey(obj.authKey)) {
            response = JSONObject.toJSONString(StateCode.AuthKeyFail);
        } else {
            response = JSONObject.toJSONString(StateCode.Unknown);
        }
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}