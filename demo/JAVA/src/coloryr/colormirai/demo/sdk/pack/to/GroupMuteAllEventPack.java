package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
30 [机器人]群 "全员禁言" 功能状态改变（事件）
id:群号
fid:执行人QQ号
old:旧的状态
now:新的状态
 */
public class GroupMuteAllEventPack extends PackBase {
    public long id;
    public long fid;
    public boolean old;
    public boolean now;

    public GroupMuteAllEventPack(long qq, long id, long fid, boolean old, boolean now) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.now = now;
        this.old = old;
    }
}
