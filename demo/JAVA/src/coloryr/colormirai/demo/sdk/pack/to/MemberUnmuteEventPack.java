package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

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
}
