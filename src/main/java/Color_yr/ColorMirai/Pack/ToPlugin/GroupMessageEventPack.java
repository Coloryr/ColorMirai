package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

/*
49 [机器人]收到群消息（事件）
id：群号
fid：发送人QQ号
name：发送人的群名片
message：发送的消息
 */
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
