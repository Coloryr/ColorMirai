package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/*
 * 76 [插件]从本地文件加载图片发送到群私聊
 */
public class SendGroupPrivateImageFilePack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 群员QQ号
     */
    public long fid;
    /*
     * 文件路径
     */
    public String file;
}
