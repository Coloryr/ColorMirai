package Color_yr.ColorMirai.Pack.ToPlugin;

public class GroupEntranceAnnouncementChangeEventPack {
    private long id;
    private long fid;
    private String old;
    private String new_;

    public GroupEntranceAnnouncementChangeEventPack(long id, long fid, String old, String new_) {
        this.id = id;
        this.old = old;
        this.new_ = new_;
        this.fid = fid;
    }

    public GroupEntranceAnnouncementChangeEventPack() {
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }

    public String getNew_() {
        return new_;
    }

    public String getOld() {
        return old;
    }
}
