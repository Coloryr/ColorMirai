package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 89 [机器人]其他客户端发送群消息（事件）
 */
public class GroupMessageSyncEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 时间
     */
    public int time;
    /**
     * 消息
     */
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
