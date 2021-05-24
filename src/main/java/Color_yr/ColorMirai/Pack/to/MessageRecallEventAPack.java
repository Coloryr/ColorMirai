package Color_yr.ColorMirai.pack.to;

import Color_yr.ColorMirai.pack.PackBase;

/*
44 [机器人]好友消息撤回（事件）
id:好友QQ号
mid:消息ID
time:时间
 */
public class MessageRecallEventAPack extends PackBase {
    public long id;
    public int[] mid;
    public int time;

    public MessageRecallEventAPack(long qq, long id, int[] mid, int time) {
        this.id = id;
        this.qq = qq;
        this.mid = mid;
        this.time = time;
    }
}
