package Color_yr.ColorMirai.Pack.ToPlugin;

/*
35 [机器人]成成员被邀请加入群（事件）
36 [机器人]成员主动加入群（事件）
id：群号
fid：进群人QQ号
 */
public class MemberJoinEventAPack {
    private long id;
    private long fid;

    public MemberJoinEventAPack(long id, long fid) {
        this.fid = fid;
        this.id = id;
    }

    public MemberJoinEventAPack() {
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
