package Color_yr.ColorMirai.Pack;

public class BotMuteEventPack {
    private long id;
    private String name;
    private long fid;
    private int time;

    public BotMuteEventPack(String name, long id, long fid, int time) {
        this.fid = fid;
        this.id = id;
        this.name = name;
        this.time = time;
    }

    public BotMuteEventPack() {
    }

    public int getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }

    public String getName() {
        return name;
    }
}
