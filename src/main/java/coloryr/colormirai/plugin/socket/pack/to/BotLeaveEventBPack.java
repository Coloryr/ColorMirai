package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/***
 * 8 [机器人]被管理员或群主踢出群（事件）
 */
public class BotLeaveEventBPack extends PackBase {
    /***
     * 群号
     */
    public long id;
    /***
     * 执行人QQ
     */
    public long fid;

    public BotLeaveEventBPack(long qq, long id, long fid) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
    }
}
