package Color_yr.ColorMirai.plugin.http.context.MessageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.command.PostCommandDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public abstract class GetBaseMessage implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        URI requestedUri = t.getRequestURI();
        String query = requestedUri.getRawQuery();
        Map<String, String> parameters = Utils.queryToMap(query);
        String response;
        if (!parameters.containsKey("sessionKey")) {
            response = JSONObject.toJSONString(new StateCode(400, "错误"));
            t.sendResponseHeaders(400, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
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
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    abstract public Object toDo(Authed authed, Map<String, String> parameters);
}