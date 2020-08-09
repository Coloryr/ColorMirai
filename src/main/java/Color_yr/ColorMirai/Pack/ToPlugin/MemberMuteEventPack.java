package Color_yr.ColorMirai.Pack.ToPlugin;

/*
40 [机器人]群成员被禁言（事件）
id：群号
fid：被禁言的QQ号
eid：执行禁言的QQ号
fname：被禁言的群名片
ename：执行禁言的群名片
time：时间
 */
public class MemberMuteEventPack {
    public long id;
    public long fid;
    public long eid;
    public String fname;
    public String ename;
    public int time;

    public MemberMuteEventPack(long id, long fid, long eid, String fname, String ename, int time) {
        this.eid = eid;
        this.fid = fid;
        this.id = id;
        this.ename = ename;
        this.fname = fname;
        this.time = time;
    }
}
