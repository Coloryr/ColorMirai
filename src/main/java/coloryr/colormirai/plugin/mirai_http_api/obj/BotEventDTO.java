package coloryr.colormirai.plugin.mirai_http_api.obj;

import coloryr.colormirai.plugin.mirai_http_api.obj.message.MessageDTO;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

public class BotEventDTO extends EventDTO {
    public static EventDTO toDTO(BotEvent event) {
        if (event instanceof MessageEvent) {
            MessageEvent event1 = (MessageEvent) event;
            return MessageDTO.toDTO(event1);
        } else
            return new IgnoreEventDTO();
    }
}
