package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.message.data.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 48 [机器人]在发送群临时会话消息前广播（事件）
 */
public class TempMessagePreSendEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 发送人的QQ号
     */
    public long fid;
    /**
     * 消息
     */
    public List<String> message;

    public TempMessagePreSendEventPack(long qq, long id, long fid, Message message) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.message = new ArrayList<>();
        this.message.add(message.toString());
        this.message.add(message.contentToString());
    }
}
