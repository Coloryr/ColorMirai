package Color_yr.ColorMirai.Pack.ToPlugin;

/*
30 [机器人]群 "全员禁言" 功能状态改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class GroupMuteAllEventPack {
    public long id;
    public long fid;
    public boolean old;
    public boolean new_;

    public GroupMuteAllEventPack(long id, long fid, boolean old, boolean new_) {
        this.fid = fid;
        this.id = id;
        this.new_ = new_;
        this.old = old;
    }
}
