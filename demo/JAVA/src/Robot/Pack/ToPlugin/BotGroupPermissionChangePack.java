package Robot.Pack.ToPlugin;

import Robot.Pack.MemberPermission;
import Robot.Pack.PackBase;

/*
3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
now:当前权限
old:旧的权限
id:群号
 */
public class BotGroupPermissionChangePack extends PackBase {
    public MemberPermission now;
    public MemberPermission old;
    public long id;
}
