package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 23 [机器人]好友昵称改变（事件）
 */
public class FriendRemarkChangeEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 新的昵称
     */
    public String now;
    /**
     * 旧的昵称
     */
    public String old;

    public FriendRemarkChangeEventPack(long qq, long id, String old, String name) {
        this.id = id;
        this.qq = qq;
        this.old = old;
        this.now = name;
    }
}
