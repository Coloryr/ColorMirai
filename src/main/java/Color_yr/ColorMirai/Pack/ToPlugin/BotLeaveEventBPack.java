package Color_yr.ColorMirai.Pack.ToPlugin;

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
