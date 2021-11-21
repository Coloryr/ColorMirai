package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

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

    public GroupMuteAllEventPack(long qq, long id, long fid, boolean old, boolean now) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.now = now;
        this.old = old;
    }
}
