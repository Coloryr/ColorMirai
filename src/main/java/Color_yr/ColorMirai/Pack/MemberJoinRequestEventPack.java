package Color_yr.ColorMirai.Pack;

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

    public long getFid() {
        return fid;
    }

    public String getMessage() {
        return message;
    }

    public long getEventid() {
        return eventid;
    }
}
