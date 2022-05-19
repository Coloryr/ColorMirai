package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 108 [插件]设置取消管理员
 */
public class GroupSetAdminPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 群员
     */
    public long fid;
    /**
     * 设置或取消
     */
    public boolean type;
}
