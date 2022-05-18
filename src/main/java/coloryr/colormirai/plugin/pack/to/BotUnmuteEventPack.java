package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 17 [机器人]被取消禁言（事件）
 */
public class BotUnmuteEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 执行人QQ号
     */
    public long fid;

    public BotUnmuteEventPack(long qq, long id, long fid) {
        this.fid = fid;
        this.id = id;
        this.qq = qq;
    }
}
