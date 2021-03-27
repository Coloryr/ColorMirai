package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/*
89 [机器人]其他客户端发送群消息（事件）
id:群号
time:时间
message:消息
 */
public class GroupMessageSyncEventPack extends PackBase {
    public long id;
    public int time;
    public List<String> message;

    public GroupMessageSyncEventPack(long qq, long id, int time, MessageChain message) {
        this.qq = qq;
        this.id = id;
        this.time = time;
        this.message = new ArrayList<>();
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.message.add(message.contentToString());
    }
}
