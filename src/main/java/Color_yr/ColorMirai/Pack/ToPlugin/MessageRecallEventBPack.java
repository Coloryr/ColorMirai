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
    private long fid;
    private String fname;

    public MessageRecallEventBPack(long id, long fid, int mid, int time, String fname) {
        super(id, mid, time);
        this.fid = fid;
        this.fname = fname;
    }

    public MessageRecallEventBPack() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}
