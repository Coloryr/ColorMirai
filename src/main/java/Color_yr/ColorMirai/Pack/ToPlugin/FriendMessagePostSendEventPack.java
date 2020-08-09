package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

/*
21 [机器人]在好友消息发送后广播（事件）
message：消息
id：好友QQ号
name：好友nick
res：是否成功发送
error：错误消息
 */
public class FriendMessagePostSendEventPack {
    public String message;
    public long id;
    public String name;
    public boolean res;
    public String error;

    public FriendMessagePostSendEventPack(MessageChain message, long id, String name, boolean res, String error) {
        this.error = error;
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
        this.res = res;
    }
}
