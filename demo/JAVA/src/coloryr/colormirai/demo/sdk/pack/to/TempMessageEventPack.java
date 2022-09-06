package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.enums.MemberPermission;
import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 50 [机器人]收到群临时会话消息（事件）
 */
public class TempMessageEventPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 发送人QQ号
     */
    public long fid;
    /**
     * 群名片
     */
    public String name;
    /**
     * 消息ID
     */
    public int[] ids1;
    /**
     * 消息ID
     */
    public int[] ids2;
    /**
     * 权限
     */
    public MemberPermission permission;
    /**
     * 发送的消息
     */
    public List<String> message;
    /**
     * 时间
     */
    public int time;
}
