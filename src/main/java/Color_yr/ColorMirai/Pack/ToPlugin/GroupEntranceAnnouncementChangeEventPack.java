package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
27 [机器人]入群公告改变（事件）
id:群号
fid:执行人QQ号
old:旧的状态
now:新的状态
 */
public class GroupEntranceAnnouncementChangeEventPack extends PackBase {
    public long id;
    public long fid;
    public String old;
    public String now;

    public GroupEntranceAnnouncementChangeEventPack(long qq, long id, long fid, String old, String now) {
        this.id = id;
        this.qq = qq;
        this.old = old;
        this.now = now;
        this.fid = fid;
    }
}
