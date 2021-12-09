package coloryr.colormirai.plugin.mirai_http_api.context.commandModule;

import coloryr.colormirai.plugin.mirai_http_api.MiraiHttpUtils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.command.PostCommandDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;

public class Command implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        PostCommandDTO obj = JSONObject.parseObject(inputStream, PostCommandDTO.class);
        String response;
        if (!MiraiHttpUtils.checkKey(obj.authKey)) {
            response = JSONObject.toJSONString(StateCode.AuthKeyFail);
        } else {
            response = JSONObject.toJSONString(StateCode.Unknown);
        }
        coloryr.colormirai.Utils.send(t, response);
    }
}