package coloryr.colormirai.plugin.mirai_http_api;

import coloryr.colormirai.plugin.mirai_http_api.obj.BotEventDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.EventDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.IgnoreEventDTO;
import net.mamoe.mirai.event.events.BotEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MessageQueue extends ConcurrentLinkedDeque<BotEvent> {

    public List<EventDTO> fetch(int size) {
        int count = size;

        List<EventDTO> list = new ArrayList<>(count);
        while (!this.isEmpty() && count > 0) {
            BotEvent event = pop();
            EventDTO item = BotEventDTO.toDTO(event);
            if (!(item instanceof IgnoreEventDTO)) {
                list.add(item);
                count--;
            }
        }
        return list;
    }

    public List<EventDTO> fetchLatest(int size) {
        int count = size;

        List<EventDTO> list = new ArrayList<>(count);
        while (!this.isEmpty() && count > 0) {
            BotEvent event = removeLast();
            EventDTO item = BotEventDTO.toDTO(event);
            if (!(item instanceof IgnoreEventDTO)) {
                list.add(item);
                count--;
            }
        }
        return list;
    }

    public List<EventDTO> peek(int size) {
        int count = size;

        List<EventDTO> list = new ArrayList<>(count);
        while (!this.isEmpty() && count > 0) {
            BotEvent event = peek();
            EventDTO item = BotEventDTO.toDTO(event);
            if (!(item instanceof IgnoreEventDTO)) {
                list.add(item);
                count--;
            }
        }
        return list;
    }

    public List<EventDTO> peekLatest(int size) {
        int count = size;

        List<EventDTO> list = new ArrayList<>(count);
        while (!this.isEmpty() && count > 0) {
            BotEvent event = peekLast();
            EventDTO item = BotEventDTO.toDTO(event);
            if (!(item instanceof IgnoreEventDTO)) {
                list.add(item);
                count--;
            }
        }
        return list;
    }
}
