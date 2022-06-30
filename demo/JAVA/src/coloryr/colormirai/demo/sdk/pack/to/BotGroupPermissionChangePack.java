package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.enums.MemberPermission;
import coloryr.colormirai.demo.sdk.pack.PackBase;

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
}
