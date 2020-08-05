package Color_yr.ColorMirai.Pack.ReturnPlugin;

public class MemberInfoPack {
    private long id;
    private String nick;
    private String name;
    private String img;
    private String per;
    private int mute;

    public int getMute() {
        return mute;
    }

    public void setMute(int mute) {
        this.mute = mute;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getPer() {
        return per;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
