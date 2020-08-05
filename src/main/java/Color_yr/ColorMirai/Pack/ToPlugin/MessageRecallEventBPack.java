package Color_yr.ColorMirai.Pack.ToPlugin;

public class MessageRecallEventBPack  extends MessageRecallEventAPack {
    private long fid;

    public MessageRecallEventBPack(long id, long fid, int mid, int time) {
        super(id, mid, time);
        this.fid = fid;
    }

    public MessageRecallEventBPack() {
    }

    public long getFid() {
        return fid;
    }
}
