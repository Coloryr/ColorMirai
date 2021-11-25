package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/*
88 [机器人]其他客户端发送消息给 Bot（事件）
appId:设备ID
platform:设备类型
deviceName:设备名字
deviceKind:设备类型
message:信息
 */
public class OtherClientMessageEventPack extends PackBase {
    public int appId;
    public String platform;
    public String deviceName;
    public String deviceKind;
    public List<String> message;

}
