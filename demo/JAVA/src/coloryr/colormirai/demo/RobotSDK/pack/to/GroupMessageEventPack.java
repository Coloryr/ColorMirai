package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.enums.MemberPermission;
import coloryr.colormirai.demo.RobotSDK.pack.PackBase;

import java.util.List;

/*
49 [机器人]收到群消息（事件）
id:群号
fid:发送人QQ号
message:发送的消息
permission:权限
name:群名片
 */
public class GroupMessageEventPack extends PackBase {
    public long id;
    public long fid;
    public MemberPermission permission;
    public List<String> message;
    public String name;
}
