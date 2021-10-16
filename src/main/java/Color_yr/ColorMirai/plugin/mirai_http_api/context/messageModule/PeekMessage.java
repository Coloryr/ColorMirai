package Color_yr.ColorMirai.plugin.mirai_http_api.context.messageModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.EventDTO;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.result.EventListRestfulResult;

import java.util.List;
import java.util.Map;

public class PeekMessage extends GetBaseMessage {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        int count = Integer.parseInt(parameters.get("count"));
        List<EventDTO> list = authed.messageQueue.peek(count);

        return new EventListRestfulResult() {{
            data = list;
        }};
    }
}