package Color_yr.ColorMirai.Pack.FromPlugin;

/*
id：群号
fid：成员QQ号
card：新的群名片
 */
public class SetGroupMemberCard {
    private long id;
    private long fid;
    private String card;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
}
