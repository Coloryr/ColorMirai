package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
75 [插件]从本地文件加载图片发送到群
id:群号
file:文件路径
 */
public class SendGroupImageFilePack extends PackBase {
    public String file;
    public long id;
}
