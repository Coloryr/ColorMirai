package coloryr.colormirai.robot;

import coloryr.colormirai.ColorMiraiMain;
import coloryr.colormirai.config.QQsObj;
import coloryr.colormirai.plugin.PluginUtils;
import coloryr.colormirai.plugin.ThePlugin;
import coloryr.colormirai.plugin.obj.SendPackObj;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.Mirai;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.MessageSourceBuilder;
import net.mamoe.mirai.message.data.MessageSourceKind;
import net.mamoe.mirai.network.WrongPasswordException;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class BotStart {

    private static final List<SendPackObj> tasks = new CopyOnWriteArrayList<>();
    private static final Map<Long, Bot> bots = new HashMap<>();
    private static final List<ReCallObj> reList = new CopyOnWriteArrayList<>();
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static final ScheduledExecutorService service1 = Executors.newSingleThreadScheduledExecutor();

    public static boolean start() {
        for (QQsObj item : ColorMiraiMain.config.qqList) {
            Bot bot = BotFactory.INSTANCE.newBot(item.qq, item.password, new BotConfiguration() {{
                fileBasedDeviceInfo(ColorMiraiMain.runDir + item.info);
                setProtocol(item.loginType);
                setHighwayUploadCoroutineCount(ColorMiraiMain.config.highwayUpload);
                redirectNetworkLogToDirectory(new File(ColorMiraiMain.runDir + "/botnetwork"));
                redirectBotLogToDirectory(new File(ColorMiraiMain.runDir + "/botlog"));
                setAutoReconnectOnForceOffline(ColorMiraiMain.config.autoReconnect);
            }});
            try {
                ColorMiraiMain.logger.info("正在登录QQ:" + item.qq);
                ColorMiraiMain.logger.info("如果登录卡住，去看看BotLog文件夹里面的日志有没有验证码");
                bot.login();
                bots.put(item.qq, bot);
                ColorMiraiMain.logger.info("QQ:" + item.qq + "已登录");
            } catch (WrongPasswordException e) {
                ColorMiraiMain.logger.error("机器人登录失败", e);
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
            break;
        }
        service.scheduleAtFixedRate(() -> {
            if (!reList.isEmpty()) {
                for (ReCallObj item : reList) {
                    try {
                        Bot bot = bots.get(item.bot);
                        MessageSource message = new MessageSourceBuilder()
                                .id(item.ids1)
                                .internalId(item.ids2)
                                .build(item.bot, item.kind);
                        Mirai.getInstance().recallMessage(bot, message);
                    } catch (Exception e) {
                        ColorMiraiMain.logger.error("消息撤回失败", e);
                    }
                }
                reList.clear();
            }
        }, 0, 1, TimeUnit.SECONDS);
        service1.scheduleAtFixedRate(() -> {
            if (!tasks.isEmpty()) {
                SendPackObj task = tasks.remove(0);
                for (ThePlugin item : PluginUtils.getAll()) {
                    item.callEvent(task, task.data, task.index);
                }
            }
        }, 0, 100, TimeUnit.MICROSECONDS);
        ColorMiraiMain.logger.info("机器人已启动");
        return true;
    }

    public static void addTask(SendPackObj task) {
        tasks.add(task);
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

    public static void reCall(long qq, int[] ids1, int[] ids2, int kind) {
        try {
            ReCallObj obj = new ReCallObj();
            obj.bot = qq;
            obj.ids1 = ids1;
            obj.ids2 = ids2;
            switch (kind){
                case 0:
                default:
                    obj.kind = MessageSourceKind.FRIEND;
                    break;
                case 1:
                    obj.kind = MessageSourceKind.GROUP;
                    break;
                case 2:
                    obj.kind = MessageSourceKind.STRANGER;
                    break;
                case 3:
                    obj.kind = MessageSourceKind.TEMP;
                    break;
            }
            reList.add(obj);
        } catch (Exception e) {
            ColorMiraiMain.logger.error("消息撤回失败", e);
        }
    }

    public static Map<Long, Bot> getBots() {
        return bots;
    }

    public static List<Long> getBotsKey() {
        return new ArrayList<>(bots.keySet());
    }
}
