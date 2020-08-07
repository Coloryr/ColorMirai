package Color_yr.ColorMirai.Pack.ToPlugin;

/*
4 [机器人]被邀请加入一个群（事件）
eventid：事件id
id：群号
name：邀请人nick
fid：邀请人QQ号
 */
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

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}
