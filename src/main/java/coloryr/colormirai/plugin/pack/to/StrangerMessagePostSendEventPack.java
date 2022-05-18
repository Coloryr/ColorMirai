package coloryr.colormirai.plugin.pack.to;

import net.mamoe.mirai.message.data.MessageSource;

/**
 * 123 [机器人]在陌生人消息发送后广播（事件）
 */
public class StrangerMessagePostSendEventPack extends FriendMessagePostSendEventPack {

    public StrangerMessagePostSendEventPack(long qq, MessageSource message, long id, boolean res, String error) {
        super(qq, message, id, res, error);
    }
}
