package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.Config.QQsObj;
import Color_yr.ColorMirai.Plugin.Objs.SendPackObj;
import Color_yr.ColorMirai.Plugin.PluginUtils;
import Color_yr.ColorMirai.Plugin.ThePlugin;
import Color_yr.ColorMirai.Start;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.network.WrongPasswordException;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class BotStart {

    private static final List<SendPackObj> Tasks = new CopyOnWriteArrayList<>();
    private static final Map<Integer, MessageSaveObj> MessageList = new ConcurrentHashMap<>();
    private static final Map<Long, Bot> bots = new HashMap<>();
    private static final List<Integer> reList = new CopyOnWriteArrayList<>();

    private static Thread EventDo;
    private static boolean isRun;

    public static boolean Start() {
        for (QQsObj item : Start.Config.QQs) {
            Bot bot = BotFactory.INSTANCE.newBot(item.QQ, item.Password, new BotConfiguration() {
                {
                    fileBasedDeviceInfo(Start.RunDir + "info.json");
                    switch (Start.Config.LoginType) {
                        case 0:
                            setProtocol(MiraiProtocol.ANDROID_PHONE);
                            break;
                        case 1:
                            setProtocol(MiraiProtocol.ANDROID_WATCH);
                            break;
                        case 2:
                            setProtocol(MiraiProtocol.ANDROID_PAD);
                            break;
                    }
                    redirectNetworkLogToDirectory(new File(Start.RunDir + "/BotNetWork"));
                    redirectBotLogToDirectory(new File(Start.RunDir + "/BotLog"));
                    setAutoReconnectOnForceOffline(Start.Config.AutoReconnect);
                }
            });
            try {
                bot.login();
                bots.put(item.QQ, bot);
                Start.logger.info("QQ:" + item.QQ + "已登录");
            } catch (WrongPasswordException e) {
                Start.logger.error("机器人密码错误", e);
            } catch (Exception e) {
                Start.logger.error("机器人错误", e);
                return false;
            }
        }
        if (bots.size() == 0) {
            Start.logger.error("没有QQ号登录");
            return false;
        }

        BotEvent host = new BotEvent();

        for (Bot item : bots.values()) {
            item.getEventChannel().registerListenerHost(host);
            break;
        }

        isRun = true;
        EventDo = new Thread(() -> {
            while (isRun) {
                try {
                    if (!Tasks.isEmpty()) {
                        SendPackObj task = Tasks.remove(0);
                        task.data += " ";
                        byte[] temp = task.data.getBytes(Start.SendCharset);
                        temp[temp.length - 1] = task.index;
                        for (ThePlugin item : PluginUtils.getAll()) {
                            item.callEvent(task, temp);
                        }
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    Start.logger.error("插件处理事件出现问题", e);
                }
            }
        });
        EventDo.start();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> {
            if (!reList.isEmpty()) {
                for (int item : reList) {
                    if (MessageList.containsKey(item)) {
                        MessageSaveObj item1 = MessageList.remove(item);
                        if (item1.time > 0 || item1.time == -1)
                            try {
                                Mirai.getInstance().recallMessage(bots.get(item1.sourceQQ), item1.source);
                            } catch (Exception e) {
                                Start.logger.error("消息撤回失败", e);
                            }
                    }
                }
                reList.clear();
            }
            for (Map.Entry<Integer, MessageSaveObj> item : MessageList.entrySet()) {
                MessageSaveObj call = item.getValue();
                if (call.time > 0)
                    call.time--;
                if (call.time != 0) {
                    MessageList.put(item.getKey(), call);
                }
            }
            if (MessageList.size() >= Start.Config.MaxList) {
                MessageList.clear();
            }
        }, 0, 1, TimeUnit.SECONDS);
        Start.logger.info("机器人已启动");
        return true;
    }

    public static void addTask(SendPackObj task) {
        Tasks.add(task);
    }

    public static void stop() {
        try {
            isRun = false;
            if (bots.size() != 0)
                for (Bot item : bots.values()) {
                    item.close(new Throwable());
                }
            bots.clear();
            if (EventDo != null)
                EventDo.join();
        } catch (Exception e) {
            Start.logger.error("关闭机器人时出现错误", e);
        }
    }

    public static MessageSaveObj getMessage(int index) {
        return MessageList.get(index);
    }

    public static void ReCall(Integer id) {
        try {
            reList.add(id);
        } catch (Exception e) {
            Start.logger.error("消息撤回失败", e);
        }
    }

    public static Map<Long, Bot> getBots() {
        return bots;
    }

    public static void addMessage(int data, MessageSaveObj obj) {
        MessageList.put(data, obj);
    }

    public static List<Long> getBotsKey() {
        return new ArrayList<>(bots.keySet());
    }

}
