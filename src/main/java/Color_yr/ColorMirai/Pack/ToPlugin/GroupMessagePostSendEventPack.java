package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class GroupMessagePostSendEventPack {
    private long id;
    private boolean res;
    private MessageChain message;
    private String error;

    public GroupMessagePostSendEventPack(long id, boolean res, MessageChain message, String error) {
        this.error = error;
        this.id = id;
        this.message = message;
        this.res = res;
    }

    public GroupMessagePostSendEventPack() {
    }

    public long getId() {
        return id;
    }

    public MessageChain getMessage() {
        return message;
    }

    public boolean isRes() {
        return res;
    }

    public String getError() {
        return error;
    }
}
