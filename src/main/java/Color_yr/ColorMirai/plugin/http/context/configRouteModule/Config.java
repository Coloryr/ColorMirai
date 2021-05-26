package Color_yr.ColorMirai.plugin.http.context.configRouteModule;

import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.result.StringMapRestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;

public class Config implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = JSONObject.toJSONString(StateCode.NoOperateSupport);
        Utils.send(t, response);
    }
}