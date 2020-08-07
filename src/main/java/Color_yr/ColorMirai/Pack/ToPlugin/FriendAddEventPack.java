package Color_yr.ColorMirai.Pack.ToPlugin;

/*
18 [机器人]成功添加了一个新好友（事件）
name：好友nick
id：好友QQ号
 */
public class FriendAddEventPack {
    private String name;
    private long id;

    public FriendAddEventPack(String name, long id) {
        this.id = id;
        this.name = name;
    }

    public FriendAddEventPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
