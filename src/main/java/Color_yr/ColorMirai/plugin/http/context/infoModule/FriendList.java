package Color_yr.ColorMirai.plugin.http.context.infoModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.contact.QQDTO;
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
