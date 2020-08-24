package Color_yr.ColorMirai.Pack.ToPlugin;

/*
35 [机器人]成成员被邀请加入群（事件）
36 [机器人]成员主动加入群（事件）
id：群号
fid：进群人QQ号
 */
public class MemberJoinEventAPack {
    public long id;
    public long fid;
    public String name;

    public MemberJoinEventAPack(long id, long fid, String name) {
        this.fid = fid;
        this.id = id;
        this.name = name;
    }
}
