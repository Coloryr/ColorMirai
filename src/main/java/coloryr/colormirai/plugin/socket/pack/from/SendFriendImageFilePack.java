package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

import java.util.List;

/*
77 [插件]从本地文件加载图片发送到朋友
id:朋友QQ号
file:文件路径
ids:好友组
*/
public class SendFriendImageFilePack extends PackBase {
    public long id;
    public String file;
    public List<Long> ids;
}
