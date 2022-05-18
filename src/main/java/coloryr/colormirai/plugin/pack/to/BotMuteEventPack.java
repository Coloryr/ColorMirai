package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/***
 * 9 [机器人]被禁言（事件）
 */
public class BotMuteEventPack extends PackBase {
    /***
     * 群号
     */
    public long id;
    /***
     * 执行人QQ号
     */
    public long fid;
    /***
     * 禁言时间
     */
    public int time;

    public BotMuteEventPack(long qq, long id, long fid, int time) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.time = time;
    }
}
