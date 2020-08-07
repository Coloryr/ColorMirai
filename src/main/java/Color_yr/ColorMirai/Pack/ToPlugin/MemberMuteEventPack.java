package Color_yr.ColorMirai.Pack.ToPlugin;

/*
40 [机器人]群成员被禁言（事件）
id：群号
fid：被禁言的QQ号
eid：执行禁言的QQ号
fname：被禁言的群名片
ename：执行禁言的群名片
time：时间
 */
public class MemberMuteEventPack {
    private long id;
    private long fid;
    private long eid;
    private String fname;
    private String ename;
    private int time;

    public MemberMuteEventPack(long id, long fid, long eid, String fname, String ename, int time) {
        this.eid = eid;
        this.fid = fid;
        this.id = id;
        this.ename = ename;
        this.fname = fname;
        this.time = time;
    }

    public MemberMuteEventPack() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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
