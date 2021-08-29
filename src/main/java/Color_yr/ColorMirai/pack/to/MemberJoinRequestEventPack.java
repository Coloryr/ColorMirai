package Color_yr.ColorMirai.pack.to;

import Color_yr.ColorMirai.pack.PackBase;

/*
37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
eventid:事件ID
id:群号
fid:进群人
message:入群消息
qif:邀请人ID
 */
public class MemberJoinRequestEventPack extends PackBase {
    public long eventid;
    public long id;
    public long fid;
    public long qif;
    public String message;

    public MemberJoinRequestEventPack(long qq, long id, long fid, String message, long eventid, long qif) {
        this.eventid = eventid;
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.qif = qif;
        this.message = message;
    }
}
