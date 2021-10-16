package Color_yr.ColorMirai.plugin.socket.pack.from;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

import java.util.List;

/*
59 [插件]回应事件
eventid:事件ID
dofun:方法
arg:附带参数
 */
public class EventCallPack extends PackBase {
    public long eventid;
    public int dofun;
    public List<Object> arg;
}
