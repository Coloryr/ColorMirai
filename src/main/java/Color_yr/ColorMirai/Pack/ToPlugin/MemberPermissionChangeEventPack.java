package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.contact.MemberPermission;

/*
41 [机器人]成员权限改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
now：新的状态
 */
public class MemberPermissionChangeEventPack extends PackBase {
    public long id;
    public long fid;
    public MemberPermission old;
    public MemberPermission now;

    public MemberPermissionChangeEventPack(long qq, long id, long fid, MemberPermission old, MemberPermission now) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.now = now;
    }
}
