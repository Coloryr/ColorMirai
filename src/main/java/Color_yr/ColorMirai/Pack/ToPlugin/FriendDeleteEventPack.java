package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
20 [机器人]好友已被删除（事件）
name：好友nick
id：好友QQ号
 */
public class FriendDeleteEventPack extends PackBase {
    public String name;
    public long id;

    public FriendDeleteEventPack(long qq, String name, long id) {
        this.id = id;
        this.qq = qq;
        this.name = name;
    }
}
