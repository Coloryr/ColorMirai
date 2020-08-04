package Color_yr.ColorMirai;

import Color_yr.ColorMirai.Socket.Plugins;
import Color_yr.ColorMirai.Socket.SocketServer;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BeforeImageUploadEvent;
import net.mamoe.mirai.event.events.BotAvatarChangedEvent;
import net.mamoe.mirai.event.events.BotGroupPermissionChangeEvent;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.DeviceInfo;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class BotStart {
    private static Bot bot;

    public static void Start() {
        bot = BotFactoryJvm.newBot(Start.Config.getQQ(), Start.Config.getPassword(), new BotConfiguration(){
            {
                fileBasedDeviceInfo(Start.RunDir + "info.json");
                setProtocol(MiraiProtocol.ANDROID_WATCH);
            }
        });
        try {
            bot.login();
        } catch (Exception e) {
            Start.logger.error("机器人错误", e);
            return;
        }

        Events.registerEvents(bot, new SimpleListenerHost() {
            @EventHandler
            public ListeningStatus BeforeImageUploadEvent(BeforeImageUploadEvent event) {
                String name = event.getSource().getFormatName();
                long id = event.getTarget().getId();
                return ListeningStatus.LISTENING;
            }

            @EventHandler
            public ListeningStatus BotAvatarChangedEvent(BotAvatarChangedEvent event) {
                String name = event.getBot().getNick();
                return ListeningStatus.LISTENING;
            }

            @EventHandler
            public ListeningStatus BotGroupPermissionChangeEvent(BotGroupPermissionChangeEvent event) {
                long id = event.getGroup().getId();
                String name = event.getNew().name();
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

    public static void eventCalltoPlugin(byte index, byte[] data) {
        byte[] temp = new byte[data.length + 1];
        temp[data.length] = index;
        for (Plugins item : SocketServer.PluginList.values()) {
            item.callEvent(index, temp);
        }
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
