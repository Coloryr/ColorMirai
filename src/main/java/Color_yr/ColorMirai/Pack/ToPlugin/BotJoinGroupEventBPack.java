package Color_yr.ColorMirai.Pack.ToPlugin;

/*
6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
id：群号
fid：邀请人QQ
name：邀请人nick
 */
public class BotJoinGroupEventBPack extends BotJoinGroupEventAPack {
    public String name;
    public long fid;

    public BotJoinGroupEventBPack(long qq, String name, long id, long fid) {
        super(qq, id);
        this.fid = fid;
        this.name = name;
    }
}
