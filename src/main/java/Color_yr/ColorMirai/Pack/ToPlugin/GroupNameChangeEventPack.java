package Color_yr.ColorMirai.Pack.ToPlugin;

public class GroupNameChangeEventPack {
    private long id;
    private long fid;
    private String old;
    private String new_;

    public GroupNameChangeEventPack(long id, long fid, String old, String new_) {
        this.fid = fid;
        this.id = id;
        this.new_ = new_;
        this.old = old;
    }

    public GroupNameChangeEventPack() {
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }

    public String getOld() {
        return old;
    }

    public String getNew_() {
        return new_;
    }
}
