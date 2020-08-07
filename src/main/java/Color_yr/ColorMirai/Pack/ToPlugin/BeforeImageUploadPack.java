package Color_yr.ColorMirai.Pack.ToPlugin;

/*
1 [机器人]图片上传前. 可以阻止上传（事件）
name：图片ID
id：发送给的号码
 */
public class BeforeImageUploadPack {
    private String name;
    private long id;

    public BeforeImageUploadPack() {
    }

    public BeforeImageUploadPack(String name, long id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
