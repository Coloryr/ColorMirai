package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.Message;

public class TempMessagePreSendEventPack {
    private long id;
    private long fid;
    private String message;

    public TempMessagePreSendEventPack(long id, long fid, Message message) {
        this.fid = fid;
        this.id = id;
        this.message = message.contentToString();
    }

    public TempMessagePreSendEventPack() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
