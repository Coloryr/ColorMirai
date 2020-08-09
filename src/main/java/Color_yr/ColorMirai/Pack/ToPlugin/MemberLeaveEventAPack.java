package Color_yr.ColorMirai.Pack.ToPlugin;

/*
38 [机器人]成员被踢出群（事件）
id：群号
fid：被踢出人QQ号
eid：执行人QQ号
fname：被踢出人群名片
ename：执行人群名片
 */
public class MemberLeaveEventAPack {
    public long id;
    public long fid;
    public long eid;
    public String fname;
    public String ename;

    public MemberLeaveEventAPack(long id, long fid, long eid, String fname, String ename) {
        this.eid = eid;
        this.id = id;
        this.fid = fid;
        this.ename = ename;
        this.fname = fname;
    }
}
