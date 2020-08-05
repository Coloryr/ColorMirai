package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class FriendMessageEventPack {
    private long id;
    private String name;
    private MessageChain message;
    private int time;

    public FriendMessageEventPack(long id, String name, MessageChain message, int time) {
        this.id = id;
        this.message = message;
        this.name = name;
        this.time = time;
    }

    public FriendMessageEventPack() {
    }

    public int getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MessageChain getMessage() {
        return message;
    }
}
