package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/*
 * 39 [机器人]成员主动离开（事件）
 */
public class MemberLeaveEventBPack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 离开人QQ号
     */
    public long fid;

    public MemberLeaveEventBPack(long qq, long id, long fid) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
    }
}
