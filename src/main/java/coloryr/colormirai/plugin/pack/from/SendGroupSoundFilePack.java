package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/*
78 [插件]从本地文件加载语音发送到群
id:群号
file:文件路径
ids:群组
 */
public class SendGroupSoundFilePack extends PackBase {
    public long id;
    public String file;
    public List<Long> ids;
}
