package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.Set;

/**
 * 77 [插件]从本地文件加载图片发送到朋友
 */
public class SendFriendImageFilePack extends PackBase {
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
    public Set<Long> ids;
}
