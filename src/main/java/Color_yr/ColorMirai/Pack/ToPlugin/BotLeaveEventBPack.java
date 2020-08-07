package Color_yr.ColorMirai.Pack.ToPlugin;

/*
8 [机器人]被管理员或群主踢出群（事件）
id：群号
fid：执行人QQ
name：执行人nick
 */
public class BotLeaveEventBPack extends BotLeaveEventAPack {
    private String name;
    private long fid;

    public BotLeaveEventBPack(String name, long id, long fid) {
        super(id);
        this.fid = fid;
        this.name = name;
    }

    public BotLeaveEventBPack() {
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
