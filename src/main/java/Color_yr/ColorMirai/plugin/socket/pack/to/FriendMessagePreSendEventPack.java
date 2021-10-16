package Color_yr.ColorMirai.plugin.socket.pack.to;

import Color_yr.ColorMirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.message.data.Message;

import java.util.ArrayList;
import java.util.List;

/*
22 [机器人]在发送好友消息前广播（事件）
message:消息
id:好友QQ号
 */
public class FriendMessagePreSendEventPack extends PackBase {
    public List<String> message;
    public long id;

    public FriendMessagePreSendEventPack(long qq, Message message, long id) {
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        this.message.add(message.toString());
        this.message.add(message.contentToString());
    }
}
