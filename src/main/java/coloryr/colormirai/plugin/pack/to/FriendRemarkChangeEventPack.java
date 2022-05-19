package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 23 [机器人]好友昵称改变（事件）
 */
public class FriendRemarkChangeEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 旧的昵称
     */
    public String old;
    /**
     * 新的昵称
     */
    public String now;

    public FriendRemarkChangeEventPack(long qq, long id, String old, String name) {
        this.id = id;
        this.qq = qq;
        this.old = old;
        this.now = name;
    }
}
