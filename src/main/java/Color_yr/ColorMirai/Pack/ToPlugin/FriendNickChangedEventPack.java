package Color_yr.ColorMirai.Pack.ToPlugin;

/*
73 [机器人]好友昵称改变（事件）
id：好友QQ号
old：旧的昵称
new_：新的昵称
 */
public class FriendNickChangedEventPack {
    public long id;
    public String old;
    public String new_;

    public FriendNickChangedEventPack(long id, String old, String new_) {
        this.id = id;
        this.old = old;
        this.new_ = new_;
    }
}
