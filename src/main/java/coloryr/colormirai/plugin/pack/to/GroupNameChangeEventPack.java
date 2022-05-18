package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 31 [机器人]群名改变（事件）
 */
public class GroupNameChangeEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 执行人QQ号
     */
    public long fid;
    /**
     * 旧的名字
     */
    public String old;
    /**
     * 新的名字
     */
    public String now;

    public GroupNameChangeEventPack(long qq, long id, long fid, String old, String now) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.now = now;
        this.old = old;
    }
}
