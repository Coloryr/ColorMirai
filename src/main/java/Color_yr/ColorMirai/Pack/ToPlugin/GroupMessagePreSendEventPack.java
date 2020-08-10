package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

import java.util.ArrayList;
import java.util.List;

/*
29 [机器人]在发送群消息前广播（事件）
id：群号
message：消息
 */
public class GroupMessagePreSendEventPack {
    public long id;
    public List<String> message;

    public GroupMessagePreSendEventPack(long id, Message message) {
        this.id = id;
        this.message = new ArrayList<>();
        this.message.add(message.toString());
        this.message.add(message.contentToString());
    }
}
