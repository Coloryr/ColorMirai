package Color_yr.ColorMirai.Pack.ToPlugin;

public class MemberLeaveEventBPack {
    private long id;
    private long fid;

    public MemberLeaveEventBPack(long id, long fid) {
        this.id = id;
        this.fid = fid;
    }

    public MemberLeaveEventBPack() {
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
}
