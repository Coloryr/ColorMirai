package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
10 [机器人]主动离线（事件）
12 [机器人]被服务器断开（事件）
13 [机器人]因网络问题而掉线（事件）
message:离线原因
 */
public class BotOfflineEventAPack extends PackBase {
    public String message;

    public BotOfflineEventAPack(long qq, String message) {
        this.qq = qq;
        this.message = message;
    }
}
