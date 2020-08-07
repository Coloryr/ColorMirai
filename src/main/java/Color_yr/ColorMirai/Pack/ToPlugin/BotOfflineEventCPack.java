package Color_yr.ColorMirai.Pack.ToPlugin;

/*
14 [机器人]服务器主动要求更换另一个服务器（事件）
id：机器人QQ号
 */
public class BotOfflineEventCPack {
    private long id;

    public BotOfflineEventCPack(long id) {
        this.id = id;
    }

    public BotOfflineEventCPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
