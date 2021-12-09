package coloryr.colormirai.plugin.mirai_http_api.context;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class WebsocketRouteModule implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = JSONObject.toJSONString(StateCode.NoOperateSupport);
        Utils.send(t, response);
    }
}