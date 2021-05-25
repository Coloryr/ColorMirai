package Color_yr.ColorMirai.plugin.http.context.MessageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.result.IntRestfulResult;

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
