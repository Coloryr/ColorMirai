package Color_yr.ColorMirai.Pack.FromPlugin;

/*
63 [插件]发送图片到朋友
id：好友QQ号
img：图片的BASE64编码
 */
public class SendFriendImagePack {
    private long id;
    private String img;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
