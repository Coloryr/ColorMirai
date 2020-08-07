package Color_yr.ColorMirai.Pack.FromPlugin;

/*
62 [插件]发送图片到私聊
id：群号
fid：成员QQ号
img：图片BASE64编码
 */
public class SendImageGroupPrivatePack {
    private long id;
    private long fid;
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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
}
