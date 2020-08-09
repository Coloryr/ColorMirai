package Color_yr.ColorMirai.Pack.ToPlugin;

/*
17 [机器人]被取消禁言（事件）
id：群号
fid：执行人QQ号
 */
public class BotUnmuteEventPack {
    public long id;
    public long fid;

    public BotUnmuteEventPack(long id, long fid) {
        this.fid = fid;
        this.id = id;
    }
}
