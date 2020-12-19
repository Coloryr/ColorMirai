package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/*
21 [机器人]在好友消息发送后广播（事件）
message：消息
id：好友QQ号
name：好友nick
res：是否成功发送
error：错误消息
 */
public class FriendMessagePostSendEventPack extends PackBase {
    public List<String> message;
    public long id;
    public String name;
    public boolean res;
    public String error;

    public FriendMessagePostSendEventPack(long qq, MessageSource message, long id, String name, boolean res, String error) {
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
        this.name = name;
        this.res = res;
    }
}
