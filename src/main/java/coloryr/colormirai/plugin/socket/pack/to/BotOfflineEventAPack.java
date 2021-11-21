package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/***
 * 10 [机器人]主动离线（事件）
 * 12 [机器人]被服务器断开（事件）
 * 13 [机器人]因网络问题而掉线（事件）
 */
public class BotOfflineEventAPack extends PackBase {
    /***
     * 离线原因
     */
    public String message;

    public BotOfflineEventAPack(long qq, String message) {
        this.qq = qq;
        this.message = message;
    }
}
