package Color_yr.ColorMirai.Pack;

public class GroupMuteAllEventPack {
    private long id;
    private long fid;
    private boolean old;
    private boolean new_;

    public GroupMuteAllEventPack(long id, long fid, boolean old, boolean new_) {
        this.fid = fid;
        this.id = id;
        this.new_ = new_;
        this.old = old;
    }

    public GroupMuteAllEventPack() {
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }

    public boolean isOld() {
        return old;
    }

    public boolean isNew_() {
        return new_;
    }
}
