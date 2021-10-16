package Color_yr.ColorMirai.plugin.socket.pack.to;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
id:群号
 */
public class BotJoinGroupEventAPack extends PackBase {
    public long id;

    public BotJoinGroupEventAPack(long qq, long id) {
        this.qq = qq;
        this.id = id;
    }
}
