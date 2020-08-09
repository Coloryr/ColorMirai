package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

/*
29 [机器人]在发送群消息前广播（事件）
id：群号
message：消息
 */
public class GroupMessagePreSendEventPack {
    public long id;
    public String message;

    public GroupMessagePreSendEventPack(long id, Message message) {
        this.id = id;
        this.message = message.contentToString();
    }
}
