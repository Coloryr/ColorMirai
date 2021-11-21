package coloryr.colormirai.plugin.mirai_http_api.context.infoModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.obj.contact.QQDTO;
import net.mamoe.mirai.contact.Friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendList extends BaseInfo {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        List<QQDTO> list = new ArrayList<>();
        for (Friend item : authed.bot.getFriends()) {
            list.add(new QQDTO(item));
        }
        return list;
    }
}
