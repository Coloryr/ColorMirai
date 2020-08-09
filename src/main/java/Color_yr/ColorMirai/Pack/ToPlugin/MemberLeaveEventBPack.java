package Color_yr.ColorMirai.Pack.ToPlugin;

/*
39 [机器人]成员主动离开（事件）
id：群号
fid：离开人QQ号
name：离开人群名片
 */
public class MemberLeaveEventBPack {
    public long id;
    public long fid;
    public String name;

    public MemberLeaveEventBPack(long id, long fid, String name) {
        this.id = id;
        this.fid = fid;
    }
}
