package Color_yr.ColorMirai.Pack.ToPlugin;

/*
72 [机器人]友输入状态改变（事件）
id：好友QQ号
name：好友昵称
input：输入状态
 */
public class FriendInputStatusChangedEventPack {
    public long id;
    public String name;
    public boolean input;

    public FriendInputStatusChangedEventPack(long id, String name, boolean input) {
        this.id = id;
        this.name = name;
        this.input = input;
    }
}
