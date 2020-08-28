package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
23 [机器人]好友昵称改变（事件）
id：好友QQ号
name：新的昵称
 */
public class FriendRemarkChangeEventPack extends PackBase {
    public long id;
    public String name;

    public FriendRemarkChangeEventPack(long qq, long id, String name) {
        this.id = id;
        this.qq = qq;
        this.name = name;
    }
}
