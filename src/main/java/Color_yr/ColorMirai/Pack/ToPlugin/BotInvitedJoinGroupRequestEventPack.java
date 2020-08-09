package Color_yr.ColorMirai.Pack.ToPlugin;

/*
4 [机器人]被邀请加入一个群（事件）
eventid：事件id
id：群号
name：邀请人nick
fid：邀请人QQ号
 */
public class BotInvitedJoinGroupRequestEventPack {
    public long eventid;
    public long id;
    public String name;
    public long fid;

    public BotInvitedJoinGroupRequestEventPack(String name, long id, long fid, long eventid) {
        this.fid = fid;
        this.id = id;
        this.name = name;
        this.eventid = eventid;
    }
}
