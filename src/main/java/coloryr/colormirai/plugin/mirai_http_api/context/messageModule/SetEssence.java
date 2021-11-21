package coloryr.colormirai.plugin.mirai_http_api.context.messageModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.message.EssenceDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.OnlineMessageSource;

import java.io.IOException;
import java.io.InputStream;

public class SetEssence implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        EssenceDTO obj = JSONObject.parseObject(inputStream, EssenceDTO.class);
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
                Utils.send(t, response);
                return;
            }
            Group group = authed.bot.getGroup(messageSource.getTarget().getId());
            if (group == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else {
                boolean res = group.setEssenceMessage(messageSource);
                response = JSONObject.toJSONString(res ? StateCode.Success : StateCode.PermissionDenied);
            }
        }
        Utils.send(t, response);
    }
}