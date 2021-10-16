package Color_yr.ColorMirai.plugin.socket.pack.to;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
85 [机器人]龙王改变时（事件）
id:群号
now:当前龙王
old:先前龙王
 */
public class GroupTalkativeChangePack extends PackBase {
    public long id;
    public long now;
    public long old;

    public GroupTalkativeChangePack(long qq, long id, long now, long old) {
        this.qq = qq;
        this.id = id;
        this.now = now;
        this.old = old;
    }
}
