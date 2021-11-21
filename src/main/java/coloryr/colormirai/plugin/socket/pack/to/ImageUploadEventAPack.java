package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
32 [机器人]图片上传成功（事件）
id:
uuid:
 */
public class ImageUploadEventAPack extends PackBase {
    /**
     * 目标ID
     */
    public long id;
    /**
     * 目标ID
     */
    public long fid;
    /**
     * 图片ID
     */
    public String uuid;

    public ImageUploadEventAPack(long qq, long id, long fid, String uuid) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.uuid = uuid;
    }
}
