package Color_yr.ColorMirai.plugin.http.context.AuthModule;

import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.auth.AuthDTO;
import Color_yr.ColorMirai.plugin.http.obj.auth.AuthRetDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Auth implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        AuthDTO obj = JSONObject.parseObject(inputStream, AuthDTO.class);
        String response;
        if (!Utils.checkKey(obj.authKey)) {
            response = JSONObject.toJSONString(StateCode.AuthKeyFail);
        } else {
            AuthRetDTO obj1 = new AuthRetDTO(0, SessionManager.createTempSession().key);
            response = JSONObject.toJSONString(obj1);
        }
        Utils.send(t, response);
    }
}
