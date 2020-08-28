package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
18 [机器人]成功添加了一个新好友（事件）
name：好友nick
id：好友QQ号
 */
public class FriendAddEventPack extends PackBase {
    public String name;
    public long id;

    public FriendAddEventPack(long qq, String name, long id) {
        this.id = id;
        this.name = name;
        this.qq = qq;
    }
}
