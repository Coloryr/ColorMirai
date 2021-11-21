package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/***
 * 7 [机器人]主动退出一个群（事件）
 */
public class BotLeaveEventAPack extends PackBase {
    /***
     * 群号
     */
    public long id;

    public BotLeaveEventAPack(long qq, long id) {
        this.qq = qq;
        this.id = id;
    }
}
