package coloryr.colormirai.demo.sdk.pack.from;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

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
    public List<Long> ids;
}
