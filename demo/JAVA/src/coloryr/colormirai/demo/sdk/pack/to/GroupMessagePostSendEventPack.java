package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 28 [机器人]在群消息发送后广播（事件）
 */
public class GroupMessagePostSendEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 是否发送成功
     */
    public boolean res;
    /**
     * 发送的消息
     */
    public List<String> message;
    /**
     * 错误消息
     */
    public String error;
}
