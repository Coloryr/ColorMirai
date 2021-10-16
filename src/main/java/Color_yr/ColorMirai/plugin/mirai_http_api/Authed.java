package Color_yr.ColorMirai.plugin.mirai_http_api;

import Color_yr.ColorMirai.robot.BotStart;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.Listener;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

public class Authed {

    public Bot bot;
    public MessageQueue messageQueue;
    public CacheQueue cacheQueue;
    public Listener<BotEvent> _listener;
    public Listener<MessageEvent> _cache;

    public Authed(long qq) {
        messageQueue = new MessageQueue();
        cacheQueue = new CacheQueue();
        this.bot = BotStart.getBots().get(qq);

        _listener = bot.getEventChannel().subscribeAlways(BotEvent.class, event -> {
            if (event.getBot() == this.bot)
                messageQueue.add(event);
        });

        _cache = bot.getEventChannel().subscribeAlways(MessageEvent.class, event -> {
            if (event.getBot() == this.bot)
                cacheQueue.add(event.getSource());
        });
    }

    public void close() {
        _listener.complete();
        _cache.complete();

        messageQueue.clear();
        cacheQueue.clear();
    }
}
