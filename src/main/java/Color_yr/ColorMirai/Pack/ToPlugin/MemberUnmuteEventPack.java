package Color_yr.ColorMirai.Pack.ToPlugin;

public class MemberUnmuteEventPack {
    private long id;
    private long fid;

    public MemberUnmuteEventPack(long id, long fid) {
        this.fid = fid;
        this.id = id;
    }

    public MemberUnmuteEventPack() {
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
