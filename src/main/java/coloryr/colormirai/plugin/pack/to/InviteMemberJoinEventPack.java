package coloryr.colormirai.plugin.pack.to;

/*
 * 35 [机器人]成成员被邀请加入群（事件）
 */
public class InviteMemberJoinEventPack extends MemberJoinEventPack {
    /*
     * 邀请人QQ号
     */
    public long ifid;
    /*
     * 邀请人昵称
     */
    public String iname;

    public InviteMemberJoinEventPack(long qq, long id, long fid, String name, long ifid, String iname) {
        super(qq, id, fid, name);
        this.ifid = ifid;
        this.iname = iname;
    }
}
