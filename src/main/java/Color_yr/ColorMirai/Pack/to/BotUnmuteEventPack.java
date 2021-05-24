package Color_yr.ColorMirai.pack.to;

import Color_yr.ColorMirai.pack.PackBase;

/*
17 [机器人]被取消禁言（事件）
id:群号
fid:执行人QQ号
 */
public class BotUnmuteEventPack extends PackBase {
    public long id;
    public long fid;

    public BotUnmuteEventPack(long qq, long id, long fid) {
        this.fid = fid;
        this.id = id;
        this.qq = qq;
    }
}
