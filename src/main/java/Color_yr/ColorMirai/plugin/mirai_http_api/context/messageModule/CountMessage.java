package Color_yr.ColorMirai.plugin.mirai_http_api.context.messageModule;

import Color_yr.ColorMirai.plugin.mirai_http_api.Authed;
import Color_yr.ColorMirai.plugin.mirai_http_api.obj.result.IntRestfulResult;

import java.util.Map;

public class CountMessage extends GetBaseMessage {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        int count = authed.messageQueue.size();
        return new IntRestfulResult() {{
            data = count;
        }};
    }
}
