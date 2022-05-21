package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/**
 * 54 [插件]发送好友消息
 */
public class SendFriendMessagePack extends PackBase {
    /**
     * QQ号
     */
    public long id;
    /**
     * 消息
     */
    public List<String> message;
    /**
     * QQ号组
     */
    public List<Long> ids;
}
