package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.List;

/*
id：群号
time：时间
senderName：发送者名字
message：消息
 */
public class GroupMessageSyncEventPack extends PackBase {
    public long id;
    public int time;
    public String senderName;
    public List<String> message;

    public GroupMessageSyncEventPack(long qq, long id, int time, String senderName, MessageChain message) {
        this.qq = qq;
        this.id = id;
        this.time = time;
        this.senderName = senderName;
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.message.add(message.contentToString());
    }
}
