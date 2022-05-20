package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 79 [机器人]成员群恢复（事件）
 */
public class MemberJoinRetrieveEventPack extends PackBase {
    /**
     * QQ群
     */
    public long id;
    /**
     * 成员QQ号
     */
    public long fid;

    public MemberJoinRetrieveEventPack(long qq, long id, long fid) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
    }
}
