package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 1 [机器人]图片上传前. 可以阻止上传（事件）
 */
public class BeforeImageUploadPack extends PackBase {
    /**
     * 发送给的号码
     */
    public long id;
    /**
     * 图片UUID
     */
    public String name;

}
