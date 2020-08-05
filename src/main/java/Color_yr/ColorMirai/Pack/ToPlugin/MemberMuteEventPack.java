package Color_yr.ColorMirai.Pack.ToPlugin;

public class MemberMuteEventPack {
    private long id;
    private long fid;
    private long eid;

    public MemberMuteEventPack(long id, long fid, long eid) {
        this.eid = eid;
        this.fid = fid;
        this.id = id;
    }

    public MemberMuteEventPack() {
    }

    public long getEid() {
        return eid;
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }
}
