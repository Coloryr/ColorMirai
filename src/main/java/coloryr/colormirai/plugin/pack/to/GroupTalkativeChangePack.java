package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

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

    public GroupTalkativeChangePack(long qq, long id, long now, long old) {
        this.qq = qq;
        this.id = id;
        this.now = now;
        this.old = old;
    }
}
