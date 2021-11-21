package coloryr.colormirai.plugin.mirai_http_api.context.messageModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.message.SendImageDTO;
import coloryr.colormirai.robot.BotStart;
import coloryr.colormirai.robot.MessageSaveObj;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SendImageMessage implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        SendImageDTO obj = JSONObject.parseObject(inputStream, SendImageDTO.class);
        String response;
        if (!SessionManager.haveKey(obj.sessionKey)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(obj.sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(obj.sessionKey);
            Contact contact = null;
            if (obj.target != 0) {
                Friend friend = authed.bot.getFriend(obj.target);
                if (friend == null) {
                    Group group = authed.bot.getGroup(obj.target);
                    if (group != null)
                        contact = group;
                } else {
                    contact = friend;
                }
            } else if (obj.qq != 0 && obj.group != 0) {
                Group group = authed.bot.getGroup(obj.group);
                if (group != null) {
                    NormalMember member = group.get(obj.qq);
                    if (member != null) {
                        contact = member;
                    }
                }
            } else if (obj.qq != 0) {
                Friend friend = authed.bot.getFriend(obj.qq);
                if (friend != null)
                    contact = friend;
            } else if (obj.group != 0) {
                Group group = authed.bot.getGroup(obj.group);
                if (group != null)
                    contact = group;
            } else {
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
                Utils.send(t, response);
                return;
            }

            if (contact == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else {
                MessageChain message = MessageUtils.newChain();
                List<Image> list = new ArrayList<>();
                List<ExternalResource> resources = new ArrayList<>();
                for (String item : obj.urls) {
                    byte[] temp = Utils.getBytes(item);
                    if (temp == null)
                        continue;
                    ExternalResource image = ExternalResource.create(temp);
                    Image image1 = contact.uploadImage(image);
                    list.add(image1);
                    message.add(image1);
                    resources.add(image);
                }
                if (message.isEmpty()) {
                    response = JSONObject.toJSONString(StateCode.MessageNull);
                } else {
                    MessageReceipt<Contact> receipt = contact.sendMessage(message);
                    authed.cacheQueue.add(receipt.getSource());
                    int id = receipt.getSource().getIds()[0];
                    BotStart.addMessage(authed.bot.getId(), id, new MessageSaveObj() {{
                        this.source = receipt.getSource();
                        this.time = receipt.getSource().getTime();
                        this.sourceQQ = authed.bot.getId();
                        this.id = receipt.getSource().getIds()[0];
                    }});
                    response = JSONObject.toJSONString(list);
                }
                resources.forEach((item) -> {
                    try {
                        item.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        Utils.send(t, response);
    }
}