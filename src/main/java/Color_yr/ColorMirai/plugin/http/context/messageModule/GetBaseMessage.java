package Color_yr.ColorMirai.plugin.http.context.messageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

public abstract class GetBaseMessage implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();
        Map<String, String> parameters = Utils.queryToMap(query);
        String response;
        if (!parameters.containsKey("sessionKey")) {
            response = JSONObject.toJSONString(new StateCode(400, "错误"));
            Utils.send(t, response);
            return;
        }
        String key = parameters.get("sessionKey");
        if (!SessionManager.haveKey(key)) {
            response = JSONObject.toJSONString(StateCode.AuthKeyFail);
        } else if (SessionManager.get(key) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(key);
            response = JSONObject.toJSONString(toDo(authed, parameters));
        }
        Utils.send(t, response);
    }

    abstract public Object toDo(Authed authed, Map<String, String> parameters);
}