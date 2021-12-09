package coloryr.colormirai.plugin.mirai_http_api.context.messageModule;

import coloryr.colormirai.Utils;
import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.context.SimpleRequestContext;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.message.UploadImageRetDTO;
import coloryr.colormirai.robot.BotUpload;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class UploadImage implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        byte[] imgdata = null;
        String type = "";
        String sessionKey = "";
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
                    }
                } else {
                    imgdata = item.get();
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
            if (imgdata == null) {
                response = JSONObject.toJSONString(StateCode.Null);
                coloryr.colormirai.Utils.send(t, response);
                return;
            }
            Contact contact = null;

            if (type.equals("group")) {
                Optional<Group> list = authed.bot.getGroups().stream().findFirst();
                if (list.isPresent())
                    contact = list.get();
            } else if (type.equals("friend") || type.equals("temp")) {
                Optional<Friend> list = authed.bot.getFriends().stream().findFirst();
                if (list.isPresent())
                    contact = list.get();
            }

            if (contact == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else {
                Image image = BotUpload.upImage(authed.bot, imgdata);
                File file = Utils.saveFile(imgdata);
                if (file == null) {
                    response = JSONObject.toJSONString(StateCode.Error);
                } else {
                    response = JSONObject.toJSONString(new UploadImageRetDTO() {{
                        this.imageId = image.getImageId();
                        this.url = Image.queryUrl(image);
                        this.path = file.getAbsolutePath();
                    }});
                }
            }
        }
        coloryr.colormirai.Utils.send(t, response);
    }
}
