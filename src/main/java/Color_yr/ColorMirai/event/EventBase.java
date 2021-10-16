package Color_yr.ColorMirai.event;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.event.events.BotEvent;

public class EventBase extends PackBase {
    public long id;
    public int type;
    public BotEvent event;

    public EventBase(long qq, long id, int type, BotEvent event) {
        this.qq = qq;
        this.type = type;
        this.id = id;
        this.event = event;
    }
}
