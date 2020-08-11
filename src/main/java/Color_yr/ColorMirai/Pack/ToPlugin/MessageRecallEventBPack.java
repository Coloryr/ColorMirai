package Color_yr.ColorMirai.Pack.ToPlugin;

/*
45 [机器人]群消息撤回事件（事件）
id：群号
mid：消息ID
time：时间
fid：群员QQ号
fname：群员群名片
 */
public class MessageRecallEventBPack extends MessageRecallEventAPack {
    public long fid;
    public long oid;
    public String oname;

    public MessageRecallEventBPack(long id, long fid, int mid, int time,long oid, String oname) {
        super(id, mid, time);
        this.fid = fid;
        this.oid = oid;
        this.oname = oname;
    }
}
