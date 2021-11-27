package coloryr.colormirai.robot.event;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class EventCall {
    private static final Map<Long, EventBase> eventsDo = new ConcurrentHashMap<>();
    private static final Random random = new Random();

    public static long addEvent(EventBase event) {
        long a = event.id;
        while (eventsDo.containsKey(a)) {
            a = random.nextLong();
        }
        eventsDo.put(a, event);
        return a;
    }

    public static void doEvent(long qq, long id, int dofun, List<Object> arg) {
        if (eventsDo.containsKey(id)) {
            try {
                EventBase task = eventsDo.remove(id);
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
