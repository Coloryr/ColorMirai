package Color_yr.ColorMirai.Pack.ToPlugin;

/*
37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
eventid：事件ID
id：群号
fid：进群人
message：入群消息
 */
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
