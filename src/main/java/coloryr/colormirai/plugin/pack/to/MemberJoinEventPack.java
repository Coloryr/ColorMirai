package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/*
 * 36 [机器人]成员主动加入群（事件）
 */
public class MemberJoinEventPack extends PackBase {
    /*
     * 群号
     */
    public long id;
    /*
     * 进群人QQ号
     */
    public long fid;
    /*
     * 进群人QQ号
     */
    public String name;

    public MemberJoinEventPack(long qq, long id, long fid, String name) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.name = name;
    }
}
