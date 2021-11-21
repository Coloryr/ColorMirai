package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 26 [机器人]群 "允许群员邀请好友加群" 功能状态改变（事件）
 */
public class GroupAllowMemberInviteEventPack extends PackBase {
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

    public GroupAllowMemberInviteEventPack(long qq, long id, long fid, boolean old, boolean now) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.old = old;
        this.now = now;
    }
}
