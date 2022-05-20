package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 33 [机器人]图片上传失败（事件）
 */
public class ImageUploadEventBPack extends PackBase {
    /**
     * 目标ID
     */
    public long id;
    /**
     * 错误码
     */
    public int index;
    /**
     * 资源名
     */
    public String name;
    /**
     * 错误消息
     */
    public String error;

    public ImageUploadEventBPack(long qq, long id, String name, String error, int index) {
        this.qq = qq;
        this.id = id;
        this.name = name;
        this.error = error;
        this.index = index;
    }
}
