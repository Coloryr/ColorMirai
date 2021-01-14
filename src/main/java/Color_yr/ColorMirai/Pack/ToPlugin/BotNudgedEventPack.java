package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
82 [机器人]机器人戳一戳（事件）
id: QQ群
fid：QQ号
ation: 戳一戳的动作名称
suffix: 戳一戳中设置的自定义后缀
 */
public class BotNudgedEventPack extends PackBase {
    public long id;
    public long fid;
    public String action;
    public String suffix;

    public BotNudgedEventPack(long qq, long id, long fid, String action, String suffix) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.action = action;
        this.suffix = suffix;
    }
}
