package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
19 [机器人]头像被修改（事件）
id：好友QQ号
name：好友nick
url：图片url
 */
public class FriendAvatarChangedEventPack extends PackBase {
    public String name;
    public String url;
    public long id;

    public FriendAvatarChangedEventPack(long qq, String name, long id, String url) {
        this.id = id;
        this.qq = qq;
        this.name = name;
        this.url = url;
    }
}
