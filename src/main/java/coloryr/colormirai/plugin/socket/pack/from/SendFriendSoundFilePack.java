package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

import java.util.List;

/*
112 [插件]发送好友语言文件
id:好友QQ号
file:文件路径
ids:好友组
 */
public class SendFriendSoundFilePack extends PackBase {
    public long id;
    public String file;
    public List<Long> ids;
}
