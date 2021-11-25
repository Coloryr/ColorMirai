package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
39 [机器人]成员主动离开（事件）
id:群号
fid:离开人QQ号
 */
public class MemberLeaveEventBPack extends PackBase {
    public long id;
    public long fid;

    public MemberLeaveEventBPack(long qq, long id, long fid) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
    }
}
