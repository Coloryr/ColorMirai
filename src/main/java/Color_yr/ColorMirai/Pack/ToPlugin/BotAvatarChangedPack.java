package Color_yr.ColorMirai.Pack.ToPlugin;

/*
2 [机器人]头像被修改（通过其他客户端修改了头像）（事件）
name：机器人nick
 */
public class BotAvatarChangedPack {
    public String name;

    public BotAvatarChangedPack(String name) {
        this.name = name;
    }
}
