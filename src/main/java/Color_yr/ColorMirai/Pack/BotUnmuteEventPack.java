package Color_yr.ColorMirai.Pack;

public class BotUnmuteEventPack {
    private long id;
    private long fid;

    public BotUnmuteEventPack(long id, long fid) {
        this.fid = fid;
        this.id = id;
    }

    public BotUnmuteEventPack() {
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }
}
