package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 86 [机器人]其他客户端上线（事件）
 */
public class OtherClientOnlineEventPack extends PackBase {
    /**
     * 设备Id
     */
    public int appId;
    /**
     * 设备类型
     */
    public String kind;
    /**
     * 设备平台
     */
    public String platform;
    /**
     * 设备名字
     */
    public String deviceName;
    /**
     * 设备类型
     */
    public String deviceKind;
}
