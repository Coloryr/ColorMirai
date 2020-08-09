package Color_yr.ColorMirai.Pack.FromPlugin;

/*
id：群号
fid：成员QQ号
time：时间
 */
public class MuteGroupMember {
    private long id;
    private long fid;
    private int time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}
