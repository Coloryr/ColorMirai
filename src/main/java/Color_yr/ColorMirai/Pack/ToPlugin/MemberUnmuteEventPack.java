package Color_yr.ColorMirai.Pack.ToPlugin;

/*
43 [机器人]群成员被取消禁言（事件）
id：群号
fid：被执行人QQ号
eid：执行人QQ号
fname：被执行人群名片
ename：执行人群名片
 */
public class MemberUnmuteEventPack {
    public long id;
    public long fid;
    public long eid;
    public String fname;
    public String ename;

    public MemberUnmuteEventPack(long id, long fid, long eid, String fname, String ename) {
        this.fid = fid;
        this.id = id;
        this.fname = fname;
        this.eid = eid;
        this.ename = ename;
    }
}
