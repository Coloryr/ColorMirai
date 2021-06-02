package coloryr.colormirai.demo.RobotSDK.pack.to;

import coloryr.colormirai.demo.RobotSDK.pack.PackBase;

import java.util.List;

/*
47 [机器人]在群临时会话消息发送后广播（事件）
id:群号
fid:发送到的QQ号
res:是否成功发送
message:消息
error:错误信息
 */
public class TempMessagePostSendEventPack extends PackBase {
    public long id;
    public long fid;
    public boolean res;
    public List<String> message;
    public String error;
}
