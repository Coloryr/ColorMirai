package Color_yr.ColorMirai;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

public class BotStart {
    private static Bot bot;

    public static void Start() {
        bot = BotFactoryJvm.newBot(Start.Config.getQQ(), Start.Config.getPassword());
        try {
            bot.login();
        } catch (Exception e) {
            Start.logger.error("机器人错误", e);
            return;
        }

        Events.registerEvents(bot, new SimpleListenerHost() {
            @EventHandler
            public ListeningStatus onGroupMessage(GroupMessageEvent event) {
                String msgString = toString_(event.getMessage());
                event.getSender().getId();
                return ListeningStatus.LISTENING;
            }

            //处理在处理事件中发生的未捕获异常
            @Override
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                Start.logger.error("在事件处理中发生异常" + "\n" + context, exception);
            }
        });

        Start.logger.info("机器人已启动");
    }

    public static boolean SendGroupMessage(long group, String message) {
        try {
            bot.getGroup(group).sendMessage(message);
            return true;
        } catch (Exception e) {
            Start.logger.error("发送群消息失败", e);
            return false;
        }
    }

    private static String toString_(MessageChain chain) {
        return chain.contentToString();
    }
}
