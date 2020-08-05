package Color_yr.ColorMirai.Pack.ToPlugin;

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
