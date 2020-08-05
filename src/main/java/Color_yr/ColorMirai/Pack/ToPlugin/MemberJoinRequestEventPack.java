package Color_yr.ColorMirai.Pack.ToPlugin;

public class MemberJoinRequestEventPack {
    private long eventid;
    private long id;
    private long fid;
    private String message;

    public MemberJoinRequestEventPack(long id, long fid, String message, long eventid) {
        this.eventid = eventid;
        this.fid = fid;
        this.id = id;
        this.message = message;
    }

    public MemberJoinRequestEventPack() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getEventid() {
        return eventid;
    }

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }
}
