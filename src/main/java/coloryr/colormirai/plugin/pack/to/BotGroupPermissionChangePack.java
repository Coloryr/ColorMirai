package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.contact.MemberPermission;

/**
 * 3 [机器人]在群里的权限被改变. 操作人一定是群主（事件）
 */
public class BotGroupPermissionChangePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 旧的权限
     */
    public MemberPermission old;
    /**
     * 当前权限
     */
    public MemberPermission now;

    public BotGroupPermissionChangePack(long qq, MemberPermission now, MemberPermission old, long id) {
        this.qq = qq;
        this.id = id;
        this.now = now;
        this.old = old;
    }
}
