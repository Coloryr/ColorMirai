package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
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
