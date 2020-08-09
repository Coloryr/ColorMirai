package Color_yr.ColorMirai.EventDo;

import Color_yr.ColorMirai.Start;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EventCall {
    private static final Map<Long, EventBase> EventsDo = new ConcurrentHashMap<>();
    private static final Random Random = new Random();

    public static long AddEvent(EventBase event) {
        var a = event.getId();
        while (EventsDo.containsKey(a)) {
            a = Random.nextLong();
        }
        EventsDo.put(a, event);
        return a;
    }

    public static void DoEvent(long id, int dofun, List<Object> arg) {
        if (EventsDo.containsKey(id)) {
            try {
                var event = EventsDo.remove(id);
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
                            data1.reject((Boolean) arg.get(0), (String) arg.get(1));
                        } else if (dofun == 2) {
                            data1.ignore((Boolean) arg.get(0));
                        }
                    case 46:
                        NewFriendRequestEvent data2 = (NewFriendRequestEvent) event.getEvent();
                        if (dofun == 0) {
                            data2.accept();
                        } else if (dofun == 1) {
                            data2.reject((Boolean) arg.get(0));
                        }
                        break;
                }
            } catch (Exception e) {
                Start.logger.error("处理事件发送错误", e);
            }
        }
    }
}
