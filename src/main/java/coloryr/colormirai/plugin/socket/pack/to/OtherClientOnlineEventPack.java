package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/*
86 [机器人]其他客户端上线（事件）
appId::设备Id
kind:设备类型
platform:设备类型
deviceName:设备名字
deviceKind:设备类型
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
