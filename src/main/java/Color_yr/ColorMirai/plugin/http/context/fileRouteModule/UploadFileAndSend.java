package Color_yr.ColorMirai.plugin.http.context.fileRouteModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.SessionManager;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.context.SimpleRequestContext;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.file.UploadFileRetDTO;
import Color_yr.ColorMirai.robot.BotStart;
import Color_yr.ColorMirai.robot.MessageSaveObj;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.RemoteFile;
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
        String file = "";
        String path = "";
        SimpleRequestContext simpleRequestContext = new SimpleRequestContext(StandardCharsets.UTF_8, inputStream, t.getRequestHeaders().get("Content-type").get(0));
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        boolean is = ServletFileUpload.isMultipartContent(simpleRequestContext);
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
                    } else if (item.getFieldName().equals("file")) {
                        file = item.getString();
                    } else if (item.getFieldName().equals("path")) {
                        file = item.getString();
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
                Utils.send(t, response);
                return;
            }

            if (!type.equals("Group")) {
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
                Utils.send(t, response);
                return;
            }

            try {
                long id = Long.parseLong(target);
                Group group = authed.bot.getGroup(id);
                if (group == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    RemoteFile dir = group.getFilesRoot().resolve(path);
                    if (!dir.exists()) {
                        response = JSONObject.toJSONString(StateCode.NoOperateSupport);
                    } else {
                        ExternalResource resource = ExternalResource.create(filedata);
                        FileMessage fileMessage = dir.upload(resource);
                        MessageChain message = MessageUtils.newChain();
                        message.add(fileMessage);
                        MessageReceipt<Group> receipt = group.sendMessage(message);
                        int id1 = receipt.getSource().getIds()[0];
                        BotStart.addMessage(authed.bot.getId(), id1, new MessageSaveObj() {{
                            this.source = receipt.getSource();
                            this.time = receipt.getSource().getTime();
                            this.sourceQQ = authed.bot.getId();
                            this.id = receipt.getSource().getIds()[0];
                        }});
                        response = JSONObject.toJSONString(new UploadFileRetDTO() {{
                            this.id = fileMessage.getId();
                        }});
                    }
                }
            } catch (NumberFormatException | IllegalStateException e) {
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
            }
        }
        Utils.send(t, response);
    }
}
