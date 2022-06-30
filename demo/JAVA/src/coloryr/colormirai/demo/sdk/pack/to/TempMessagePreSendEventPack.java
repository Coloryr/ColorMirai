package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 48 [机器人]在发送群临时会话消息前广播（事件）
 */
public class TempMessagePreSendEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 发送人的QQ号
     */
    public long fid;
    /**
     * 消息
     */
    public List<String> message;
}
