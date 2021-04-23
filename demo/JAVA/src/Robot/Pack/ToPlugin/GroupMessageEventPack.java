package Robot.Pack.ToPlugin;

import Robot.Pack.MemberPermission;
import Robot.Pack.PackBase;

import java.util.List;

/*
49 [机器人]收到群消息（事件）
id:群号
fid:发送人QQ号
message:发送的消息
 */
public class GroupMessageEventPack extends PackBase {
    public long id;
    public long fid;
    public MemberPermission permission;
    public List<String> message;
}
