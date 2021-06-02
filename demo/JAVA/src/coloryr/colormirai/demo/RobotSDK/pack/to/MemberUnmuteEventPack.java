package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;

/*
43 [机器人]群成员被取消禁言（事件）
id:群号
fid:被执行人QQ号
eid:执行人QQ号
 */
public class MemberUnmuteEventPack extends PackBase {
    public long id;
    public long fid;
    public long eid;

    public MemberUnmuteEventPack(long qq, long id, long fid, long eid) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.eid = eid;
    }
}
