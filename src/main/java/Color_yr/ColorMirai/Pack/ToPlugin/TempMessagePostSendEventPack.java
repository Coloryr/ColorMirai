package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class TempMessagePostSendEventPack {
    private long id;
    private long fid;
    private boolean res;
    private String message;
    private String error;

    public TempMessagePostSendEventPack(long id, long fid, boolean res, MessageChain message, String error) {
        this.error = error;
        this.fid = fid;
        this.id = id;
        this.message = message.contentToString();
        this.res = res;
    }

    public TempMessagePostSendEventPack() {
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
}
