package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/***
 * 6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
 */
public class BotJoinGroupEventBPack extends PackBase {
    /***
     * 邀请人QQ
     */
    public long fid;
    /***
     * 群号
     */
    public long id;

    public BotJoinGroupEventBPack(long qq, long id, long fid) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
    }
}
