package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 19 [机器人]好友头像修改（事件）
 */
public class FriendAvatarChangedEventPack extends PackBase {
    /**
     *      *
     */
    public String url;
    /**
     * 好友QQ号
     */
    public long id;

    public FriendAvatarChangedEventPack(long qq, long id, String url) {
        this.id = id;
        this.qq = qq;
        this.url = url;
    }
}
