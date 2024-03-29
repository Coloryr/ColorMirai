package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;

/**
 * 37 [机器人]一个账号请求加入群事件, [Bot] 在此群中是管理员或群主.（事件）
 */
public class MemberJoinRequestEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 进群人
     */
    public long fid;
    /**
     * 邀请人ID
     */
    public long qif;
    /**
     * 入群消息
     */
    public String message;
    /**
     * 事件ID
     */
    public long eventid;

    public MemberJoinRequestEventPack(long qq, long id, long fid, String message, long eventid, long qif) {
        this.eventid = eventid;
        this.qq = qq;
        this.fid = fid;
        this.id = id;
        this.qif = qif;
        this.message = message;
    }
}
