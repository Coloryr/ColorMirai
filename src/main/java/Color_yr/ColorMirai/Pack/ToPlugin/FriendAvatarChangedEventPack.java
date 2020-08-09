package Color_yr.ColorMirai.Pack.ToPlugin;

/*
19 [机器人]头像被修改（事件）
id：好友QQ号
name：好友nick
url：图片url
 */
public class FriendAvatarChangedEventPack {
    public String name;
    public String url;
    public long id;

    public FriendAvatarChangedEventPack(String name, long id, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
}
