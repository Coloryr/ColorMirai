package coloryr.colormirai.plugin.pack.to;

import net.mamoe.mirai.message.data.MessageChain;

/**
 * 116 [机器人]收到陌生人消息（事件）
 */
public class StrangerMessageEventPack extends FriendMessageEventPack {

    public StrangerMessageEventPack(long qq, long id, int[] ids1, int[] ids2,
                                    MessageChain message, int time, String name) {
        super(qq, id, ids1, ids2, message, time, name);
    }
}
