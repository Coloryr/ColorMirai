package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.Set;

/**
 * 63 [插件]发送图片到朋友
 */
public class SendFriendImagePack extends PackBase {
    /**
     * QQ号
     */
    public long id;
    /**
     * 图片数据
     */
    public byte[] data;
    /**
     * QQ号组
     */
    public Set<Long> ids;
}
