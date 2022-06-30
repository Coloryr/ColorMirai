package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 45 [机器人]群消息撤回事件（事件）
 */
public class MessageRecallEventBPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 时间
     */
    public int time;
    /**
     * 群员QQ号
     */
    public long fid;
    /**
     * 撤回者
     */
    public long oid;
    /**
     * 消息ID
     */
    public int[] mid;
}
