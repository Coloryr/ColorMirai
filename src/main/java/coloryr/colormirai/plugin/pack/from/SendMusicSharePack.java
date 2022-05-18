package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/*
93 [插件]发送音乐分享
id:发送目标
fid:发送目标
type:音乐类型
type1:目标类型
title:标题
summary:概要
jumpUrl:跳转Url
pictureUrl:图片Url
musicUrl:音乐Url
 */
public class SendMusicSharePack extends PackBase {
    public long id;
    public long fid;
    public int type;
    public int type1;
    public String title;
    public String summary;
    public String jumpUrl;
    public String pictureUrl;
    public String musicUrl;
}
