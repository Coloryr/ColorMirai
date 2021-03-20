package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

import java.util.ArrayList;
import java.util.List;

/*
22 [机器人]在发送好友消息前广播（事件）
message：消息
id：好友QQ号
 */
public class FriendMessagePreSendEventPack extends PackBase {
    public List<String> message;
    public long id;
}
