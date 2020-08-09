package Color_yr.ColorMirai.Pack.ToPlugin;

/*
44 [机器人]好友消息撤回（事件）
id：好友QQ号
mid：消息id
time：时间
 */
public class MessageRecallEventAPack {
    public long id;
    public int mid;
    public int time;

    public MessageRecallEventAPack(long id, int mid, int time) {
        this.id = id;
        this.mid = mid;
        this.time = time;
    }
}
