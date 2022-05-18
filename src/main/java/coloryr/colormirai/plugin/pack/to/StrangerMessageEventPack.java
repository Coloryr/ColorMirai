package coloryr.colormirai.plugin.pack.to;

import net.mamoe.mirai.message.data.MessageChain;

/*
 * 116 [机器人]收到陌生人消息（事件）
 */
public class StrangerMessageEventPack extends FriendMessageEventPack {

    public StrangerMessageEventPack(long qq, long id, MessageChain message, int time, String name) {
        super(qq, id, message, time, name);
    }
}
