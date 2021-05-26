package Color_yr.ColorMirai.plugin.http.context.eventRouteModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.EventRespDTO;
import net.mamoe.mirai.Mirai;

public class BotInvitedJoinGroupRequestEvent extends BaseResp {
    @Override
    public Object toDo(Authed authed, EventRespDTO parameters) {
        Mirai.getInstance().solveBotInvitedJoinGroupRequestEvent(
                authed.bot,
                parameters.eventId,
                parameters.fromId,
                parameters.groupId,
                parameters.operate == 0);
        return StateCode.Success;
    }
}
