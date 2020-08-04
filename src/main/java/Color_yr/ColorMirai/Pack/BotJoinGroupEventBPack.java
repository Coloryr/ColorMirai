package Color_yr.ColorMirai.Pack;

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

    public String getName() {
        return name;
    }
}
