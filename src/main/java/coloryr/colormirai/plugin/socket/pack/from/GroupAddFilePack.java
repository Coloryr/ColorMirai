package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 99 [插件]上传群文件
 */
public class GroupAddFilePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 系统文件路径
     */
    public String file;
    /**
     * 群文件名称
     */
    public String name;
}
