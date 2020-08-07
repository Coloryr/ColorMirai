package Color_yr.ColorMirai.Pack.FromPlugin;

/*
61 [插件]发送图片到群
id：群号
img：图片的BASE64编码
 */
public class SendImageGroupPack {
    private long id;
    private String img;

    public SendImageGroupPack() {
    }

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
