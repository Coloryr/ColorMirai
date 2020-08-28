package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
39 [机器人]成员主动离开（事件）
id：群号
fid：离开人QQ号
name：离开人群名片
 */
public class MemberLeaveEventBPack extends PackBase {
    public long id;
    public long fid;
    public String name;

    public MemberLeaveEventBPack(long qq, long id, long fid, String name) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
    }
}
