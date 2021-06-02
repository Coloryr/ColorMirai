package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;

/*
9 [机器人]被禁言（事件）
id:群号
fid:执行人QQ号
time:禁言时间
 */
public class BotMuteEventPack extends PackBase {
    public long id;
    public long fid;
    public int time;

    public BotMuteEventPack(long qq, long id, long fid, int time) {
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.time = time;
    }
}
