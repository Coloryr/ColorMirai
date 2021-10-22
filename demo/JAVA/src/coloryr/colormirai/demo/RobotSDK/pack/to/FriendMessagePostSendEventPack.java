package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/*
21 [机器人]在好友消息发送后广播（事件）
message:消息
id:好友QQ号
res:是否成功发送
error:错误消息
 */
public class FriendMessagePostSendEventPack extends PackBase {
    public List<String> message;
    public long id;
    public boolean res;
    public String error;

    public FriendMessagePostSendEventPack(long qq, MessageSource message, long id, boolean res, String error) {
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
        this.res = res;
    }
}
