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
import java.util.*;
import java.util.concurrent.*;

public class BotStart {

    private static final List<SendPackObj> Tasks = new CopyOnWriteArrayList<>();
    private static final Map<Long, Map<Integer, MessageSaveObj>> MessageList = new ConcurrentHashMap<>();
    private static final Map<Long, Bot> bots = new HashMap<>();
    private static final List<ReCallObj> reList = new CopyOnWriteArrayList<>();

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
                    setHighwayUploadCoroutineCount(Start.Config.HighwayUpload);
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
            MessageList.put(item.getId(), new ConcurrentHashMap<>());
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
                for (ReCallObj item : reList) {
                    if (MessageList.containsKey(item.bot)) {
                        Map<Integer, MessageSaveObj> list = MessageList.get(item.bot);
                        MessageSaveObj obj = list.get(item.mid);
                        if (obj == null) {
                            Start.logger.warn("不存在消息:" + item.mid);
                            continue;
                        }
                        if (obj.time > 0 || obj.time == -1)
                            try {
                                Mirai.getInstance().recallMessage(bots.get(item.bot), obj.source);
                                list.remove(item.mid);
                                MessageList.put(item.bot, list);
                            } catch (Exception e) {
                                Start.logger.error("消息撤回失败", e);
                            }
                    }
                }
                reList.clear();
            }
            for (Map<Integer, MessageSaveObj> item : MessageList.values()) {
                for (Map.Entry<Integer, MessageSaveObj> item1 : item.entrySet()) {
                    item1.getValue().time -= 1;
                }
                if (item.size() >= Start.Config.MaxList) {
                    Iterator<Integer> iterator = item.keySet().iterator();
                    if (iterator.hasNext()) {
                        iterator.next();
                        iterator.remove();
                    }
                }
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

    public static MessageSaveObj getMessage(long qq, int index) {
        Map<Integer, MessageSaveObj> list = MessageList.get(qq);
        if (list == null) {
            Start.logger.warn("不存在QQ:" + qq);
            return null;
        }
        return list.get(index);
    }

    public static void ReCall(long qq, int id) {
        try {
            ReCallObj obj = new ReCallObj();
            obj.mid = id;
            obj.bot = qq;
            reList.add(obj);
        } catch (Exception e) {
            Start.logger.error("消息撤回失败", e);
        }
    }

    public static Map<Long, Bot> getBots() {
        return bots;
    }

    public static void addMessage(long qq, int data, MessageSaveObj obj) {
        Map<Integer, MessageSaveObj> list = MessageList.get(qq);
        if (list == null) {
            Start.logger.warn("不存在QQ:" + qq);
        } else {
            list.put(data, obj);
            MessageList.put(qq, list);
        }
    }

    public static List<Long> getBotsKey() {
        return new ArrayList<>(bots.keySet());
    }
}
