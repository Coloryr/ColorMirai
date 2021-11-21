package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.message.data.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 29 [机器人]在发送群消息前广播（事件）
 */
public class GroupMessagePreSendEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 消息
     */
    public List<String> message;

    public GroupMessagePreSendEventPack(long qq, long id, Message message) {
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        this.message.add(message.toString());
        this.message.add(message.contentToString());
    }
}
