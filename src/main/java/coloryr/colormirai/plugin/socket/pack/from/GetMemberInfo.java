package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 91 [插件]获取群成员信息
 */
public class GetMemberInfo extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 群员QQ号
     */
    public long fid;
}