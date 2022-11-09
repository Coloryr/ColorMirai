package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.Set;

/**
 * 75 [插件]从本地文件加载图片发送到群
 */
public class SendGroupImageFilePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 文件路径
     */
    public String file;
    /**
     * 群号组
     */
    public Set<Long> ids;
}
