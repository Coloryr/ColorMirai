package Color_yr.ColorMirai.Pack.ToPlugin;

/*
23 [机器人]好友昵称改变（事件）
id：好友QQ号
name：新的昵称
 */
public class FriendRemarkChangeEventPack {
    public long id;
    public String name;

    public FriendRemarkChangeEventPack(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
