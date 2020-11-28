package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
14 [机器人]服务器主动要求更换另一个服务器（事件）
id：机器人QQ号
 */
public class BotOfflineEventCPack extends PackBase {

    public BotOfflineEventCPack(long qq) {
        this.qq = qq;
    }
}
