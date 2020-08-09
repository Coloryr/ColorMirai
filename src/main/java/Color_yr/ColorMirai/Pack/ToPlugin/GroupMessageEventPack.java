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
    public long id;
    public long fid;
    public String name;
    public String message;

    public GroupMessageEventPack(long id, long fid, String name, MessageChain message) {
        this.fid = fid;
        this.id = id;
        this.message = message.contentToString();
        this.name = name;
    }
}
