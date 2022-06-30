package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 44 [机器人]好友消息撤回（事件）
 */
public class MessageRecallEventAPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 消息ID
     */
    public int[] mid;
    /**
     * 时间
     */
    public int time;
}
