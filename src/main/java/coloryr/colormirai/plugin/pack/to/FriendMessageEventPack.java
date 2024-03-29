package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 51 [机器人]收到朋友消息（事件）
 */
public class FriendMessageEventPack extends PackBase {
    /**
     * 朋友QQ号
     */
    public long id;
    /**
     * 昵称
     */
    public String name;
    /**
     * 消息ID
     */
    public int[] ids1;
    /**
     * 消息ID
     */
    public int[] ids2;
    /**
     * 消息
     */
    public List<String> message;
    /**
     * 时间
     */
    public int time;

    public FriendMessageEventPack(long qq, long id, int[] ids1, int[] ids2,
                                  MessageChain message, int time, String name) {
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.message.add(message.contentToString());
        this.ids1 = ids1;
        this.ids2 = ids2;
        this.time = time;
        this.name = name;
    }
}
