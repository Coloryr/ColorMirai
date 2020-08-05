package Color_yr.ColorMirai.Pack.ToPlugin;

public class GroupAllowConfessTalkEventPack {
    private long id;
    private boolean old;
    private boolean new_;
    private boolean bot;

    public GroupAllowConfessTalkEventPack(long id, boolean old, boolean new_, boolean bot) {
        this.id = id;
        this.old = old;
        this.new_ = new_;
        this.bot = bot;
    }

    public GroupAllowConfessTalkEventPack() {
    }

    public long getId() {
        return id;
    }

    public boolean isNew_() {
        return new_;
    }

    public boolean isOld() {
        return old;
    }

    public boolean isBot() {
        return bot;
    }
}
