package Color_yr.ColorMirai.Pack;

import net.mamoe.mirai.message.data.Message;

public class FriendMessagePreSendEventPack {
    private Message message;
    private long id;
    private String name;

    public FriendMessagePreSendEventPack(Message message, long id, String name) {
        this.id = id;
        this.message = message;
        this.name = name;
    }

    public FriendMessagePreSendEventPack() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public Message getMessage() {
        return message;
    }
}
