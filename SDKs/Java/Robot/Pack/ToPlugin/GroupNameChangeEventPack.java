package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

/*
31 [机器人]群名改变（事件）
id：群号
fid：执行人QQ号
old：旧的名字
now：新的名字
 */
public class GroupNameChangeEventPack extends PackBase {
    public long id;
    public long fid;
    public String old;
    public String now;

    public GroupNameChangeEventPack(long qq, long id, long fid, String old, String now) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.now = now;
        this.old = old;
    }
}
