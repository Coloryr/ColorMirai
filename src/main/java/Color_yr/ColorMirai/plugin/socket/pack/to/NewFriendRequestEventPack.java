package Color_yr.ColorMirai.plugin.socket.pack.to;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
46 [机器人]一个账号请求添加机器人为好友（事件）
id:群号
fid:请求人QQ号
message:请求消息
eventid:事件ID
 */
public class NewFriendRequestEventPack extends PackBase {
    public long id;
    public long fid;
    public String message;
    public long eventid;

    public NewFriendRequestEventPack(long qq, long id, long fid, String message, long eventid) {
        this.eventid = eventid;
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.message = message;
    }
}
