package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 123 [机器人]在陌生人消息发送后广播（事件）
 */
public class StrangerMessagePostSendEventPack extends FriendMessagePostSendEventPack {

    public StrangerMessagePostSendEventPack(long qq, MessageSource message, long id, boolean res, String error) {
        super(qq, message, id, res, error);
    }
}
