package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
78 [插件]从本地文件加载语音发送到群
id:群号
file:文件路径
 */
public class SendGroupSoundFilePack extends PackBase {
    public long id;
    public String file;
}
