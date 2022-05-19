package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.contact.MemberPermission;

/*
 * 41 [机器人]成员权限改变（事件）
 */
public class MemberPermissionChangeEventPack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 执行人QQ号
     */
    public long fid;
    /*
     * 旧的状态
     */
    public MemberPermission old;
    /*
     * 新的状态
     */
    public MemberPermission now;

    public MemberPermissionChangeEventPack(long qq, long id, long fid, MemberPermission old, MemberPermission now) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.now = now;
    }
}
