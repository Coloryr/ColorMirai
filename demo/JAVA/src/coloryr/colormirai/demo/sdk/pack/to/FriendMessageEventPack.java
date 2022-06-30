package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 51 [机器人]收到朋友消息（事件）
 */
public class FriendMessageEventPack extends PackBase {
    /**
     * 朋友QQ号
     */
    public long id;
    /**
     * 昵称
     */
    public String name;
    /**
     * 消息
     */
    public List<String> message;
    /**
     * 时间
     */
    public int time;
}
