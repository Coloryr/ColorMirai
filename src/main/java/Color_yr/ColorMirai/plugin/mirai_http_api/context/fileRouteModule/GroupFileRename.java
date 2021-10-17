package Color_yr.ColorMirai.plugin.mirai_http_api.context.fileRouteModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.SessionManager;
import Color_yr.ColorMirai.plugin.mirai_http_api.Utils;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.file.FileRenameDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

import java.io.IOException;
import java.io.InputStream;

public class GroupFileRename implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        FileRenameDTO obj = JSONObject.parseObject(inputStream, FileRenameDTO.class);
        String response;
        if (!SessionManager.haveKey(obj.sessionKey)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(obj.sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(obj.sessionKey);
            Group group = null;
            if (obj.target != 0) {
                group = authed.bot.getGroup(obj.target);
            } else if (obj.group != 0) {
                group = authed.bot.getGroup(obj.group);
            } else if (obj.qq != 0) {
                group = authed.bot.getGroup(obj.qq);
            }
            if (group == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else {
                RemoteFile remoteFile = group.getFilesRoot();
                if (obj.path != null) {
                    remoteFile = remoteFile.resolve(obj.path);
                } else if (!obj.id.isEmpty()) {
                    remoteFile = remoteFile.resolveById(obj.id);
                }
                if (remoteFile == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    boolean res = remoteFile.renameTo(obj.rename);
                    response = JSONObject.toJSONString(res ? StateCode.Success : StateCode.PermissionDenied);
                }
            }
        }
        Utils.send(t, response);
    }
}