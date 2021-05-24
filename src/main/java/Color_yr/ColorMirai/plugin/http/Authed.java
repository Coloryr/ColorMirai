package Color_yr.ColorMirai.plugin.http;

import Color_yr.ColorMirai.robot.BotStart;
import net.mamoe.mirai.Bot;

public class Authed {

    private Bot bot;
    private MessageQueue messageQueue;
    private CacheQueue cacheQueue;

    public static Authed create(long qq) {
        if (BotStart.getBots().containsKey(qq)) {
            return new Authed(BotStart.getBots().get(qq));
        }
        return null;
    }

    public Authed(Bot bot) {
        messageQueue = new MessageQueue();
        cacheQueue = new CacheQueue();
        this.bot = bot;
    }

    public void close() {

    }
}
