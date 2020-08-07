package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

/*
48 [机器人]在发送群临时会话消息前广播（事件）
id：群号
fid：发送人的QQ号
fname：发送人的群名片
message：消息
 */
public class TempMessagePreSendEventPack {
    private long id;
    private long fid;
    private String fname;
    private String message;

    public TempMessagePreSendEventPack(long id, long fid, Message message, String fname) {
        this.fid = fid;
        this.id = id;
        this.fname = fname;
        this.message = message.contentToString();
    }

    public TempMessagePreSendEventPack() {
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
