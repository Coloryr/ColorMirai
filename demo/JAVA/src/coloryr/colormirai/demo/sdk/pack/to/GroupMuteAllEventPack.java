package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 30 [机器人]群 "全员禁言" 功能状态改变（事件）
 */
public class GroupMuteAllEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 执行人QQ号
     */
    public long fid;
    /**
     * 旧的状态
     */
    public boolean old;
    /**
     * 新的状态
     */
    public boolean now;
}
