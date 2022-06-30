package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 124 [机器人]陌生人关系改变->删除（事件）
 * 125 [机器人]陌生人关系改变->朋友（事件）
 */
public class StrangerRelationChangePack extends PackBase {
    /**
     * QQ号
     */
    public long id;
    /**
     * 方式
     */
    public int type;
}