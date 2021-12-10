package coloryr.colormirai.plugin.mirai_http_api.context.fileRouteModule;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.context.fileModule.FileUtils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.MkDirDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.RemoteFileItem;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.file.AbsoluteFolder;
import net.mamoe.mirai.contact.file.RemoteFiles;
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
                RemoteFiles remoteFile = group.getFiles();
                AbsoluteFolder res = remoteFile.getRoot().createFolder(obj.directoryName);
                RemoteFileItem item = new RemoteFileItem();
                item.data = FileUtils.get(res);
                response = JSONObject.toJSONString(item);
            }
        }
        Utils.send(t, response);
    }
}