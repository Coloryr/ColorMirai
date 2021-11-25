package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
34 [机器人]成员群名片改动（事件）
id:群号
fid:执行人QQ号
old:旧的状态
now:新的状态
 */
public class MemberCardChangeEventPack extends PackBase {
    public long id;
    public long fid;
    public String old;
    public String now;

    public MemberCardChangeEventPack(long qq, long id, long fid, String old, String now) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.now = now;
    }
}
