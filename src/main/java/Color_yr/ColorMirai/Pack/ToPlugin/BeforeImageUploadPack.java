package Color_yr.ColorMirai.Pack.ToPlugin;

/*
1 [机器人]图片上传前. 可以阻止上传（事件）
name：图片ID
id：发送给的号码
 */
public class BeforeImageUploadPack {
    public String name;
    public long id;

    public BeforeImageUploadPack(String name, long id) {
        this.id = id;
        this.name = name;
    }
}
