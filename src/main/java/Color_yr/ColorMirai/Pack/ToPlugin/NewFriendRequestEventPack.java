package Color_yr.ColorMirai.Pack.ToPlugin;

/*
46 [机器人]一个账号请求添加机器人为好友（事件）
id：群号
fid：请求人QQ号
name：请求人昵称
message：请求消息
eventid：时间ID
 */
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

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEventid() {
        return eventid;
    }

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
