package coloryr.colormirai.plugin.mirai_http_api.context.groupManageModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.group.MemberAdminDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;

import java.io.IOException;
import java.io.InputStream;

public class MemberAdmin implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        MemberAdminDTO obj = JSONObject.parseObject(inputStream, MemberAdminDTO.class);
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
            } else if (group.getBotPermission() != MemberPermission.OWNER) {
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
            } else {
                NormalMember member = group.get(obj.memberId);
                if (member == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    member.modifyAdmin(obj.assign);
                    response = JSONObject.toJSONString(StateCode.Success);
                }
            }
        }
        Utils.send(t, response);
    }
}