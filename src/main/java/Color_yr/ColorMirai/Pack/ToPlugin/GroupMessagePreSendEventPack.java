package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

/*
29 [机器人]在发送群消息前广播（事件）
id：群号
message：消息
 */
public class GroupMessagePreSendEventPack {
    private long id;
    private String message;

    public GroupMessagePreSendEventPack(long id, Message message) {
        this.id = id;
        this.message = message.contentToString();
    }

    public GroupMessagePreSendEventPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
