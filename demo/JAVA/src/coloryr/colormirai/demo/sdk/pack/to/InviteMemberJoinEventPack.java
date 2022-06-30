package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 35 [机器人]成成员被邀请加入群（事件）
 */
public class InviteMemberJoinEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 进群人QQ号
     */
    public long fid;
    /**
     * 进群人昵称
     */
    public String name;
    /**
     * 邀请人QQ号
     */
    public long ifid;
    /**
     * 邀请人昵称
     */
    public String iname;
}
