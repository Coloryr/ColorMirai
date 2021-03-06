package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
23 [机器人]好友昵称改变（事件）
id：好友QQ号
old: 旧的昵称
name：新的昵称
 */
public class FriendRemarkChangeEventPack extends PackBase {
    public long id;
    public String name;
    public String old;

    public FriendRemarkChangeEventPack(long qq, long id, String old, String name) {
        this.id = id;
        this.qq = qq;
        this.old = old;
        this.name = name;
    }
}
