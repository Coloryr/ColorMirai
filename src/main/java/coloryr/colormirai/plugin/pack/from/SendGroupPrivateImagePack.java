package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/*
62 [插件]发送图片到私聊
id: 群号
fid: QQ号
data: 图片数据
 */
public class SendGroupPrivateImagePack extends PackBase {
    public long id;
    public long fid;
    public byte[] data;
}
