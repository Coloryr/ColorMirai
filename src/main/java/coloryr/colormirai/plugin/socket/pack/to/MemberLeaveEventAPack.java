package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
38 [机器人]成员被踢出群（事件）
id:群号
fid:被踢出人QQ号
eid:执行人QQ号
 */
public class MemberLeaveEventAPack extends PackBase {
    public long id;
    public long fid;
    public long eid;

    public MemberLeaveEventAPack(long qq, long id, long fid, long eid) {
        this.eid = eid;
        this.id = id;
        this.qq = qq;
        this.fid = fid;
    }
}
