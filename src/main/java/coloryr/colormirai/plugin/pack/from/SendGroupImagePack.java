package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/*
61 [插件]发送图片到群
id: 群号
data: 图片数据
 */
public class SendGroupImagePack extends PackBase {
    public long id;
    public byte[] data;
}
