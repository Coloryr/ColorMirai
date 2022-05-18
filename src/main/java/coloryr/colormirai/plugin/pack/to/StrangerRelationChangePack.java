package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/*
 * 124 [机器人]陌生人关系改变（事件）
 */
public class StrangerRelationChangePack extends PackBase {
    /*
     * QQ号
     */
    public long id;
    /*
     * 方式
     */
    public int type;

    public StrangerRelationChangePack(long qq, long id, int type) {
        this.qq = qq;
        this.id = id;
        this.type = type;
    }
}