package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
7 [机器人]主动退出一个群（事件）
id；群号
 */
public class BotLeaveEventAPack extends PackBase {
    public long id;

    public BotLeaveEventAPack(long qq, long id) {
        this.qq = qq;
        this.id = id;
    }
}
