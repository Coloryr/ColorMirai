package Color_yr.ColorMirai.EventDo;

import Color_yr.ColorMirai.ColorMiraiMain;
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
        long a = event.id;
        while (EventsDo.containsKey(a)) {
            a = Random.nextLong();
        }
        EventsDo.put(a, event);
        return a;
    }

    public static void DoEvent(long qq, long id, int dofun, List<Object> arg) {
        if (EventsDo.containsKey(id)) {
            try {
                EventBase task = EventsDo.remove(id);
                if (task.qq != qq)
                    return;
                switch (task.type) {
                    case 4: {
                        BotInvitedJoinGroupRequestEvent event = (BotInvitedJoinGroupRequestEvent) task.event;
                        if (dofun == 0) {
                            event.accept();
                        } else if (dofun == 1) {
                            event.ignore();
                        }
                        break;
                    }
                    case 37: {
                        MemberJoinRequestEvent event = (MemberJoinRequestEvent) task.event;
                        if (dofun == 0) {
                            event.accept();
                        } else if (dofun == 1) {
                            event.reject((Boolean) arg.get(0), (String) arg.get(1));
                        } else if (dofun == 2) {
                            event.ignore((Boolean) arg.get(0));
                        }
                        break;
                    }
                    case 46: {
                        NewFriendRequestEvent event = (NewFriendRequestEvent) task.event;
                        if (dofun == 0) {
                            event.accept();
                        } else if (dofun == 1) {
                            event.reject((Boolean) arg.get(0));
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                ColorMiraiMain.logger.error("处理事件发送错误", e);
            }
        }
    }
}
