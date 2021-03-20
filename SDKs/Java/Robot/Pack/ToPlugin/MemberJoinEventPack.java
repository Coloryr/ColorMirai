package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

/*
35 [机器人]成成员被邀请加入群（事件）
36 [机器人]成员主动加入群（事件）
id：群号
fid：进群人QQ号
 */
public class MemberJoinEventPack extends PackBase {
    public long id;
    public long fid;

    public MemberJoinEventPack(long qq, long id, long fid) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
    }
}
