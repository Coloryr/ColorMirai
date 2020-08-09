package Color_yr.ColorMirai.Pack.ToPlugin;

/*
3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
name：权限名
id：群号
 */
public class BotGroupPermissionChangePack {
    public String name;
    public long id;

    public BotGroupPermissionChangePack(String name, long id) {
        this.id = id;
        this.name = name;
    }
}
