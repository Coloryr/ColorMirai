package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 4 [机器人]被邀请加入一个群（事件）
 */
public class BotInvitedJoinGroupRequestEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 邀请人QQ号
     */
    public long fid;
    /**
     * 事件ID
     */
    public long eventid;
}
