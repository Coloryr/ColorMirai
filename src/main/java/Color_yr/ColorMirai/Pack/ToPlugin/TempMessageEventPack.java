package Color_yr.ColorMirai.Pack.ToPlugin;

import net.mamoe.mirai.message.data.MessageChain;

/*
50 [机器人]收到群临时会话消息（事件）
id：群号
fid：发送人QQ号
name：发送人的群名片
message：发送的消息
time：时间
 */
public class TempMessageEventPack extends GroupMessageEventPack {
    private int time;

    public TempMessageEventPack(long id, long fid, String name, MessageChain message, int time) {
        super(id, fid, name, message);
        this.time = time;
    }

    public TempMessageEventPack() {
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
