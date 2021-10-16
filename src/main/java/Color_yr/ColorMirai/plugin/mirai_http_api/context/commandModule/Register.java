package Color_yr.ColorMirai.plugin.mirai_http_api.context.commandModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Utils;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.command.PostCommandDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

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
        Utils.send(t, response);
    }
}