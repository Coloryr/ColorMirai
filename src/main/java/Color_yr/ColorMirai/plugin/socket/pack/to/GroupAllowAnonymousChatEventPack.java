package Color_yr.ColorMirai.plugin.socket.pack.to;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
24 [机器人]群 "匿名聊天" 功能状态改变（事件）
id:群号
fid:执行人QQ号
old:旧的状态
now:新的状态
 */
public class GroupAllowAnonymousChatEventPack extends PackBase {
    public long id;
    public long fid;
    public boolean old;
    public boolean now;

    public GroupAllowAnonymousChatEventPack(long qq, long id, long fid, boolean old, boolean now) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.old = old;
        this.now = now;
    }
}
