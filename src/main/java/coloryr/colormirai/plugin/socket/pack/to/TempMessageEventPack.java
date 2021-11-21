package coloryr.colormirai.plugin.socket.pack.to;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.MessageChain;

/*
50 [机器人]收到群临时会话消息（事件）
id:群号
fid:发送人QQ号
message:发送的消息
time:时间
name:群名片
 */
public class TempMessageEventPack extends GroupMessageEventPack {
    public int time;

    public TempMessageEventPack(long qq, long id, long fid, MessageChain message, int time, MemberPermission permission, String name) {
        super(qq, id, fid, message, permission, name);
        this.time = time;
    }

}
