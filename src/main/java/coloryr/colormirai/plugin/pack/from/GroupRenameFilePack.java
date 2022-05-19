package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 103 [插件]重命名群文件
 */
public class GroupRenameFilePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 旧群文件ID
     */
    public String fid;
    /**
     * 新群文件名
     */
    public String now;
}
