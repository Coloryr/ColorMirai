package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

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
}
