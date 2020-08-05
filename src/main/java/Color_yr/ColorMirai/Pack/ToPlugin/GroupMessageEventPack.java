package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class GroupMessageEventPack {
    private long id;
    private long fid;
    private String name;
    private String message;

    public GroupMessageEventPack(long id, long fid, String name, MessageChain message) {
        this.fid = fid;
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
    }

    public GroupMessageEventPack() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getFid() {
        return fid;
    }

    public String getMessage() {
        return message;
    }
}
