package coloryr.colormirai.demo.RobotSDK.pack.to;

/*
33 [机器人]图片上传失败（事件）
error:错误消息
index:错误码
 */
public class ImageUploadEventBPack extends ImageUploadEventAPack {
    public String error;
    public int index;

    public ImageUploadEventBPack(long qq, long id, String name, String error, int index) {
        super(qq, id, name);
        this.error = error;
        this.index = index;
    }
}
