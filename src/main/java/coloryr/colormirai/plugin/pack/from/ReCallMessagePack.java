package coloryr.colormirai.plugin.pack.from;

import coloryr.colormirai.plugin.pack.PackBase;
import net.mamoe.mirai.message.data.MessageSourceKind;

/**
 * 71 [插件]撤回消息
 */
public class ReCallMessagePack extends PackBase {
    /**
     * 消息ID
     */
    public int[] ids1;
    /**
     * 消息ID
     */
    public int[] ids2;
    /**
     * 类型
     */
    public MessageSourceKind kind;
}
