package Color_yr.ColorMirai.Pack;

import net.mamoe.mirai.message.data.MessageChain;

public class TempMessagePostSendEventPack {
    private long id;
    private long fid;
    private boolean res;
    private MessageChain message;
    private String error;

    public TempMessagePostSendEventPack(long id, long fid, boolean res, MessageChain message, String error) {
        this.error = error;
        this.fid = fid;
        this.id = id;
        this.message = message;
        this.res = res;
    }

    public TempMessagePostSendEventPack() {
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }

    public String getError() {
        return error;
    }

    public MessageChain getMessage() {
        return message;
    }

    public boolean isRes() {
        return res;
    }
}
