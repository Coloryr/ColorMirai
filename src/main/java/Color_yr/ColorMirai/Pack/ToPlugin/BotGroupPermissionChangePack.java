package Color_yr.ColorMirai.Pack.ToPlugin;

/*
3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
name：权限名
id：群号
 */
public class BotGroupPermissionChangePack {
    private String name;
    private long id;

    public BotGroupPermissionChangePack(String name, long id) {
        this.id = id;
        this.name = name;
    }

    public BotGroupPermissionChangePack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
