package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/*
51 [机器人]收到朋友消息（事件）
id:朋友QQ号
message:消息
time:时间
 */
public class FriendMessageEventPack extends PackBase {
    public long id;
    public List<String> message;
    public int time;

    public FriendMessageEventPack(long qq, long id, MessageChain message, int time) {
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.message.add(message.contentToString());
        this.time = time;
    }
}
