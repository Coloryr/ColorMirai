package coloryr.colormirai.plugin.mirai_http_api.context.groupManageModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.MiraiHttpUtils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.group.MemberDetailDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.group.MemberInfoDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

public class MemberInfo implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equals("GET")) {
            URI requestedUri = t.getRequestURI();
            String query = requestedUri.getRawQuery();
            Map<String, String> parameters = MiraiHttpUtils.queryToMap(query);
            String response;
            if (!parameters.containsKey("sessionKey")) {
                response = JSONObject.toJSONString(StateCode.IllegalSession);
                coloryr.colormirai.Utils.send(t, response);
                return;
            }
            String key = parameters.get("sessionKey");
            if (!SessionManager.haveKey(key)) {
                response = JSONObject.toJSONString(StateCode.IllegalSession);
            } else if (SessionManager.get(key) == null) {
                response = JSONObject.toJSONString(StateCode.NotVerifySession);
            } else {
                Authed authed = SessionManager.get(key);
                String target = parameters.get("target");
                String memberId = parameters.get("memberId");
                try {
                    long id = Long.parseLong(target);
                    long mid = Long.parseLong(memberId);
                    Group group = authed.bot.getGroup(id);
                    if (group == null) {
                        response = JSONObject.toJSONString(StateCode.NoElement);
                    } else {
                        Member member = group.get(mid);
                        if (member == null) {
                            response = JSONObject.toJSONString(StateCode.NoElement);
                        } else {
                            response = JSONObject.toJSONString(new MemberDetailDTO(member));
                        }
                    }
                } catch (NumberFormatException e) {
                    response = JSONObject.toJSONString(StateCode.NoOperateSupport);
                }
            }
            coloryr.colormirai.Utils.send(t, response);
        } else {
            InputStream inputStream = t.getRequestBody();
            MemberInfoDTO obj = JSONObject.parseObject(inputStream, MemberInfoDTO.class);
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
                        if (obj.info.name != null) {
                            member.setNameCard(obj.info.name);
                        }
                        if (obj.info.specialTitle != null) {
                            member.setNameCard(obj.info.specialTitle);
                        }
                        response = JSONObject.toJSONString(StateCode.Success);
                    }
                }
            }
            coloryr.colormirai.Utils.send(t, response);
        }
    }
}