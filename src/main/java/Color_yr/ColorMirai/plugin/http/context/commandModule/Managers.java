package Color_yr.ColorMirai.plugin.http.context.commandModule;

import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
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