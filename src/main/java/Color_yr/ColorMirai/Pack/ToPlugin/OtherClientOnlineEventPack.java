package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
/*
appId:：设备Id
kind：设备类型1
platform：设备类型2
deviceName：设备名字
deviceKind：设备类型3
 */
public class OtherClientOnlineEventPack extends PackBase {
    public int appId;
    public String kind;
    public String platform;
    public String deviceName;
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
