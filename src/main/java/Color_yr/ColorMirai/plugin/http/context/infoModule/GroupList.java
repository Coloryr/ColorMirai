package Color_yr.ColorMirai.plugin.http.context.infoModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.contact.GroupDTO;
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
