package Color_yr.ColorMirai.Pack.ToPlugin;

/*
31 [机器人]群名改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class GroupNameChangeEventPack {
    private long id;
    private long fid;
    private String old;
    private String new_;

    public GroupNameChangeEventPack(long id, long fid, String old, String new_) {
        this.fid = fid;
        this.id = id;
        this.new_ = new_;
        this.old = old;
    }

    public GroupNameChangeEventPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getNew_() {
        return new_;
    }

    public void setNew_(String new_) {
        this.new_ = new_;
    }
}
