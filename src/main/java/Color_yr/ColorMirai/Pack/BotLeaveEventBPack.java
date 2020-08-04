package Color_yr.ColorMirai.Pack;

public class BotLeaveEventBPack extends BotLeaveEventAPack{
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

    public String getName() {
        return name;
    }
}
