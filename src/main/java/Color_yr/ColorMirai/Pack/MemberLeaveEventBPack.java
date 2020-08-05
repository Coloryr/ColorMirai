package Color_yr.ColorMirai.Pack;

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

    public long getId() {
        return id;
    }
}
