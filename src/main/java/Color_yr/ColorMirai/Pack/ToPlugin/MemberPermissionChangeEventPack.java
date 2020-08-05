package Color_yr.ColorMirai.Pack.ToPlugin;

public class MemberPermissionChangeEventPack {
    private long id;
    private long fid;
    private String old;
    private String new_;

    public MemberPermissionChangeEventPack(long id, long fid, String old, String new_)
    {
        this.fid = fid;
        this.id = id;
        this.old = old;
        this.new_ = new_;
    }

    public MemberPermissionChangeEventPack()
    {}

    public long getFid() {
        return fid;
    }

    public long getId() {
        return id;
    }

    public String getOld() {
        return old;
    }

    public String getNew_() {
        return new_;
    }
}
