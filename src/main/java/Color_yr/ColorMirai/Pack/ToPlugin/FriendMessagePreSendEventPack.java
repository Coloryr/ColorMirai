package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.Message;

import java.util.ArrayList;
import java.util.List;

/*
22 [机器人]在发送好友消息前广播（事件）
message：消息
id：好友QQ号
name：好友nick
 */
public class FriendMessagePreSendEventPack extends PackBase {
    public List<String> message;
    public long id;
    public String name;

    public FriendMessagePreSendEventPack(long qq, Message message, long id, String name) {
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        this.message.add(message.toString());
        this.message.add(message.contentToString());
        this.name = name;
    }
}
