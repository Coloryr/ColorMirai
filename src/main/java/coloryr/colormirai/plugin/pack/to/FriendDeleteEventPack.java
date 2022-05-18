package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 20 [机器人]好友已被删除（事件）
 */
public class FriendDeleteEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;

    public FriendDeleteEventPack(long qq, long id) {
        this.id = id;
        this.qq = qq;
    }
}
