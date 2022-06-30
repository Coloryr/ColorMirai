package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 29 [机器人]在发送群消息前广播（事件）
 */
public class GroupMessagePreSendEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 消息
     */
    public List<String> message;
}
