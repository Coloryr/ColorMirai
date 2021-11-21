package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.message.data.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 22 [机器人]在发送好友消息前广播（事件）
 */
public class FriendMessagePreSendEventPack extends PackBase {
    /**
     * 消息
     */
    public List<String> message;
    /**
     * 好友QQ号
     */
    public long id;

    public FriendMessagePreSendEventPack(long qq, Message message, long id) {
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        this.message.add(message.toString());
        this.message.add(message.contentToString());
    }
}
