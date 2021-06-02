package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;

/*
36 [机器人]成员主动加入群（事件）
id:群号
fid:进群人QQ号
name:进群人QQ号
 */
public class MemberJoinEventPack extends PackBase {
    public long id;
    public long fid;
    public String name;

    public MemberJoinEventPack(long qq, long id, long fid, String name) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.name = name;
    }
}

