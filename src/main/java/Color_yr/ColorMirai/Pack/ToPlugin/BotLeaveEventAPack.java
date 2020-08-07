package Color_yr.ColorMirai.Pack.ToPlugin;

/*
7 [机器人]主动退出一个群（事件）
id；群号
 */
public class BotLeaveEventAPack {
    private long id;

    public BotLeaveEventAPack(long id) {
        this.id = id;
    }

    public BotLeaveEventAPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
