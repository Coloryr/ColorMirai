package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

import java.util.List;

/*
21 [机器人]在好友消息发送后广播（事件）
message:消息
id:好友QQ号
res:是否成功发送
error:错误消息
 */
public class FriendMessagePostSendEventPack extends PackBase {
    public List<String> message;
    public long id;
    public boolean res;
    public String error;
}
