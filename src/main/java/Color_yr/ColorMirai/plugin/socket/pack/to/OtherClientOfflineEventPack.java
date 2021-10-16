package Color_yr.ColorMirai.plugin.socket.pack.to;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;

/*
87 [机器人]其他客户端离线（事件）
appId::设备ID
platform:设备类型
deviceName:设备名字
deviceKind:设备类型
 */
public class OtherClientOfflineEventPack extends PackBase {
    public int appId;
    public String platform;
    public String deviceName;
    public String deviceKind;

    public OtherClientOfflineEventPack(long qq, int appId, String platform, String deviceName, String deviceKind) {
        this.qq = qq;
        this.appId = appId;
        this.deviceKind = deviceKind;
        this.platform = platform;
        this.deviceName = deviceName;
    }
}
