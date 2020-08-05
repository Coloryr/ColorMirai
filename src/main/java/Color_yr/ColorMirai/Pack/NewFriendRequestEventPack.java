package Color_yr.ColorMirai.Pack;

public class NewFriendRequestEventPack {
    private long id;
    private long fid;
    private String name;
    private String message;
    private long eventid;

    public NewFriendRequestEventPack(long id, long fid, String name, String message, long eventid) {
        this.eventid = eventid;
        this.fid = fid;
        this.id = id;
        this.message = message;
        this.name = name;
    }

    public NewFriendRequestEventPack() {
    }

    public long getFid() {
        return fid;
    }

    public long getId() {
        return id;
    }

    public long getEventid() {
        return eventid;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }
}
