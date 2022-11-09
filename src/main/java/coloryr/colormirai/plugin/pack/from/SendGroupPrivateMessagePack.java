package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;
import java.util.Set;

/**
 * 53 [插件]发送私聊消息
 */
public class SendGroupPrivateMessagePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 成员QQ号
     */
    public long fid;
    /**
     * 消息
     */
    public List<String> message;
    /**
     * 成员QQ号组
     */
    public Set<Long> ids;
}
