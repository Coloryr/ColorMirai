package coloryr.colormirai.plugin.mirai_http_api.context.fileRouteModule;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.context.SimpleRequestContext;
import coloryr.colormirai.plugin.mirai_http_api.context.fileModule.FileUtils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.contact.GroupDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.file.UploadFileRetDTO;
import coloryr.colormirai.robot.BotUpload;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UploadFileAndSend implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        byte[] filedata = null;
        String type = "";
        String sessionKey = "";
        String target = "";
        String path = "";
        SimpleRequestContext simpleRequestContext = new SimpleRequestContext(StandardCharsets.UTF_8, inputStream, t.getRequestHeaders().get("Content-type").get(0));
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        // 解析出所有的部件
        try {
            List<FileItem> fileItems = upload.parseRequest(simpleRequestContext);
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    if (item.getFieldName().equals("sessionKey")) {
                        sessionKey = item.getString();
                    } else if (item.getFieldName().equals("type")) {
                        type = item.getString();
                    } else if (item.getFieldName().equals("target")) {
                        target = item.getString();
                    } else if (item.getFieldName().equals("path")) {
                        path = item.getString();
                    }
                } else {
                    filedata = item.get();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        String response;
        if (!SessionManager.haveKey(sessionKey)) {
            response = JSONObject.toJSONString(StateCode.AuthKeyFail);
        } else if (SessionManager.get(sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(sessionKey);
            if (filedata == null) {
                response = JSONObject.toJSONString(StateCode.Null);
                coloryr.colormirai.Utils.send(t, response);
                return;
            }

            if (!type.equals("Group")) {
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
                coloryr.colormirai.Utils.send(t, response);
                return;
            }

            try {
                long id = Long.parseLong(target);
                Group group = authed.bot.getGroup(id);
                if (group == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    AbsoluteFile files = group.getFiles().uploadNewFile(path, BotUpload.up(authed.bot, filedata));
                    response = JSONObject.toJSONString(new UploadFileRetDTO() {{
                        this.id = files.getId();
                        this.name = files.getName();
                        this.path = files.getAbsolutePath();
                        this.contact = new GroupDTO(group);
                        this.isFile = true;
                        this.isDirectory = false;
                        this.isDictionary = false;
                        this.parent = FileUtils.get(files.getParent());
                    }});
                }
            } catch (NumberFormatException | IllegalStateException e) {
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
            }
        }
        Utils.send(t, response);
    }
}
