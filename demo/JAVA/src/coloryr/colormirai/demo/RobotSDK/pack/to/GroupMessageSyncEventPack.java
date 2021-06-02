package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;

import java.util.List;

/*
89 [机器人]其他客户端发送群消息（事件）
id:群号
time:时间
message:消息
 */
public class GroupMessageSyncEventPack extends PackBase {
    public long id;
    public int time;
    public List<String> message;
}
