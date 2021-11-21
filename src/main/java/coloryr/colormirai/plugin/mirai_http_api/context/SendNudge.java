package coloryr.colormirai.plugin.mirai_http_api.context;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.SessionManager;
import coloryr.colormirai.plugin.mirai_http_api.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.NudgeDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.Stranger;

import java.io.IOException;
import java.io.InputStream;

public class SendNudge implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        NudgeDTO obj = JSONObject.parseObject(inputStream, NudgeDTO.class);
        String response;
        if (!SessionManager.haveKey(obj.sessionKey)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(obj.sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(obj.sessionKey);
            if (obj.kind.equals("Friend")) {
                Friend friend = authed.bot.getFriend(obj.target);
                if (friend == null) {
                    Stranger stranger = authed.bot.getStranger(obj.target);
                    if (stranger == null) {
                        response = JSONObject.toJSONString(StateCode.NoElement);
                    } else {
                        stranger.nudge().sendTo(stranger);
                        response = JSONObject.toJSONString(StateCode.Success);
                    }
                } else {
                    friend.nudge().sendTo(friend);
                    response = JSONObject.toJSONString(StateCode.Success);
                }
            } else if (obj.kind.equals("Group")) {
                Group group = authed.bot.getGroup(obj.subject);
                if (group == null) {
                    response = JSONObject.toJSONString(StateCode.NoElement);
                } else {
                    NormalMember member = group.get(obj.target);
                    if (member == null) {
                        response = JSONObject.toJSONString(StateCode.NoElement);
                    } else {
                        member.nudge().sendTo(group);
                        response = JSONObject.toJSONString(StateCode.Success);
                    }
                }
            } else
                response = JSONObject.toJSONString(StateCode.NoOperateSupport);
        }
        Utils.send(t, response);
    }
}