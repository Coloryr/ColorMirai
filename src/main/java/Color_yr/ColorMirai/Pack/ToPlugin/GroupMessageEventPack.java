package Color_yr.ColorMirai.Pack.ToPlugin;

import Color_yr.ColorMirai.Pack.PackBase;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;
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
    public List<String> message;

    public GroupMessageEventPack(long qq, long id, long fid, MessageChain message) {
        this.fid = fid;
        this.qq = qq;
        this.id = id;
        this.message = new ArrayList<>();
        for (SingleMessage item : message) {
            this.message.add(item.toString());
        }
        this.message.add(message.contentToString());
    }
}
