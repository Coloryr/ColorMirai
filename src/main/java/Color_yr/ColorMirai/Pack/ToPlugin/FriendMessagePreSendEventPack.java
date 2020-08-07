package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

/*
22 [机器人]在发送好友消息前广播（事件）
message：消息
id：好友QQ号
name：好友nick
 */
public class FriendMessagePreSendEventPack {
    private String message;
    private long id;
    private String name;

    public FriendMessagePreSendEventPack(Message message, long id, String name) {
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
    }

    public FriendMessagePreSendEventPack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
