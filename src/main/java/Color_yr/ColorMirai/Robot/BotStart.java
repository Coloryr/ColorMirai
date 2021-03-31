package Color_yr.ColorMirai.Robot;

import Color_yr.ColorMirai.Config.QQsObj;
import Color_yr.ColorMirai.Plugin.Objs.SendPackObj;
import Color_yr.ColorMirai.Plugin.PluginUtils;
import Color_yr.ColorMirai.Plugin.ThePlugin;
import Color_yr.ColorMirai.ColorMiraiMain;
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
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static final ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();

    public static boolean Start() {
        for (QQsObj item : ColorMiraiMain.Config.QQs) {
            Bot bot = BotFactory.INSTANCE.newBot(item.QQ, item.Password, new BotConfiguration() {{
                fileBasedDeviceInfo(ColorMiraiMain.RunDir + item.Info);
                setProtocol(item.LoginType);
                setHighwayUploadCoroutineCount(ColorMiraiMain.Config.HighwayUpload);
                redirectNetworkLogToDirectory(new File(ColorMiraiMain.RunDir + "/BotNetWork"));
                redirectBotLogToDirectory(new File(ColorMiraiMain.RunDir + "/BotLog"));
                setAutoReconnectOnForceOffline(ColorMiraiMain.Config.AutoReconnect);
            }});
            try {
                ColorMiraiMain.logger.info("正在登录QQ:" + item.QQ);
                ColorMiraiMain.logger.info("如果登录卡住，去看看BotLog文件夹里面的日志有没有验证码");
                bot.login();
                bots.put(item.QQ, bot);
                ColorMiraiMain.logger.info("QQ:" + item.QQ + "已登录");
            } catch (WrongPasswordException e) {
                ColorMiraiMain.logger.error("机器人密码错误", e);
            } catch (Exception e) {
                ColorMiraiMain.logger.error("机器人错误", e);
                return false;
            }
        }
        if (bots.size() == 0) {
            ColorMiraiMain.logger.error("没有QQ号登录");
            return false;
        }

        BotEvent host = new BotEvent();

        for (Bot item : bots.values()) {
            item.getEventChannel().registerListenerHost(host);
            MessageList.put(item.getId(), new ConcurrentHashMap<>());
            break;
        }
        service.scheduleAtFixedRate(() -> {
            if (!reList.isEmpty()) {
                for (ReCallObj item : reList) {
                    if (MessageList.containsKey(item.bot)) {
                        Map<Integer, MessageSaveObj> list = MessageList.get(item.bot);
                        MessageSaveObj obj = list.get(item.mid);
                        if (obj == null) {
                            ColorMiraiMain.logger.warn("不存在消息:" + item.mid);
                            continue;
                        }
                        if (obj.time > 0 || obj.time == -1)
                            try {
                                Mirai.getInstance().recallMessage(bots.get(item.bot), obj.source);
                                list.remove(item.mid);
                                MessageList.put(item.bot, list);
                            } catch (Exception e) {
                                ColorMiraiMain.logger.error("消息撤回失败", e);
                            }
                    }
                }
                reList.clear();
            }
            for (Map<Integer, MessageSaveObj> item : MessageList.values()) {
                for (Map.Entry<Integer, MessageSaveObj> item1 : item.entrySet()) {
                    item1.getValue().time -= 1;
                }
                if (item.size() >= ColorMiraiMain.Config.MaxList) {
                    Iterator<Integer> iterator = item.keySet().iterator();
                    if (iterator.hasNext()) {
                        iterator.next();
                        iterator.remove();
                    }
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
        service1.scheduleAtFixedRate(() -> {
            if (!Tasks.isEmpty()) {
                SendPackObj task = Tasks.remove(0);
                task.data += " ";
                byte[] temp = task.data.getBytes(ColorMiraiMain.SendCharset);
                temp[temp.length - 1] = task.index;
                for (ThePlugin item : PluginUtils.getAll()) {
                    item.callEvent(task, temp);
                }
            }
        }, 0, 100, TimeUnit.MICROSECONDS);
        ColorMiraiMain.logger.info("机器人已启动");
        return true;
    }

    public static void addTask(SendPackObj task) {
        Tasks.add(task);
    }

    public static void stop() {
        try {
            service.shutdown();
            service1.shutdown();
            if (bots.size() != 0)
                for (Bot item : bots.values()) {
                    item.closeAndJoin(null);
                }
            bots.clear();
        } catch (Exception e) {
            ColorMiraiMain.logger.error("关闭机器人时出现错误", e);
        }
    }

    public static MessageSaveObj getMessage(long qq, int index) {
        Map<Integer, MessageSaveObj> list = MessageList.get(qq);
        if (list == null) {
            ColorMiraiMain.logger.warn("不存在QQ:" + qq);
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
            ColorMiraiMain.logger.error("消息撤回失败", e);
        }
    }

    public static Map<Long, Bot> getBots() {
        return bots;
    }

    public static void addMessage(long qq, int data, MessageSaveObj obj) {
        Map<Integer, MessageSaveObj> list = MessageList.get(qq);
        if (list == null) {
            ColorMiraiMain.logger.warn("不存在QQ:" + qq);
        } else {
            list.put(data, obj);
            MessageList.put(qq, list);
        }
    }

    public static List<Long> getBotsKey() {
        return new ArrayList<>(bots.keySet());
    }
}
