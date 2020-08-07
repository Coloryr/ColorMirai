package Color_yr.ColorMirai.Pack.ToPlugin;

/*
6 [机器人]成功加入了一个新群（机器人被一个群内的成员直接邀请加入了群）（事件）
id：群号
fid：邀请人QQ
name：邀请人nick
 */
public class BotJoinGroupEventBPack extends BotJoinGroupEventAPack {
    private String name;
    private long fid;

    public BotJoinGroupEventBPack(String name, long id, long fid) {
        super(id);
        this.fid = fid;
        this.name = name;
    }

    public BotJoinGroupEventBPack() {
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
