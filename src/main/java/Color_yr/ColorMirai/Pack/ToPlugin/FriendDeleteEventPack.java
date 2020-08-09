package Color_yr.ColorMirai.Pack.ToPlugin;

/*
20 [机器人]好友已被删除（事件）
name：好友nick
id：好友QQ号
 */
public class FriendDeleteEventPack {
    public String name;
    public long id;

    public FriendDeleteEventPack(String name, long id) {
        this.id = id;
        this.name = name;
    }
}
