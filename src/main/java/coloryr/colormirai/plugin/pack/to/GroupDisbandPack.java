package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 113 [机器人]群解散消息（事件）
 */
public class GroupDisbandPack extends PackBase {
    /**
     * 群号
     */
    public long id;

    public GroupDisbandPack(long qq, long id) {
        this.qq = qq;
        this.id = id;
    }
}
