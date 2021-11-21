package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 15 [机器人]登录完成, 好友列表, 群组列表初始化完成（事件）
 */
public class BotOnlineEventPack extends PackBase {

    public BotOnlineEventPack(long qq) {
        this.qq = qq;
    }
}
