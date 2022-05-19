package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/*
 * 40 [机器人]群成员被禁言（事件）
 */
public class MemberMuteEventPack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 被禁言的QQ号
     */
    public long fid;
    /*
     * 执行禁言的QQ号
     */
    public long eid;
    /*
     * 时间
     */
    public int time;

    public MemberMuteEventPack(long qq, long id, long fid, long eid, int time) {
        this.eid = eid;
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.time = time;
    }
}
