package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class GroupMessagePostSendEventPack {
    private long id;
    private boolean res;
    private String message;
    private String error;

    public GroupMessagePostSendEventPack(long id, boolean res, MessageChain message, String error) {
        this.error = error;
        this.id = id;
        this.message = message.contentToString();
        this.res = res;
    }

    public GroupMessagePostSendEventPack() {
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

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
