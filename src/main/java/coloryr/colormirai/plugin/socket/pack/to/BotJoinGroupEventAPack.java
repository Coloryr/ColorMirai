package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/***
 * 5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
 */
public class BotJoinGroupEventAPack extends PackBase {
    /***
     * 群号
     */
    public long id;

    public BotJoinGroupEventAPack(long qq, long id) {
        this.qq = qq;
        this.id = id;
    }
}
