package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

/*
5 [机器人]成功加入了一个新群（不确定. 可能是主动加入）（事件）
id:群号
 */
public class BotJoinGroupEventAPack extends PackBase {
    public long id;

    public BotJoinGroupEventAPack(long qq, long id) {
        this.qq = qq;
        this.id = id;
    }
}
