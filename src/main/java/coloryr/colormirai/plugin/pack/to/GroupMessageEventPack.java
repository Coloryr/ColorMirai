package coloryr.colormirai.plugin.pack.to;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
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
     * 发送时间
     */
    public int time;
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

    public GroupMessageEventPack(long qq, long id, long fid, int time, int[] ids1, int[] ids2,
                                 MessageChain message, MemberPermission permission, String name) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.permission = permission;
        this.message = new ArrayList<>();
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.message.add(message.contentToString());
        this.time = time;
        this.ids1 = ids1;
        this.ids2 = ids2;
        this.name = name;
    }
}
