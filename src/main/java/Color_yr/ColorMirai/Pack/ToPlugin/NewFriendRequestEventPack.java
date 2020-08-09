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
    public long id;
    public long fid;
    public String name;
    public String message;
    public long eventid;

    public NewFriendRequestEventPack(long id, long fid, String name, String message, long eventid) {
        this.eventid = eventid;
        this.fid = fid;
        this.id = id;
        this.message = message;
        this.name = name;
    }
}
