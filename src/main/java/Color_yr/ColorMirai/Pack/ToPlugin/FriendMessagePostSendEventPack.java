package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

/*
21 [机器人]在好友消息发送后广播（事件）
message：消息
id：好友QQ号
name：好友nick
res：是否成功发送
error：错误消息
 */
public class FriendMessagePostSendEventPack {
    private String message;
    private long id;
    private String name;
    private boolean res;
    private String error;

    public FriendMessagePostSendEventPack(MessageChain message, long id, String name, boolean res, String error) {
        this.error = error;
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
        this.res = res;
    }

    public FriendMessagePostSendEventPack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean getRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }
}
