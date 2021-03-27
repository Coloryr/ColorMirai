package Robot.Pack.FromPlugin;

import Robot.Pack.PackBase;

import java.util.List;

/*
54 [插件]发送好友消息
id:好友QQ号
message:消息
 */
public class SendFriendMessagePack extends PackBase {
    public long id;
    public List<String> message;
}
