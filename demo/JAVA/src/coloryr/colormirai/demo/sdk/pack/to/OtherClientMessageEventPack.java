package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 88 [机器人]其他客户端发送消息给 Bot（事件）
 */
public class OtherClientMessageEventPack extends PackBase {
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
    /**
     * 信息
     */
    public List<String> message;
}
