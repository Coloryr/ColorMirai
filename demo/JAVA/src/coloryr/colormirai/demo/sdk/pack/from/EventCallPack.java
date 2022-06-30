package coloryr.colormirai.demo.sdk.pack.from;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 59 [插件]回应事件
 */
public class EventCallPack extends PackBase {
    /**
     * 事件ID
     */
    public long eventid;
    /**
     * 方法
     */
    public int dofun;
    /**
     * 附带参数
     */
    public List<String> arg;
}
