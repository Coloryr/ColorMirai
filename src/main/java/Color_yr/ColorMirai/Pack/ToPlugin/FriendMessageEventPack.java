package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

/*
51 [机器人]收到朋友消息（事件）
id：朋友ID
fname：发送人的群名片
message：消息
time：时间
 */
public class FriendMessageEventPack {
    private long id;
    private String name;
    private String message;
    private int time;

    public FriendMessageEventPack(long id, String name, MessageChain message, int time) {
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
        this.time = time;
    }

    public FriendMessageEventPack() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
