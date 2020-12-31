package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;

/*
appId:：设备Id
platform：设备类型2
deviceName：设备名字
deviceKind：设备类型3
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
