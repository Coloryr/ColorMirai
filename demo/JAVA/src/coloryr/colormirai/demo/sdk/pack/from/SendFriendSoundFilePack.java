package coloryr.colormirai.demo.sdk.pack.from;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 112 [插件]发送好友语言文件
 */
public class SendFriendSoundFilePack extends PackBase {
    /**
     * QQ号
     */
    public long id;
    /**
     * 文件路径
     */
    public String file;
    /**
     * QQ号组
     */
    public List<Long> ids;
}
