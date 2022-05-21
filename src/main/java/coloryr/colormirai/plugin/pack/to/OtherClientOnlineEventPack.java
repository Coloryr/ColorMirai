package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

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

    public OtherClientOnlineEventPack(long qq, int appId, String kind, String platform, String deviceName, String deviceKind) {
        this.qq = qq;
        this.appId = appId;
        this.deviceKind = deviceKind;
        this.kind = kind;
        this.platform = platform;
        this.deviceName = deviceName;
    }
}
