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
    private long id;
    private long fid;
    private long eid;
    private String fname;
    private String ename;

    public MemberUnmuteEventPack(long id, long fid, long eid, String fname, String ename) {
        this.fid = fid;
        this.id = id;
        this.fname = fname;
        this.eid = eid;
        this.ename = ename;
    }

    public MemberUnmuteEventPack() {
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

    public long getEid() {
        return eid;
    }

    public void setEid(long eid) {
        this.eid = eid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}
