package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
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

    public OtherClientMessageEventPack(long qq, int appId, String platform, String deviceName, String deviceKind, MessageChain messages) {
        this.qq = qq;
        this.appId = appId;
        this.deviceKind = deviceKind;
        this.platform = platform;
        this.deviceName = deviceName;
        this.message = new ArrayList<>();
        for (SingleMessage item : messages) {
            this.message.add(item.toString());
        }
        this.message.add(messages.contentToString());
    }
}
