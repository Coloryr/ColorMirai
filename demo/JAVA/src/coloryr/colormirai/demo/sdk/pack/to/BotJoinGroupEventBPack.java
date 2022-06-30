package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
 */
public class BotJoinGroupEventBPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 邀请人QQ
     */
    public long fid;
}
