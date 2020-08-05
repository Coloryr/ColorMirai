package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

public class FriendMessagePreSendEventPack {
    private String message;
    private long id;
    private String name;

    public FriendMessagePreSendEventPack(Message message, long id, String name) {
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
    }

    public FriendMessagePreSendEventPack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
