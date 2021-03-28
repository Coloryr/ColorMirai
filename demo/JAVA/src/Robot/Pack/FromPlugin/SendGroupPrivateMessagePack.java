package Robot.Pack.FromPlugin;

import Robot.Pack.PackBase;

import java.util.List;

/*
53 [插件]发送私聊消息
id:群号
fid:成员QQ号
message:消息
 */
public class SendGroupPrivateMessagePack extends PackBase {
    public long id;
    public long fid;
    public List<String> message;
}
