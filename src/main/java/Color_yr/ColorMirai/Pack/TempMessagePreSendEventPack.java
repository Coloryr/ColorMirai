package Color_yr.ColorMirai.Pack;

import net.mamoe.mirai.message.data.Message;

public class TempMessagePreSendEventPack {
    private long id;
    private long fid;
    private Message message;

    public TempMessagePreSendEventPack(long id, long fid, Message message) {
        this.fid = fid;
        this.id = id;
        this.message = message;
    }

    public TempMessagePreSendEventPack() {
    }

    public long getId() {
        return id;
    }

    public long getFid() {
        return fid;
    }

    public Message getMessage() {
        return message;
    }
}
