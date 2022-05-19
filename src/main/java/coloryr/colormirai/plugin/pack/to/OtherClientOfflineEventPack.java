package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 87 [机器人]其他客户端离线（事件）
 */
public class OtherClientOfflineEventPack extends PackBase {
    /**
     * 设备ID
     */
    public int appId;
    /**
     * 设备类型
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

    public OtherClientOfflineEventPack(long qq, int appId, String platform, String deviceName, String deviceKind) {
        this.qq = qq;
        this.appId = appId;
        this.deviceKind = deviceKind;
        this.platform = platform;
        this.deviceName = deviceName;
    }
}
