package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

/*
81 [机器人]群成员戳一戳（事件）
82 [机器人]机器人被戳一戳（事件）
id: QQ群
aid:发起戳一戳QQ号
fid: 被戳成员QQ号
action: 戳一戳的动作名称
suffix: 戳一戳中设置的自定义后缀
 */
public class NudgedEventPack extends PackBase {
    public long id;
    public long fid;
    public long aid;
    public String action;
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
