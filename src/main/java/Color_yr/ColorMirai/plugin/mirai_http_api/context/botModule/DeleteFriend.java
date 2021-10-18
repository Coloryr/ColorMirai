package Color_yr.ColorMirai.plugin.mirai_http_api.context.botModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.SessionManager;
import Color_yr.ColorMirai.plugin.mirai_http_api.Utils;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.NudgeDTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.bot.DeleteFriendDTO;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.contact.Stranger;

import java.io.IOException;
import java.io.InputStream;

public class DeleteFriend implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        InputStream inputStream = t.getRequestBody();
        DeleteFriendDTO obj = JSONObject.parseObject(inputStream, DeleteFriendDTO.class);
        String response;
        if (!SessionManager.haveKey(obj.sessionKey)) {
            response = JSONObject.toJSONString(StateCode.IllegalSession);
        } else if (SessionManager.get(obj.sessionKey) == null) {
            response = JSONObject.toJSONString(StateCode.NotVerifySession);
        } else {
            Authed authed = SessionManager.get(obj.sessionKey);
            Friend friend = authed.bot.getFriend(obj.target);
            if (friend == null) {
                response = JSONObject.toJSONString(StateCode.NoElement);
            } else {
                friend.delete();
                response = JSONObject.toJSONString(StateCode.Success);
            }
        }
        Utils.send(t, response);
    }
}