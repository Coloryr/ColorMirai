package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 27 [机器人]入群公告改变（事件）
 */
public class GroupEntranceAnnouncementChangeEventPack extends PackBase {
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
    public String old;
    /**
     * 新的状态
     */
    public String now;

    public GroupEntranceAnnouncementChangeEventPack(long qq, long id, long fid, String old, String now) {
        this.id = id;
        this.qq = qq;
        this.old = old;
        this.now = now;
        this.fid = fid;
    }
}
