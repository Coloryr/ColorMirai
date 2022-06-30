package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 8 [机器人]被管理员或群主踢出群（事件）
 */
public class BotLeaveEventBPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 执行人QQ
     */
    public long fid;
}
