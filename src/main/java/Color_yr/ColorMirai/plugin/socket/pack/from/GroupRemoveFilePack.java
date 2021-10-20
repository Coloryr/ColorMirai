package Color_yr.ColorMirai.plugin.socket.pack.from;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
103 [插件]重命名群文件
id:群号
fid:旧群文件ID
now:新群文件名
 */
public class GroupRemoveFilePack extends PackBase {
    public long id;
    public String fid;
    public String now;
}
