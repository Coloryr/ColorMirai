package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 14 [机器人]服务器主动要求更换另一个服务器（事件）
 */
public class BotOfflineEventCPack extends PackBase {

    public BotOfflineEventCPack(long qq) {
        this.qq = qq;
    }
}
