package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
32 [机器人]图片上传成功（事件）
id:目标ID
uuid:图片ID
 */
public class ImageUploadEventAPack extends PackBase {
    public long id;
    public String uuid;

    public ImageUploadEventAPack(long qq, long id, String uuid) {
        this.qq = qq;
        this.id = id;
        this.uuid = uuid;
    }
}
