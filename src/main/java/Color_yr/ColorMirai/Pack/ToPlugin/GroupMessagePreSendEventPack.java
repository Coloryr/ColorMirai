package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

public class GroupMessagePreSendEventPack {
    private long id;
    private String message;

    public GroupMessagePreSendEventPack(long id, Message message) {
        this.id = id;
        this.message = message.contentToString();
    }

    public GroupMessagePreSendEventPack() {
    }

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
