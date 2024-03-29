package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.Set;

/**
 * 93 [插件]发送音乐分享
 */
public class SendMusicSharePack extends PackBase {
    /**
     * 发送目标
     */
    public long id;
    /**
     * 发送目标
     */
    public long fid;
    /**
     * 音乐类型
     */
    public int type;
    /**
     * 目标类型
     */
    public int type1;
    /**
     * 标题
     */
    public String title;
    /**
     * 概要
     */
    public String summary;
    /**
     * 跳转Url
     */
    public String jumpUrl;
    /**
     * 图片Url
     */
    public String pictureUrl;
    /**
     * 音乐Url
     */
    public String musicUrl;
    /**
     * 号码组
     */
    public Set<Long> ids;
}
