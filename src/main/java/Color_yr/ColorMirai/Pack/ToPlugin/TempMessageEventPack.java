package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class TempMessageEventPack {
    private long id;
    private long fid;
    private String name;
    private MessageChain message;
    private int time;

    public TempMessageEventPack(long id, long fid, String name, MessageChain message, int time) {
        this.fid = fid;
        this.id = id;
        this.message = message;
        this.name = name;
        this.time = time;
    }

    public TempMessageEventPack() {
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

    public long getFid() {
        return fid;
    }

    public MessageChain getMessage() {
        return message;
    }
}
