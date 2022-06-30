package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 85 [机器人]龙王改变时（事件）
 */
public class GroupTalkativeChangePack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 先前龙王
     */
    public long old;
    /**
     * 当前龙王
     */
    public long now;
}
