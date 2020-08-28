package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
72 [机器人]友输入状态改变（事件）
id：好友QQ号
name：好友昵称
input：输入状态
 */
public class FriendInputStatusChangedEventPack extends PackBase {
    public long id;
    public String name;
    public boolean input;

    public FriendInputStatusChangedEventPack(long qq, long id, String name, boolean input) {
        this.id = id;
        this.qq = qq;
        this.name = name;
        this.input = input;
    }
}
