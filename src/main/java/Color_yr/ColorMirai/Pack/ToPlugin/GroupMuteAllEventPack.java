package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
30 [机器人]群 "全员禁言" 功能状态改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class GroupMuteAllEventPack extends PackBase {
    public long id;
    public long fid;
    public boolean old;
    public boolean new_;

    public GroupMuteAllEventPack(long qq, long id, long fid, boolean old, boolean new_) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.new_ = new_;
        this.old = old;
    }
}
