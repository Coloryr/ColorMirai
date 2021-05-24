package Color_yr.ColorMirai.pack.to;

import Color_yr.ColorMirai.pack.PackBase;

/*
79 [机器人]成员群恢复（事件）
id: QQ群
fid: 成员QQ号
 */
public class MemberJoinRetrieveEventPack extends PackBase {
    public long id;
    public long fid;

    public MemberJoinRetrieveEventPack(long qq, long id, long fid) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
    }
}
