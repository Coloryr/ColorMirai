package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.List;

/*
appId:：设备Id
platform：设备类型2
deviceName：设备名字
deviceKind：设备类型3
senderName：发送者名字
message：信息
 */
public class OtherClientMessageEventPack extends PackBase {
    public int appId;
    public String platform;
    public String deviceName;
    public String deviceKind;
    public String senderName;
    public List<String> message;

    public OtherClientMessageEventPack(long qq, int appId, String platform, String deviceName, String deviceKind, String senderName, MessageChain messages) {
        this.qq = qq;
        this.appId = appId;
        this.deviceKind = deviceKind;
        this.platform = platform;
        this.deviceName = deviceName;
        this.senderName = senderName;
        for (SingleMessage item : messages) {
            this.message.add(item.toString());
        }
        this.message.add(messages.contentToString());
    }
}
