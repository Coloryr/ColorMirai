package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.Set;

/**
 * 126 [插件]发送好友语音
 */
public class SendFriendSoundPack extends PackBase {
    /**
     * QQ号
     */
    public long id;
    /**
     * 语音内容
     */
    public byte[] data;
    /**
     * QQ号组
     */
    public Set<Long> ids;
}
