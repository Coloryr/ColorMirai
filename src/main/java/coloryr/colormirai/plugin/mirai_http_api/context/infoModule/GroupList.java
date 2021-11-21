package coloryr.colormirai.plugin.mirai_http_api.context.infoModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.obj.contact.GroupDTO;
import net.mamoe.mirai.contact.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupList extends BaseInfo {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        List<GroupDTO> list = new ArrayList<>();
        for (Group item : authed.bot.getGroups()) {
            list.add(new GroupDTO(item));
        }
        return list;
    }
}
