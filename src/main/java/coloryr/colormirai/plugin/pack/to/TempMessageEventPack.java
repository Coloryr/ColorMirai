package coloryr.colormirai.plugin.pack.to;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * 50 [机器人]收到群临时会话消息（事件）
 */
public class TempMessageEventPack extends GroupMessageEventPack {
    /**
     * 时间
     */
    public int time;

    public TempMessageEventPack(long qq, long id, long fid, MessageChain message, int time, MemberPermission permission, String name) {
        super(qq, id, fid, message, permission, name);
        this.time = time;
    }

}
