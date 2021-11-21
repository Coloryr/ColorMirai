package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/***
 * 11 [机器人]被挤下线（事件）
 */
public class BotOfflineEventBPack extends PackBase {
    /***
     * 离线原因
     */
    public String message;
    /***
     * 标题
     */
    public String title;

    public BotOfflineEventBPack(long qq, String message, String title) {
        this.qq = qq;
        this.title = title;
        this.message = message;
    }
}
