package Color_yr.ColorMirai.Pack.FromPlugin;

/*
52 [插件]发送群消息
id：群号
message：消息
 */
public class SendGroupMessagePack {
    private long id;
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
