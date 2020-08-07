package Color_yr.ColorMirai.Pack.ToPlugin;

/*
30 [机器人]群 "全员禁言" 功能状态改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class GroupMuteAllEventPack {
    private long id;
    private long fid;
    private boolean old;
    private boolean new_;

    public GroupMuteAllEventPack(long id, long fid, boolean old, boolean new_) {
        this.fid = fid;
        this.id = id;
        this.new_ = new_;
        this.old = old;
    }

    public GroupMuteAllEventPack() {
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

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public boolean isNew_() {
        return new_;
    }

    public void setNew_(boolean new_) {
        this.new_ = new_;
    }
}
