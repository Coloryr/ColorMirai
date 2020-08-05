package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class FriendMessagePostSendEventPack {
    private MessageChain message;
    private long id;
    private String name;
    private boolean res;
    private String error;

    public FriendMessagePostSendEventPack(MessageChain message, long id, String name, boolean res, String error) {
        this.error = error;
        this.id = id;
        this.message = message;
        this.name = name;
        this.res = res;
    }

    public FriendMessagePostSendEventPack() {
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getError() {
        return error;
    }

    public MessageChain getMessage() {
        return message;
    }

    public boolean getRes() {
        return res;
    }
}
