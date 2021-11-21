package coloryr.colormirai.plugin.mirai_http_api.context.configRouteModule;

import coloryr.colormirai.plugin.mirai_http_api.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.result.StringMapRestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;

public class About implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = JSONObject.toJSONString(new StringMapRestfulResult() {{
            this.data = new HashMap<>();
            data.put("version", "ColorMirai-3.7.0");
        }});
        Utils.send(t, response);
    }
}