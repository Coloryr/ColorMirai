package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/*
28 [机器人]在群消息发送后广播（事件）
id：群号
res：是否发送成功
message：发送的消息
error：错误消息
 */
public class GroupMessagePostSendEventPack extends PackBase {
    public long id;
    public boolean res;
    public List<String> message;
    public String error;

    public GroupMessagePostSendEventPack(long qq, long id, boolean res, MessageSource message, String error) {
        this.error = error;
        this.id = id;
        this.qq = qq;
        this.message = new ArrayList<>();
        this.message.add(message.toString());
        for (SingleMessage item : message.getOriginalMessage()) {
            this.message.add(item.toString());
        }
        this.res = res;
    }
}
