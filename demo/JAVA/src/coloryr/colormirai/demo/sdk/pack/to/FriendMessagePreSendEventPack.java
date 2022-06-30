package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 22 [机器人]在发送好友消息前广播（事件）
 */
public class FriendMessagePreSendEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 消息
     */
    public List<String> message;
}
