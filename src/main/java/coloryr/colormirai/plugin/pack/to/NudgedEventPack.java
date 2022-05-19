package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 81 [机器人]群成员戳一戳（事件）
 * 82 [机器人]机器人被戳一戳（事件）
 */
public class NudgedEventPack extends PackBase {
    /**
     * QQ群
     */
    public long id;
    /**
     * 被戳成员QQ号
     */
    public long fid;
    /**
     * 发起戳一戳QQ号
     */
    public long aid;
    /**
     * 戳一戳的动作名称
     */
    public String action;
    /**
     * 戳一戳中设置的自定义后缀
     */
    public String suffix;

    public NudgedEventPack(long qq, long id, long fid, long aid, String action, String suffix) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
        this.action = action;
        this.suffix = suffix;
        this.aid = aid;
    }
}
