package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

/*
22 [机器人]在发送好友消息前广播（事件）
message：消息
id：好友QQ号
name：好友nick
 */
public class FriendMessagePreSendEventPack {
    public String message;
    public long id;
    public String name;

    public FriendMessagePreSendEventPack(Message message, long id, String name) {
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
    }
}
