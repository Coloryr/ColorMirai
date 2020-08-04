package Color_yr.ColorMirai.EventDo;

import net.mamoe.mirai.event.events.BotEvent;

public class EventBase {
    private long id;
    private byte type;
    private BotEvent event;

    public EventBase(long id, byte type, BotEvent event) {
        this.type = type;
        this.id = id;
        this.event = event;
    }

    public void setEvent(BotEvent event) {
        this.event = event;
    }

    public BotEvent getEvent() {
        return event;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
