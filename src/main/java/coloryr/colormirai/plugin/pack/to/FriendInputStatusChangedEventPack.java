package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 72 [机器人]友输入状态改变（事件）
 */
public class FriendInputStatusChangedEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 输入状态
     */
    public boolean input;

    public FriendInputStatusChangedEventPack(long qq, long id, boolean input) {
        this.id = id;
        this.qq = qq;
        this.input = input;
    }
}
