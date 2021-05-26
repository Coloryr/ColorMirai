package Color_yr.ColorMirai.plugin.http.context.fileRouteModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.file.FileDeleteDTO;
import Color_yr.ColorMirai.plugin.http.obj.file.MkDirDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

import java.io.IOException;
import java.io.InputStream;

public class GroupMkdir implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        MkDirDTO obj = JSONObject.parseObject(inputStream, MkDirDTO.class);
        String response;
        if (!SessionManager.haveKey(obj.sessionKey)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(obj.sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(obj.sessionKey);
            Group group = authed.bot.getGroup(obj.group);
            if (group == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else {
                RemoteFile remoteFile = group.getFilesRoot().resolve(obj.dir);
                if (remoteFile.isDirectory()) {
                    response = JSONObject.toJSONString(StateCode.Exists);
                } else {
                    boolean res = remoteFile.mkdir();
                    response = JSONObject.toJSONString(res ? StateCode.Success : StateCode.PermissionDenied);
                }
            }
        }
        Utils.send(t, response);
    }
}