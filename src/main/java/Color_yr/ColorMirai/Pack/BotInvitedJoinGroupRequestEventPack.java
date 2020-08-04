package Color_yr.ColorMirai.Pack;

public class BotInvitedJoinGroupRequestEventPack {
    private long eventid;
    private long id;
    private String name;
    private long fid;

    public BotInvitedJoinGroupRequestEventPack(String name, long id, long fid, long eventid) {
        this.fid = fid;
        this.id = id;
        this.name = name;
        this.eventid = eventid;
    }

    public BotInvitedJoinGroupRequestEventPack() {
    }

    public long getEventid() {
        return eventid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getFid() {
        return fid;
    }
}
