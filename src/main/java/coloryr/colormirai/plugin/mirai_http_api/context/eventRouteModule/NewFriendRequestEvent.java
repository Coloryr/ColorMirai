package coloryr.colormirai.plugin.mirai_http_api.context.eventRouteModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.obj.EventRespDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
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
