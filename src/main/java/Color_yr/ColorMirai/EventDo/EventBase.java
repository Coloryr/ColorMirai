package Color_yr.ColorMirai.EventDo;

import net.mamoe.mirai.event.events.BotEvent;

public class EventBase {
    public long id;
    public int type;
    public BotEvent event;

    public EventBase(long id, int type, BotEvent event) {
        this.type = type;
        this.id = id;
        this.event = event;
    }
}
