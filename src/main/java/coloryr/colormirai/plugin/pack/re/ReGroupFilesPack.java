package coloryr.colormirai.plugin.pack.re;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/**
 * 101 [插件]获取群文件
 */
public class ReGroupFilesPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 文件列表
     */
    public List<GroupFileInfo> files;
    /**
     * 请求UUID
     */
    public String uuid;
}
