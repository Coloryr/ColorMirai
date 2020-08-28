package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
name：权限名
id：群号
 */
public class BotGroupPermissionChangePack extends PackBase {
    public String name;
    public long id;

    public BotGroupPermissionChangePack(long qq, String name, long id) {
        this.qq = qq;
        this.id = id;
        this.name = name;
    }
}
