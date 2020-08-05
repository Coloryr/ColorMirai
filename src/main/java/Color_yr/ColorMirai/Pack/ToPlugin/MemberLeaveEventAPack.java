package Color_yr.ColorMirai.Pack.ToPlugin;

public class MemberLeaveEventAPack {
    private long id;
    private long fid;
    private long eid;

    public MemberLeaveEventAPack(long id, long fid, long eid) {
        this.eid = eid;
        this.id = id;
        this.fid = fid;
    }

    public MemberLeaveEventAPack() {
    }

    public long getFid() {
        return fid;
    }

    public long getId() {
        return id;
    }

    public long getEid() {
        return eid;
    }
}
