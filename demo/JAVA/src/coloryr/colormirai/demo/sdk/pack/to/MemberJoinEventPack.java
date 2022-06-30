package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 36 [机器人]成员主动加入群（事件）
 */
public class MemberJoinEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 进群人QQ号
     */
    public long fid;
    /**
     * 进群人QQ号
     */
    public String name;
}
