package coloryr.colormirai.plugin.socket.pack.to;

import coloryr.colormirai.plugin.socket.pack.PackBase;
import net.mamoe.mirai.message.data.MessageSource;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
import java.util.List;

/*
47 [机器人]在群临时会话消息发送后广播（事件）
id:群号
fid:发送到的QQ号
res:是否成功发送
message:消息
error:错误信息
 */
public class TempMessagePostSendEventPack extends PackBase {
    public long id;
    public long fid;
    public boolean res;
    public List<String> message;
    public String error;

    public TempMessagePostSendEventPack(long qq, long id, long fid, boolean res, MessageSource message, String error) {
        this.error = error;
        this.qq = qq;
        this.id = id;
        this.fid = fid;
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
