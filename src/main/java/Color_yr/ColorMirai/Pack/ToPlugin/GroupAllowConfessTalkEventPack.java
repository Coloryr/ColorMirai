package Color_yr.ColorMirai.Pack.ToPlugin;

/*
25 [机器人]群 "坦白说" 功能状态改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
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

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNew_() {
        return new_;
    }

    public void setNew_(boolean new_) {
        this.new_ = new_;
    }

    public boolean isOld() {
        return old;
    }

    public void setOld(boolean old) {
        this.old = old;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }
}
