package Color_yr.ColorMirai.plugin.http.context.MessageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.obj.EventDTO;
import Color_yr.ColorMirai.plugin.http.obj.result.EventListRestfulResult;

import java.util.List;
import java.util.Map;

public class FetchLatestMessage extends GetBaseMessage {
    @Override
    public Object toDo(Authed authed, Map<String, String> parameters) {
        int count = Integer.parseInt(parameters.get("count"));
        List<EventDTO> list = authed.messageQueue.fetchLatest(count);

        return new EventListRestfulResult() {{
            data = list;
        }};
    }
}