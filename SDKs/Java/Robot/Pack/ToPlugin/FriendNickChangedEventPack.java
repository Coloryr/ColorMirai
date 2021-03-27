package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

/*
73 [机器人]好友昵称改变（事件）
id:好友QQ号
old:旧的昵称
now:新的昵称
 */
public class FriendNickChangedEventPack extends PackBase {
    public long id;
    public String old;
    public String now;

    public FriendNickChangedEventPack(long qq, long id, String old, String now) {
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.now = now;
    }
}
