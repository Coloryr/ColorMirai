package coloryr.colormirai.plugin.mirai_http_api.context.fileRouteModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.FilePathMoveDTO;
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
                RemoteFile moveTo = group.getFilesRoot();
                if (obj.moveToPath != null) {
                    moveTo = moveTo.resolve(obj.moveToPath);
                }
                moveTo = moveTo.resolveById(obj.id);
                RemoteFile remoteFile = group.getFilesRoot().resolveById(obj.id);
                if (remoteFile == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    boolean res = remoteFile.moveTo(moveTo);
                    response = JSONObject.toJSONString(res ? StateCode.Success : StateCode.PermissionDenied);
                }
            }
        }
        Utils.send(t, response);
    }
}