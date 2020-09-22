package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
81 [机器人]群成员戳一戳（事件）
id: QQ群
fid: 成员QQ号
name: 成员昵称
action: 戳一戳的动作名称
suffix: 戳一戳中设置的自定义后缀
 */
public class MemberNudgedEventPack extends PackBase {
    public long id;
    public long fid;
    public String name;
    public String action;
    public String suffix;

    public MemberNudgedEventPack(long qq, long id, long fid, String name, String action, String suffix) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
        this.action = action;
        this.suffix = suffix;
        this.name = name;
    }
}
