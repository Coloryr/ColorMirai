package coloryr.colormirai.demo.sdk.pack.from;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

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
    public List<Long> ids;
}
