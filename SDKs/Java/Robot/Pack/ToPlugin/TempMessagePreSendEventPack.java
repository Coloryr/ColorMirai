package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

import java.util.List;

/*
48 [机器人]在发送群临时会话消息前广播（事件）
id：群号
fid：发送人的QQ号
message：消息
 */
public class TempMessagePreSendEventPack extends PackBase {
    public long id;
    public long fid;
    public List<String> message;
}
