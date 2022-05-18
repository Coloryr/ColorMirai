package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

import java.util.List;

/*
 * 78 [插件]从本地文件加载语音发送到群
 */
public class SendGroupSoundFilePack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 文件路径
     */
    public String file;
    /*
     * 群组
     */
    public List<Long> ids;
}
