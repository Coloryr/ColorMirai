package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 38 [机器人]成员被踢出群（事件）
 */
public class MemberLeaveEventAPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 被踢出人QQ号
     */
    public long fid;
    /**
     * 执行人QQ号
     */
    public long eid;
}
