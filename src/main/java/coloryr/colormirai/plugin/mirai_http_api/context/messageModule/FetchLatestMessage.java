package coloryr.colormirai.plugin.mirai_http_api.context.messageModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.context.GetBaseMessage;
import coloryr.colormirai.plugin.mirai_http_api.obj.EventDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.result.EventListRestfulResult;

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