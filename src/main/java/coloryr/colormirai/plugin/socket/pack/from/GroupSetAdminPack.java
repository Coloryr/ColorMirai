package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
108 [插件]设置取消管理员
id:群号
fid:群员
type:设置或取消
 */
public class GroupSetAdminPack extends PackBase {
    public long id;
    public long fid;
    public boolean type;
}