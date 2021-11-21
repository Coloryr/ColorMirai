package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
69 [插件]设置群名片
id:群号
fid:成员QQ号
card:新的群名片
 */
public class GroupSetMemberCard extends PackBase {
    public long id;
    public long fid;
    public String card;
}
