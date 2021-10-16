package Color_yr.ColorMirai.plugin.socket.pack.to;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
18 [机器人]成功添加了一个新好友（事件）
id:好友QQ号
 */
public class FriendAddEventPack extends PackBase {
    public long id;

    public FriendAddEventPack(long qq, long id) {
        this.id = id;
        this.qq = qq;
    }
}
