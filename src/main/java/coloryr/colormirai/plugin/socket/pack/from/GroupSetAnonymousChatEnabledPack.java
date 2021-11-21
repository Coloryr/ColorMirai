package coloryr.colormirai.plugin.socket.pack.from;

import coloryr.colormirai.plugin.socket.pack.PackBase;

/**
 * 115 [插件]设置允许匿名聊天
 */
public class GroupSetAnonymousChatEnabledPack extends PackBase {
    /**
     * 群号
     */
    public long id;
    /**
     * 是否允许
     */
    public boolean enable;
}
