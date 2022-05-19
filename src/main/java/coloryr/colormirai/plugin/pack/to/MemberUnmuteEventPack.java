package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 43 [机器人]群成员被取消禁言（事件）
 */
public class MemberUnmuteEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 被执行人QQ号
     */
    public long fid;
    /**
     * 执行人QQ号
     */
    public long eid;

    public MemberUnmuteEventPack(long qq, long id, long fid, long eid) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.eid = eid;
    }
}
