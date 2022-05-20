package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
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

    public TempMessageEventPack(long qq, long id, long fid, MessageChain message, int time, MemberPermission permission, String name) {
        this.qq = qq;
        this.id = id;
        this.fid = fid;
        this.message = new ArrayList<>();
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.permission = permission;
        this.name = name;
        this.time = time;
    }

}
