package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 80 [机器人]机器人群恢复（事件）
 */
public class BotJoinGroupEventRetrieveEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;

    public BotJoinGroupEventRetrieveEventPack(long qq, long id) {
        this.qq = qq;
        this.id = id;
    }
}
