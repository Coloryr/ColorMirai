package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

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

    public MessageRecallEventBPack(long qq, long id, long fid, int[] mid, int time, long oid) {
        this.qq = qq;
        this.id = id;
        this.mid = mid;
        this.time = time;
        this.fid = fid;
        this.oid = oid;
    }
}
