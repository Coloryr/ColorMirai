package coloryr.colormirai.demo.sdk.pack.from;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
98 [插件]发送群私聊骰子
id:群号
fid:群员QQ号
dice:点数
 */
public class SendGroupPrivateDicePack extends PackBase {
    public long id;
    public long fid;
    public int dice;
}