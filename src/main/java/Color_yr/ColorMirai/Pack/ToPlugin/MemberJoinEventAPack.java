package Color_yr.ColorMirai.Pack.ToPlugin;

public class MemberJoinEventAPack {
    private long id;
    private long fid;

    public MemberJoinEventAPack(long id, long fid) {
        this.fid = fid;
        this.id = id;
    }

    public MemberJoinEventAPack() {
    }

    public long getFid() {
        return fid;
    }

    public long getId() {
        return id;
    }
}
