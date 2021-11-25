package coloryr.colormirai.demo.sdk.pack.to;

/*
45 [机器人]群消息撤回事件（事件）
id:群号
mid:消息ID
time:时间
fid:群员QQ号
oid:撤回者
 */
public class MessageRecallEventBPack extends MessageRecallEventAPack {
    public long fid;
    public long oid;

    public MessageRecallEventBPack(long qq, long id, long fid, int[] mid, int time, long oid) {
        super(qq, id, mid, time);
        this.fid = fid;
        this.oid = oid;
    }
}
