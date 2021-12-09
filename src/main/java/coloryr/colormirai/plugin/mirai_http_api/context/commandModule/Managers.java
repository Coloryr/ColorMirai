package coloryr.colormirai.plugin.mirai_http_api.context.commandModule;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class Managers implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response;
        response = JSONObject.toJSONString(StateCode.Unknown);
        Utils.send(t, response);
    }
}