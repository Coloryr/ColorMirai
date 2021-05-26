package Color_yr.ColorMirai.plugin.http.context.fileRouteModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.file.FilePathMoveDTO;
import Color_yr.ColorMirai.plugin.http.obj.file.FileRenameDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.utils.RemoteFile;

import java.io.IOException;
import java.io.InputStream;

public class GroupFileMove implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        FilePathMoveDTO obj = JSONObject.parseObject(inputStream, FilePathMoveDTO.class);
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
            } else {
                RemoteFile remoteFile = group.getFilesRoot().resolveById(obj.id);
                if (remoteFile == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    RemoteFile dir = group.getFilesRoot().resolve(obj.movePath + "/" + remoteFile.getName());
                    if (dir.getParent() != null &&
                            ((dir.getParent() != null && dir.getParent().exists())
                                    || (dir.getParent() != null && dir.isFile()))) {
                        response = JSONObject.toJSONString(StateCode.NoElement);
                    } else {
                        boolean res = remoteFile.moveTo(dir);
                        response = JSONObject.toJSONString(res ? StateCode.Success : StateCode.PermissionDenied);
                    }
                }
            }
        }
        Utils.send(t, response);
    }
}