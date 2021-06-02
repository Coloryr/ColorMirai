package coloryr.colormirai.demo.RobotSDK.pack.to;

/*
35 [机器人]成成员被邀请加入群（事件）
id:群号
fid:进群人QQ号
name:进群人昵称
ifid:邀请人QQ号
iname:邀请人昵称
 */
public class InviteMemberJoinEventPack extends MemberJoinEventPack {
    public long ifid;
    public String iname;

    public InviteMemberJoinEventPack(long qq, long id, long fid, String name, long ifid, String iname) {
        super(qq, id, fid, name);
        this.ifid = ifid;
        this.iname = iname;
    }
}
