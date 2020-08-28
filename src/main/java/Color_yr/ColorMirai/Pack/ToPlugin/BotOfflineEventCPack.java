package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
14 [机器人]服务器主动要求更换另一个服务器（事件）
id：机器人QQ号
 */
public class BotOfflineEventCPack extends PackBase {
    public long id;

    public BotOfflineEventCPack(long qq, long id) {
        this.id = id;
        this.qq = qq;
    }
}
