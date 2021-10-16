package Color_yr.ColorMirai.plugin.mirai_http_api.context.groupManageModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.StateCode;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.group.MuteDTO;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.contact.NormalMember;

public class Unmute extends BaseMute {
    @Override
    public Object toDo(Authed authed, MuteDTO parameters) {
        Group group = authed.bot.getGroup(parameters.target);
        if (group == null) {
            return StateCode.NoElement;
        }
        if (group.getBotPermission() == MemberPermission.MEMBER) {
            return StateCode.PermissionDenied;
        }
        NormalMember member = group.get(parameters.memberId);
        if (member == null) {
            return StateCode.NoElement;
        }
        member.unmute();
        return StateCode.Success;
    }
}
