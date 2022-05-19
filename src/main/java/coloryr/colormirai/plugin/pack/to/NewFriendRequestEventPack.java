package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 46 [机器人]一个账号请求添加机器人为好友（事件）
 */
public class NewFriendRequestEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 请求人QQ号
     */
    public long fid;
    /**
     * 请求消息
     */
    public String message;
    /**
     * 事件ID
     */
    public long eventid;

    public NewFriendRequestEventPack(long qq, long id, long fid, String message, long eventid) {
        this.eventid = eventid;
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.message = message;
    }
}
