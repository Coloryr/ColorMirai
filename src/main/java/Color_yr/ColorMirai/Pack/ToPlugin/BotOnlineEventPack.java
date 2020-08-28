package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）
id：机器人QQ号
 */
public class BotOnlineEventPack extends PackBase {
    public long id;

    public BotOnlineEventPack(long qq, long id) {
        this.id = id;
        this.qq = qq;
    }
}
