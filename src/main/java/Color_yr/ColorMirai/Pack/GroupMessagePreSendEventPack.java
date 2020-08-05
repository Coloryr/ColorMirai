package Color_yr.ColorMirai.Pack;

import net.mamoe.mirai.message.data.Message;

public class GroupMessagePreSendEventPack {
    private long id;
    private Message message;

    public GroupMessagePreSendEventPack(long id, Message message) {
        this.id = id;
        this.message = message;
    }

    public GroupMessagePreSendEventPack() {
    }

    public long getId() {
        return id;
    }

    public Message getMessage() {
        return message;
    }
}
