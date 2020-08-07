package Color_yr.ColorMirai.Pack.FromPlugin;

/*
54 [插件]发送好友消息
id：好友QQ号
message：消息
 */
public class SendFriendMessagePack {
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
