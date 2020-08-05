package Color_yr.ColorMirai.EventDo;

import net.mamoe.mirai.event.events.*;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EventCall {
    private static final Map<Long, EventBase> EventsDo = new ConcurrentHashMap<>();
    private static final Random Random = new Random();

    public static long AddEvent(EventBase event) {
        long a = event.getId();
        while (EventsDo.containsKey(a)) {
            a = Random.nextLong();
        }
        EventsDo.put(a, event);
        return a;
    }

    public static void DoEvent(long id, int dofun, Object... arg) {
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
                case 37:
                    MemberJoinRequestEvent data1 = (MemberJoinRequestEvent) event.getEvent();
                    if (dofun == 0) {
                        data1.accept();
                    } else if (dofun == 1) {
                        data1.reject((Boolean) arg[0], (String) arg[1]);
                    } else if (dofun == 2) {
                        data1.ignore((Boolean) arg[0]);
                    }
                case 46:
                    NewFriendRequestEvent data2 = (NewFriendRequestEvent) event.getEvent();
                    if (dofun == 0) {
                        data2.accept();
                    } else if (dofun == 1) {
                        data2.reject((Boolean) arg[0]);
                    }
                    break;
            }
        }
    }
}
