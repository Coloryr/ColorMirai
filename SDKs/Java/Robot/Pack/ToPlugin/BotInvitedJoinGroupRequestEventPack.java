package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

/*
4 [机器人]被邀请加入一个群（事件）
eventid：事件ID
id：群号
fid：邀请人QQ号
 */
public class BotInvitedJoinGroupRequestEventPack extends PackBase {
    public long eventid;
    public long id;
    public long fid;

    public BotInvitedJoinGroupRequestEventPack(long qq, long id, long fid, long eventid) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.eventid = eventid;
    }
}
