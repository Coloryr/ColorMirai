package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

import java.util.List;

/*
 * 77 [插件]从本地文件加载图片发送到朋友
 */
public class SendFriendImageFilePack extends PackBase {
    /*
     * QQ号
     */
    public long id;
    /*
     * 文件路径
     */
    public String file;
    /*
     * QQ号组
     */
    public List<Long> ids;
}
