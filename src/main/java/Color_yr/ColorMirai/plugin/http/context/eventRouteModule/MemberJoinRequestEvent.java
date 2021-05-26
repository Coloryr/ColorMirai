package Color_yr.ColorMirai.plugin.http.context.eventRouteModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.EventRespDTO;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import net.mamoe.mirai.Mirai;

public class MemberJoinRequestEvent extends BaseResp {
    @Override
    public Object toDo(Authed authed, EventRespDTO parameters) {
        boolean res;
        if (parameters.operate == 0)
            res = true;
        else res = parameters.operate % 2 == 0;
        Mirai.getInstance().solveMemberJoinRequestEvent(
                authed.bot,
                parameters.eventId,
                parameters.fromId,
                "",
                parameters.groupId,
                res,
                (parameters.operate == 3 || parameters.operate == 4),
                "");
        return StateCode.Success;
    }
}
