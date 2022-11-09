package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.Set;

/**
 * 61 [插件]发送图片到群
 */
public class SendGroupImagePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 图片数据
     */
    public byte[] data;
    /**
     * 群号组
     */
    public Set<Long> ids;
}
