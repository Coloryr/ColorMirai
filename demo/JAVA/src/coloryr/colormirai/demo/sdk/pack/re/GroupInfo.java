package coloryr.colormirai.demo.sdk.pack.re;

import coloryr.colormirai.demo.sdk.enums.MemberPermission;

/**
 * 群信息包
 */
public class GroupInfo {
    /**
     * 群号
     */
    public long id;
    /**
     * 群名
     */
    public String name;
    /**
     * 群头像
     */
    public String img;
    /**
     * 所有者QQ号
     */
    public long oid;
    /**
     * 机器人所拥有的权限
     */
    public MemberPermission per;
}
