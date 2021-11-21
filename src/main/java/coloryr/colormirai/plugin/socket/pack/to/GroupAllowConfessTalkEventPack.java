package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 25 [机器人]群 "坦白说" 功能状态改变（事件）
 */
public class GroupAllowConfessTalkEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 旧的状态
     */
    public boolean old;
    /**
     * 新的状态
     */
    public boolean now;

    public GroupAllowConfessTalkEventPack(long qq, long id, boolean old, boolean now) {
        this.id = id;
        this.qq = qq;
        this.old = old;
        this.now = now;
    }
}
