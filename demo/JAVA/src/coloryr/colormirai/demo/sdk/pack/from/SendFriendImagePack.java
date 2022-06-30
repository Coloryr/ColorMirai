package coloryr.colormirai.demo.sdk.pack.from;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

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
    public List<Long> ids;
}
