package coloryr.colormirai.plugin.socket.pack.re;

import coloryr.colormirai.plugin.socket.pack.PackBase;

import java.util.List;

/*
101 [插件]获取群文件
id:群号
files:文件列表
 */
public class GroupFilesPack extends PackBase {
    public long id;
    public List<GroupFileInfo> files;
}
