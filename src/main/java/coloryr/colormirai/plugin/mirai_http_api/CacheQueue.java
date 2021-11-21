package coloryr.colormirai.plugin.mirai_http_api;

import coloryr.colormirai.ColorMiraiMain;
import net.mamoe.mirai.message.data.OnlineMessageSource;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheQueue extends LinkedHashMap<Integer, OnlineMessageSource> {
    private int cacheSize = ColorMiraiMain.Config.cacheSize;

    @Override
    public boolean removeEldestEntry(Map.Entry<Integer, OnlineMessageSource> eldest) {
        return size() > cacheSize;
    }

    public void add(OnlineMessageSource source) {
        int id = source.getIds().length == 0 ? 0 : source.getIds()[0];
        super.put(id, source);
    }
}
