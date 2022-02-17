package coloryr.colormirai.plugin.socket.pack.to;

import net.mamoe.mirai.message.data.Message;

/**
 * 122 [机器人]在发送陌生人消息前广播（事件）
 */
public class StrangerMessagePreSendEventPack extends FriendMessagePreSendEventPack {

    public StrangerMessagePreSendEventPack(long qq, Message message, long id) {
        super(qq, message, id);
    }
}
