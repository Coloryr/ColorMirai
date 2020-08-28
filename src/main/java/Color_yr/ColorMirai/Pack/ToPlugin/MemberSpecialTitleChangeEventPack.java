package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
42 [机器人]成员群头衔改动（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class MemberSpecialTitleChangeEventPack extends PackBase {
    public long id;
    public long fid;
    public String old;
    public String new_;

    public MemberSpecialTitleChangeEventPack(long qq, long id, long fid, String old, String new_) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.new_ = new_;
    }
}
