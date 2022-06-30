package coloryr.colormirai.demo.sdk.pack.from;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 74 [插件]发送语音到群
 */
public class SendGroupSoundPack extends PackBase {
    /**
     * QQ号
     */
    public long id;
    /**
     * 文件内容
     */
    public byte[] data;
    /**
     * QQ号组
     */
    public List<Long> ids;
}
