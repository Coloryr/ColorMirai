package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 21 [机器人]在好友消息发送后广播（事件）
 */
public class FriendMessagePostSendEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 是否成功发送
     */
    public boolean res;
    /**
     * 消息
     */
    public List<String> message;
    /**
     * 错误消息
     */
    public String error;
}
