package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;

/*
 * 98 [插件]发送群私聊骰子
 */
public class SendGroupPrivateDicePack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 群员QQ号
     */
    public long fid;
    /*
     * 点数
     */
    public int dice;
}
