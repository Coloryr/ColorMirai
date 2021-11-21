package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
40 [机器人]群成员被禁言（事件）
id:群号
fid:被禁言的QQ号
eid:执行禁言的QQ号
time:时间
 */
public class MemberMuteEventPack extends PackBase {
    public long id;
    public long fid;
    public long eid;
    public int time;

    public MemberMuteEventPack(long qq, long id, long fid, long eid, int time) {
        this.eid = eid;
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.time = time;
    }
}
