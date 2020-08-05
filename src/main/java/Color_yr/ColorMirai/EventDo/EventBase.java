package Color_yr.ColorMirai.EventDo;

import net.mamoe.mirai.event.events.BotEvent;

public class EventBase {
    private long id;
    private int type;
    private BotEvent event;

    public EventBase(long id, int type, BotEvent event) {
        this.type = type;
        this.id = id;
        this.event = event;
    }

    public BotEvent getEvent() {
        return event;
    }

    public void setEvent(BotEvent event) {
        this.event = event;
    }

    public int getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
