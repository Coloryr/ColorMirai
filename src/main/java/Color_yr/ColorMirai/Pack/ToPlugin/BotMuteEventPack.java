package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
9 [机器人]被禁言（事件）
id：群号
name：执行人nick
fid：执行人QQ号
time：禁言时间
 */
public class BotMuteEventPack extends PackBase {
    public long id;
    public String name;
    public long fid;
    public int time;

    public BotMuteEventPack(long qq, String name, long id, long fid, int time) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.name = name;
        this.time = time;
    }
}
