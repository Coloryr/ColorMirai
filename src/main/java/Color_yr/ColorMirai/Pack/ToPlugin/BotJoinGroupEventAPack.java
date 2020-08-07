package Color_yr.ColorMirai.Pack.ToPlugin;

/*
5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
id：群号
 */
public class BotJoinGroupEventAPack {
    private long id;

    public BotJoinGroupEventAPack(long id) {
        this.id = id;
    }

    public BotJoinGroupEventAPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
