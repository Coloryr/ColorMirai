package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/**
 * 31 [机器人]群名改变（事件）
 */
public class GroupNameChangeEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 执行人QQ号
     */
    public long fid;
    /**
     * 旧的名字
     */
    public String old;
    /**
     * 新的名字
     */
    public String now;
}
