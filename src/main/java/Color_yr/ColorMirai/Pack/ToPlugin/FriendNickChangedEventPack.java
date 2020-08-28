package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
73 [机器人]好友昵称改变（事件）
id：好友QQ号
old：旧的昵称
new_：新的昵称
 */
public class FriendNickChangedEventPack extends PackBase {
    public long id;
    public String old;
    public String new_;

    public FriendNickChangedEventPack(long qq, long id, String old, String new_) {
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.new_ = new_;
    }
}
