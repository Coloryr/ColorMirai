package coloryr.colormirai.plugin.one_bot.context;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.one_bot.TokenUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseContext implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String token = null;
        String ctype = null;
        if (t.getRequestHeaders().containsKey("Authorization")) {
            for (String item : t.getRequestHeaders().get("Authorization")) {
                if (item.startsWith("Bearer")) {
                    token = item.replace("Bearer ", "");
                }
            }
        }
        if (t.getRequestHeaders().containsKey("Content-type")) {
            for (String item : t.getRequestHeaders().get("Content-type")) {
                ctype = item;
                break;
            }
        }
        Map<String, String> args = new HashMap<>();
        if (t.getRequestURI().getQuery() != null) {
            String[] arg = t.getRequestURI().getQuery().split("&");
            for (String item : arg) {
                String[] item1 = item.split("=");
                if (item1.length == 1) {
                    args.put(item1[0], null);
                } else if (item1[0].equalsIgnoreCase("access_token")) {
                    token = item1[1];
                } else {
                    args.put(item1[0], item1[1]);
                }
            }
        }
        if (token == null) {
            t.sendResponseHeaders(401, 0);
            t.close();
        }
        if (!TokenUtils.haveKey(token)) {
            t.sendResponseHeaders(403, 0);
            t.close();
        }

        if (t.getRequestMethod().equalsIgnoreCase("GET")) {
            Utils.send(t, get(token, args));
        } else {
            if (ctype == null) {
                t.sendResponseHeaders(406, 0);
                t.close();
                return;
            }
            CType type;
            if (ctype.equalsIgnoreCase("application/x-www-form-urlencoded")) {
                type = CType.FORM;
            } else if (ctype.startsWith("multipart/form-data")) {
                type = CType.MFORM;
            } else if (ctype.equalsIgnoreCase("application/json")) {
                type = CType.JSON;
            } else {
                t.sendResponseHeaders(406, 0);
                t.close();
                return;
            }
            Utils.send(t, post(token, type, t));
        }
    }

    abstract String get(String token, Map<String, String> args);
    abstract String post(String token, CType type, HttpExchange t);
}