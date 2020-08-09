package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

/*
51 [机器人]收到朋友消息（事件）
id：朋友ID
fname：发送人的群名片
message：消息
time：时间
 */
public class FriendMessageEventPack {
    public long id;
    public String name;
    public String message;
    public int time;

    public FriendMessageEventPack(long id, String name, MessageChain message, int time) {
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
        this.time = time;
    }
}
