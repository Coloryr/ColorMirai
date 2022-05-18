package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 18 [机器人]成功添加了一个新好友（事件）
 */
public class FriendAddEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 昵称
     */
    public String nick;

    public FriendAddEventPack(long qq, long id, String nick) {
        this.id = id;
        this.qq = qq;
        this.nick = nick;
    }
}
