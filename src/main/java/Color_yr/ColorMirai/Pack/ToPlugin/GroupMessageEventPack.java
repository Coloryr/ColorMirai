package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

public class GroupMessageEventPack {
    private long id;
    private long fid;
    private String name;
    private MessageChain message;

    public GroupMessageEventPack(long id, long fid, String name, MessageChain message) {
        this.fid = fid;
        this.id = id;
        this.message = message;
        this.name = name;
    }

    public GroupMessageEventPack() {
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
