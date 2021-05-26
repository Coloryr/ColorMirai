package Color_yr.ColorMirai.plugin.http.context.eventRouteModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.EventRespDTO;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import net.mamoe.mirai.Mirai;

public class NewFriendRequestEvent extends BaseResp {
    @Override
    public Object toDo(Authed authed, EventRespDTO parameters) {
        Mirai.getInstance().solveNewFriendRequestEvent(
                authed.bot,
                parameters.eventId,
                parameters.fromId,
                "",
                parameters.operate == 0,
                parameters.operate == 2);
        return StateCode.Success;
    }
}
