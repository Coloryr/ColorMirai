package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

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

    public InviteMemberJoinEventPack(long qq, long id, long fid, String name, long ifid, String iname) {
        this.id = id;
        this.qq= qq;
        this.fid = fid;
        this.name = name;
        this.ifid = ifid;
        this.iname = iname;
    }
}
