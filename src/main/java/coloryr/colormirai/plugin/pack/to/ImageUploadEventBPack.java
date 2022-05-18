package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/*
33 [机器人]图片上传失败（事件）
error:错误消息
index:错误码
 */
public class ImageUploadEventBPack extends PackBase {
    public String error;
    public int index;
    public long id;
    public String name;

    public ImageUploadEventBPack(long qq, long id, String name, String error, int index) {
        this.qq = qq;
        this.id = id;
        this.name = name;
        this.error = error;
        this.index = index;
    }
}
