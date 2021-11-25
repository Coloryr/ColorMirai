package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/*
28 [机器人]在群消息发送后广播（事件）
id:群号
res:是否发送成功
message:发送的消息
error:错误消息
 */
public class GroupMessagePostSendEventPack extends PackBase {
    public long id;
    public boolean res;
    public List<String> message;
    public String error;
}
