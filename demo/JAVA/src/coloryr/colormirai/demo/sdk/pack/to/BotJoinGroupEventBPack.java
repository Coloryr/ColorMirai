package coloryr.colormirai.demo.sdk.pack.to;

/*
6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
id:群号
fid:邀请人QQ
 */
public class BotJoinGroupEventBPack extends BotJoinGroupEventAPack {
    public long fid;

    public BotJoinGroupEventBPack(long qq, long id, long fid) {
        super(qq, id);
        this.fid = fid;
    }
}
