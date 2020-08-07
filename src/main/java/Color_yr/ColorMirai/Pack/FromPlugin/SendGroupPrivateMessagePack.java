package Color_yr.ColorMirai.Pack.FromPlugin;

/*
53 [插件]发送私聊消息
id：群号
fid：成员QQ号
message：消息
 */
public class SendGroupPrivateMessagePack {
    private long id;
    private long fid;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
