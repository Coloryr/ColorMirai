package Color_yr.ColorMirai.Pack.ToPlugin;

public class GroupAllowAnonymousChatEventPack {
    private long id;
    private long fid;
    private boolean old;
    private boolean new_;

    public GroupAllowAnonymousChatEventPack(long id, long fid, boolean old, boolean new_) {
        this.fid = fid;
        this.id = id;
        this.old = old;
        this.new_ = new_;
    }

    public GroupAllowAnonymousChatEventPack() {
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }

    public boolean isNew_() {
        return new_;
    }

    public boolean isOld() {
        return old;
    }
}
