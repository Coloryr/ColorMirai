package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 57 [插件]获取群成员
 */
public class GroupGetMemberInfoPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 只获取关键数据
     */
    public boolean fast;
    /**
     * 请求UUID
     */
    public String uuid;
}
