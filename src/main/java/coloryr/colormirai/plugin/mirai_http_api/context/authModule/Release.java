package coloryr.colormirai.plugin.mirai_http_api.context.authModule;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.auth.BindDTO;
import coloryr.colormirai.robot.BotStart;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

public class Release implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        BindDTO obj = JSONObject.parseObject(inputStream, BindDTO.class);
        String response;
        Authed authed = SessionManager.get(obj.sessionKey);
        if (authed == null) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (!BotStart.getBots().containsKey(obj.qq)) {
            response = JSONObject.toJSONString(StateCode.NoBot);
        } else {
            SessionManager.close(obj.sessionKey);
            response = JSONObject.toJSONString(StateCode.Success);
        }
        Utils.send(t, response);
    }
}