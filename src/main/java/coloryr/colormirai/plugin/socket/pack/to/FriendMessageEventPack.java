package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/*
 * 51 [机器人]收到朋友消息（事件）
 */
public class FriendMessageEventPack extends PackBase {
    /*
     * 朋友QQ号
     */
    public long id;
    /*
     * 消息
     */
    public List<String> message;
    /*
     * 时间
     */
    public int time;
    /*
     * 昵称
     */
    public String name;

    public FriendMessageEventPack(long qq, long id, MessageChain message, int time, String name) {
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.message.add(message.contentToString());
        this.time = time;
        this.name = name;
    }
}
