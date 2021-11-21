package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
96 [插件]发送朋友骰子
id:好友QQ号
dice:点数
 */
public class SendFriendDicePack extends PackBase {
    public long id;
    public int dice;
}
