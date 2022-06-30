package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 89 [机器人]其他客户端发送群消息（事件）
 */
public class GroupMessageSyncEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 时间
     */
    public int time;
    /**
     * 消息
     */
    public List<String> message;
}
