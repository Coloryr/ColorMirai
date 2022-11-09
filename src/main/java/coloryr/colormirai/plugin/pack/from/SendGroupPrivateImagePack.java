package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.Set;

/**
 * 62 [插件]发送图片到私聊
 */
public class SendGroupPrivateImagePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * QQ号
     */
    public long fid;
    /**
     * 图片数据
     */
    public byte[] data;
    /**
     * QQ号组
     */
    public Set<Long> ids;
}
