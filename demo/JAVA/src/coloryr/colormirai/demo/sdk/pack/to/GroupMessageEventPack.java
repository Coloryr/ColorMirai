package coloryr.colormirai.demo.sdk.pack.to;

import coloryr.colormirai.demo.sdk.enums.MemberPermission;
import coloryr.colormirai.demo.sdk.pack.PackBase;

import java.util.List;

/**
 * 49 [机器人]收到群消息（事件）
 */
public class GroupMessageEventPack extends PackBase {
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
     * 权限
     */
    public MemberPermission permission;
    /**
     * 发送的消息
     */
    public List<String> message;
}
