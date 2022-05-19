package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

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

    public MessageRecallEventAPack(long qq, long id, int[] mid, int time) {
        this.id = id;
        this.qq = qq;
        this.mid = mid;
        this.time = time;
    }
}
