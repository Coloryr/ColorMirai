package Color_yr.ColorMirai.plugin.http.context.groupManageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.group.GroupConfigDTO;
import Color_yr.ColorMirai.plugin.http.obj.group.GroupDetailDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.GroupSettings;
import net.mamoe.mirai.contact.MemberPermission;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

public class GroupConfig implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        if (t.getRequestMethod().equals("GET")) {
            URI requestedUri = t.getRequestURI();
            String query = requestedUri.getRawQuery();
            Map<String, String> parameters = Utils.queryToMap(query);
            String response;
            if (!parameters.containsKey("sessionKey")) {
                response = JSONObject.toJSONString(StateCode.IllegalSession);
                Utils.send(t, response);
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
                try {
                    long id = Long.parseLong(target);
                    Group group = authed.bot.getGroup(id);
                    if (group == null) {
                        response = JSONObject.toJSONString(StateCode.NoElement);
                    } else {
                        response = JSONObject.toJSONString(new GroupDetailDTO(group));
                    }
                } catch (NumberFormatException e) {
                    response = JSONObject.toJSONString(StateCode.NoOperateSupport);
                }
            }
            Utils.send(t, response);
        } else {
            InputStream inputStream = t.getRequestBody();
            GroupConfigDTO obj = JSONObject.parseObject(inputStream, GroupConfigDTO.class);
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
                    GroupSettings settings = group.getSettings();
                    if (obj.config.name != null) {
                        group.setName(obj.config.name);
                    }
                    if (obj.config.announcement != null) {
                        settings.setEntranceAnnouncement(obj.config.announcement);
                    }
                    settings.setAllowMemberInvite(obj.config.allowMemberInvite);
                    response = JSONObject.toJSONString(StateCode.Success);
                }
            }
            Utils.send(t, response);
        }
    }
}