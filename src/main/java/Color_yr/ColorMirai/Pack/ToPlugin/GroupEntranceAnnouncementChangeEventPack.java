package Color_yr.ColorMirai.Pack.ToPlugin;

/*
27 [机器人]入群公告改变（事件）
id：群号
fid：执行人QQ号
old：旧的状态
new_：新的状态
 */
public class GroupEntranceAnnouncementChangeEventPack {
    public long id;
    public long fid;
    public String old;
    public String new_;

    public GroupEntranceAnnouncementChangeEventPack(long id, long fid, String old, String new_) {
        this.id = id;
        this.old = old;
        this.new_ = new_;
        this.fid = fid;
    }
}
