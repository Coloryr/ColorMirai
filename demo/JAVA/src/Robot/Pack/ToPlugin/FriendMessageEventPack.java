package Robot.Pack.ToPlugin;

import Robot.Pack.PackBase;

import java.util.List;

/*
51 [机器人]收到朋友消息（事件）
id:朋友QQ号
message:消息
time:时间
name:昵称
 */
public class FriendMessageEventPack extends PackBase {
    public long id;
    public List<String> message;
    public int time;
    public String name;
}
