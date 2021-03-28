package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

/*
1 [机器人]图片上传前. 可以阻止上传（事件）
name:图片ID
id:发送给的号码
 */
public class BeforeImageUploadPack extends PackBase {
    public String name;
    public long id;

    public BeforeImageUploadPack(long qq, String name, long id) {
        this.qq = qq;
        this.id = id;
        this.name = name;
    }
}
