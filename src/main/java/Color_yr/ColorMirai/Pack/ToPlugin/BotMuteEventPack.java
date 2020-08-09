package Color_yr.ColorMirai.Pack.ToPlugin;

/*
9 [机器人]被禁言（事件）
id：群号
name：执行人nick
fid：执行人QQ号
time：禁言时间
 */
public class BotMuteEventPack {
    public long id;
    public String name;
    public long fid;
    public int time;

    public BotMuteEventPack(String name, long id, long fid, int time) {
        this.fid = fid;
        this.id = id;
        this.name = name;
        this.time = time;
    }
}
