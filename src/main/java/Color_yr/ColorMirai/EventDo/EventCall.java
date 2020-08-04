package Color_yr.ColorMirai.EventDo;

import net.mamoe.mirai.event.events.*;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EventCall {
    private static final Map<Integer, EventBase> EventsDo = new ConcurrentHashMap<>();
    private static final Random Random = new Random();

    public static void AddEvent(EventBase event) {
        int a;
        do {
            a = Random.nextInt();
        }
        while (EventsDo.containsKey(a));
        EventsDo.put(a, event);
    }

    public static void DoEvent(int id, int dofun) {
        if (EventsDo.containsKey(id)) {
            EventBase event = EventsDo.remove(id);
            switch (event.getType()) {
                case 4:
                    BotInvitedJoinGroupRequestEvent data = (BotInvitedJoinGroupRequestEvent) event.getEvent();
                    if (dofun == 0) {
                        data.accept();
                    } else if (dofun == 1) {
                        data.ignore();
                    }
                    break;
            }
        }
    }
}
