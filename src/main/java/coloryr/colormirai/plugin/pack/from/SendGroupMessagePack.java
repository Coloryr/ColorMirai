package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;
import java.util.Set;

/**
 * 52 [插件]发送群消息
 */
public class SendGroupMessagePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 消息
     */
    public List<String> message;
    /**
     * 群列表
     */
    public Set<Long> ids;
}
