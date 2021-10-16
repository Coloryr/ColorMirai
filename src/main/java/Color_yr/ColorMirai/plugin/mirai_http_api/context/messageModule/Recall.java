package Color_yr.ColorMirai.plugin.mirai_http_api.context.messageModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.SessionManager;
import Color_yr.ColorMirai.plugin.mirai_http_api.Utils;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.message.RecallDTO;
import Color_yr.ColorMirai.robot.BotStart;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.message.data.OnlineMessageSource;

import java.io.IOException;
import java.io.InputStream;

public class Recall implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        RecallDTO obj = JSONObject.parseObject(inputStream, RecallDTO.class);
        String response;
        if (!SessionManager.haveKey(obj.sessionKey)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(obj.sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(obj.sessionKey);
            OnlineMessageSource messageSource = authed.cacheQueue.get(obj.target);
            if (messageSource == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else {
                Mirai.getInstance().recallMessage(messageSource.getBot(), messageSource);
                BotStart.removeMessage(authed.bot.getId(), obj.target);
                response = JSONObject.toJSONString(StateCode.Success);
            }
        }
        Utils.send(t, response);
    }
}