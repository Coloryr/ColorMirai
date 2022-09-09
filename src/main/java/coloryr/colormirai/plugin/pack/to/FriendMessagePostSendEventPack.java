package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 21 [机器人]在好友消息发送后广播（事件）
 */
public class FriendMessagePostSendEventPack extends PackBase {
    /**
     * 好友QQ号
     */
    public long id;
    /**
     * 是否成功发送
     */
    public boolean res;
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
     * 错误消息
     */
    public String error;

    public FriendMessagePostSendEventPack(long qq, MessageSource message, long id, boolean res,
                                          int[] ids1, int[] ids2, String error) {
        this.qq = qq;
        this.error = error;
        this.id = id;
        this.message = new ArrayList<>();
        if (message != null) {
            this.message.add(message.toString());
            for (SingleMessage item : message.getOriginalMessage()) {
                this.message.add(item.toString());
            }
        }
        this.ids1 = ids1;
        this.ids2 = ids2;
        this.res = res;
    }
}
