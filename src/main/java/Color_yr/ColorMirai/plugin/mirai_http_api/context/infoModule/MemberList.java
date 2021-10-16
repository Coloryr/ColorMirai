package Color_yr.ColorMirai.plugin.mirai_http_api.context.infoModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.contact.MemberDTO;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemberList extends BaseInfo {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        String target = parameters.get("target");
        if (target == null) {
            return StateCode.NoElement;
        }
        try {
            Group group = authed.bot.getGroup(Long.parseLong(target));
            if (group == null) {
                return StateCode.NoElement;
            }
            List<MemberDTO> list = new ArrayList<>();
            for (NormalMember item : group.getMembers()) {
                list.add(new MemberDTO(item));
            }
            return list;
        } catch (NumberFormatException e) {
            return StateCode.NoOperateSupport;
        }
    }
}