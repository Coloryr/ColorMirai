package Color_yr.ColorMirai.Pack.ToPlugin;

/*
26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class GroupAllowMemberInviteEventPack {
    public long id;
    public long fid;
    public boolean old;
    public boolean new_;

    public GroupAllowMemberInviteEventPack(long id, long fid, boolean old, boolean new_) {
        this.fid = fid;
        this.id = id;
        this.old = old;
        this.new_ = new_;
    }
}
