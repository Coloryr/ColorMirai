package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 73 [机器人]好友昵称改变（事件）
 */
public class FriendNickChangedEventPack extends PackBase {
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

    public FriendNickChangedEventPack(long qq, long id, String old, String now) {
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.now = now;
    }
}
