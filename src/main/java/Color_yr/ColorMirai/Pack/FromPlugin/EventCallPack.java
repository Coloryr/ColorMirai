package Color_yr.ColorMirai.Pack.FromPlugin;

import java.util.List;

/*
59 [插件]回应事件
eventid：事件ID
dofun：方法
arg：附带参数
 */
public class EventCallPack {
    private long eventid;
    private int dofun;
    private List<Object> arg;

    public long getEventid() {
        return eventid;
    }

    public void setEventid(long eventid) {
        this.eventid = eventid;
    }

    public int getDofun() {
        return dofun;
    }

    public void setDofun(int dofun) {
        this.dofun = dofun;
    }

    public List<Object> getArg() {
        return arg;
    }

    public void setArg(List<Object> arg) {
        this.arg = arg;
    }
}
