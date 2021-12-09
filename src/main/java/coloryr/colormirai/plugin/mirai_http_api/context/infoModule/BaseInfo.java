package coloryr.colormirai.plugin.mirai_http_api.context.infoModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.MiraiHttpUtils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public abstract class BaseInfo implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();
        Map<String, String> parameters = MiraiHttpUtils.queryToMap(query);
        String response;
        if (!parameters.containsKey("sessionKey")) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
            coloryr.colormirai.Utils.send(t, response);
            return;
        }
        String key = parameters.get("sessionKey");
        if (!SessionManager.haveKey(key)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(key) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(key);
            response = JSONObject.toJSONString(toDo(authed, parameters));
        }
        coloryr.colormirai.Utils.send(t, response);
    }

    abstract public Object toDo(Authed authed, Map<String, String> parameters);
}