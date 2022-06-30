package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.enums.MemberPermission;
import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 41 [机器人]成员权限改变（事件）
 */
public class MemberPermissionChangeEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 执行人QQ号
     */
    public long fid;
    /**
     * 旧的状态
     */
    public MemberPermission old;
    /**
     * 新的状态
     */
    public MemberPermission now;
}
