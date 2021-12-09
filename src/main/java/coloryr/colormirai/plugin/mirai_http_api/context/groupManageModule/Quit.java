package coloryr.colormirai.plugin.mirai_http_api.context.groupManageModule;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.group.QuitDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;

import java.io.IOException;
import java.io.InputStream;

public class Quit implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        QuitDTO obj = JSONObject.parseObject(inputStream, QuitDTO.class);
        String response;
        if (!SessionManager.haveKey(obj.sessionKey)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(obj.sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(obj.sessionKey);
            Group group = authed.bot.getGroup(obj.target);
            if (group == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else if (group.getBotPermission() == MemberPermission.OWNER) {
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
            } else {
                boolean res = group.quit();
                response = JSONObject.toJSONString(res ? StateCode.Success : StateCode.PermissionDenied);
            }
        }
        Utils.send(t, response);
    }
}