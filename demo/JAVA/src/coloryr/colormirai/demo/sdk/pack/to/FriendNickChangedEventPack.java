package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

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
}
