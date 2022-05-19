package coloryr.colormirai.plugin.pack.to;

/**
 * 45 [机器人]群消息撤回事件（事件）
 */
public class MessageRecallEventBPack extends MessageRecallEventAPack {
    /**
     * 群员QQ号
     */
    public long fid;
    /**
     * 撤回者
     */
    public long oid;

    public MessageRecallEventBPack(long qq, long id, long fid, int[] mid, int time, long oid) {
        super(qq, id, mid, time);
        this.fid = fid;
        this.oid = oid;
    }
}
