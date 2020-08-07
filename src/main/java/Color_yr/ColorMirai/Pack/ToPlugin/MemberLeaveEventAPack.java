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
    private long id;
    private long fid;
    private long eid;
    private String fname;
    private String ename;

    public MemberLeaveEventAPack(long id, long fid, long eid, String fname, String ename) {
        this.eid = eid;
        this.id = id;
        this.fid = fid;
        this.ename = ename;
        this.fname = fname;
    }

    public MemberLeaveEventAPack() {
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEid() {
        return eid;
    }

    public void setEid(long eid) {
        this.eid = eid;
    }
}
