package Color_yr.ColorMirai.Pack.ToPlugin;

/*
34 [机器人]成员群名片改动（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class MemberCardChangeEventPack {
    private long id;
    private long fid;
    private String old;
    private String new_;

    public MemberCardChangeEventPack(long id, long fid, String old, String new_) {
        this.fid = fid;
        this.id = id;
        this.old = old;
        this.new_ = new_;
    }

    public MemberCardChangeEventPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNew_() {
        return new_;
    }

    public void setNew_(String new_) {
        this.new_ = new_;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}
