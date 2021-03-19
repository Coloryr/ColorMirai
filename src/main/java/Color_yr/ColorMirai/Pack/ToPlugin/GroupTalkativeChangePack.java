package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
/*
98 [机器人]龙王改变时（事件）
id：群号
fid：当前龙王
old：先前龙王
 */
public class GroupTalkativeChangePack extends PackBase {
    public long id;
    public long fid;
    public long old;

    public GroupTalkativeChangePack(long qq, long id, long fid, long old) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
        this.old = old;
    }
}
