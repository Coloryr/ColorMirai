package Color_yr.ColorMirai;

import Color_yr.ColorMirai.EventDo.EventBase;
import Color_yr.ColorMirai.EventDo.EventCall;
import Color_yr.ColorMirai.Pack.BotAvatarChangedPack;
import Color_yr.ColorMirai.Pack.BotGroupPermissionChangePack;
import Color_yr.ColorMirai.Pack.BeforeImageUploadPack;
import Color_yr.ColorMirai.Pack.BotInvitedJoinGroupRequestEventPack;
import Color_yr.ColorMirai.Socket.Plugins;
import Color_yr.ColorMirai.Socket.SocketServer;
import com.google.gson.Gson;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BeforeImageUploadEvent;
import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BotStart {
    private static Bot bot;
    private static final List<Task> Tasks = new CopyOnWriteArrayList<>();
    private static Thread EventDo;
    private static boolean isRun;
    private static Gson Gson;

    public static boolean Start() {
        bot = BotFactoryJvm.newBot(Start.Config.getQQ(), Start.Config.getPassword(), new BotConfiguration() {
            {
                fileBasedDeviceInfo(Start.RunDir + "info.json");
                setProtocol(MiraiProtocol.ANDROID_PHONE);
            }
        });
        try {
            bot.login();
        } catch (Exception e) {
            Start.logger.error("机器人错误", e);
            return false;
        }

        Events.registerEvents(bot, new SimpleListenerHost() {
            @EventHandler
            public ListeningStatus BeforeImageUploadEvent(BeforeImageUploadEvent event) {
                String name = event.getSource().toString();
                long id = event.getTarget().getId();
                BeforeImageUploadPack pack = new BeforeImageUploadPack(name, id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task((byte) 1, data));
                return ListeningStatus.LISTENING;
            }

            @EventHandler
            public ListeningStatus BotAvatarChangedEvent(BotAvatarChangedEvent event) {
                String name = event.getBot().getNick();
                BotAvatarChangedPack pack = new BotAvatarChangedPack(name);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task((byte) 2, data));
                return ListeningStatus.LISTENING;
            }

            @EventHandler
            public ListeningStatus BotGroupPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
                long id = event.getGroup().getId();
                String name = event.getNew().name();
                BotGroupPermissionChangePack pack = new BotGroupPermissionChangePack(name, id);
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task((byte) 3, data));
                return ListeningStatus.LISTENING;
            }

            public ListeningStatus BotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event)
            {
                long id = event.getGroupId();
                String name = event.getInvitor().getNick();
                long fid = event.getInvitorId();
                BotInvitedJoinGroupRequestEventPack pack =
                        new BotInvitedJoinGroupRequestEventPack(name, id, fid, event.getEventId());
                String temp = Gson.toJson(pack);
                byte[] data = temp.getBytes(StandardCharsets.UTF_8);
                Tasks.add(new Task((byte) 3, data));
                EventCall.AddEvent(new EventBase(event.getEventId(), (byte) 4, event));
                return ListeningStatus.LISTENING;
            }

            //处理在处理事件中发生的未捕获异常
            @Override
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                Start.logger.error("在事件处理中发生异常" + "\n" + context, exception);
            }
        });

        Gson = new Gson();
        isRun = true;
        EventDo = new Thread(() -> {
            while (isRun) {
                try {
                    if (!Tasks.isEmpty()) {
                        Task Task = Tasks.remove(0);
                        eventCallToPlugin(Task.index, Task.data);
                    }
                    Thread.sleep(50);
                } catch (Exception e) {
                    Start.logger.error("插件处理事件出现问题", e);
                }
            }
        });
        EventDo.start();
        Start.logger.info("机器人已启动");
        return true;
    }

    public static void eventCallToPlugin(byte index, byte[] data) {
        byte[] temp = new byte[data.length + 1];
        temp[data.length] = index;
        for (Plugins item : SocketServer.PluginList.values()) {
            item.callEvent(index, temp);
        }
    }

    public static boolean sendGroupMessage(long group, String message) {
        try {
            bot.getGroup(group).sendMessage(message);
            return true;
        } catch (Exception e) {
            Start.logger.error("发送群消息失败", e);
            return false;
        }
    }

    static class Task {
        public byte index;
        public byte[] data;

        public Task(byte index, byte[] data) {
            this.index = index;
            this.data = data;
        }
    }
}
