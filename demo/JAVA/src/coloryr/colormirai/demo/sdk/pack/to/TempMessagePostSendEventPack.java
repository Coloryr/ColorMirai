package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 47 [机器人]在群临时会话消息发送后广播（事件）
 */
public class TempMessagePostSendEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 发送到的QQ号
     */
    public long fid;
    /**
     * 是否成功发送
     */
    public boolean res;
    /**
     * 消息
     */
    public List<String> message;
    /**
     * 错误信息
     */
    public String error;
}
