package Color_yr.ColorMirai.plugin.http.context.groupManageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.group.KickDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;

import java.io.IOException;
import java.io.InputStream;

public class Kick implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        KickDTO obj = JSONObject.parseObject(inputStream, KickDTO.class);
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
            } else if (group.getBotPermission() == MemberPermission.MEMBER) {
                response = JSONObject.toJSONString(StateCode.PermissionDenied);
            } else {
                NormalMember member = group.get(obj.memberId);
                if (member == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    member.kick(obj.msg);
                    response = JSONObject.toJSONString(StateCode.Success);
                }
            }
        }
        Utils.send(t, response);
    }

}